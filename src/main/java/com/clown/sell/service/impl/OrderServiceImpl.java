package com.clown.sell.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.clown.sell.converter.OrderMaster2OrderDTOConverter;
import com.clown.sell.dao.OrderDetailDao;
import com.clown.sell.dao.OrderMasterDao;
import com.clown.sell.domain.OrderDetail;
import com.clown.sell.domain.OrderMaster;
import com.clown.sell.domain.ProductInfo;
import com.clown.sell.dto.CartDTO;
import com.clown.sell.dto.OrderDTO;
import com.clown.sell.enums.OrderStatusEnum;
import com.clown.sell.enums.PayStatusEnum;
import com.clown.sell.enums.ResultEnum;
import com.clown.sell.exception.SellException;
import com.clown.sell.service.OrderService;
import com.clown.sell.service.PayService;
import com.clown.sell.service.ProductInfoService;
import com.clown.sell.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;
    
    @Autowired
    private OrderDetailDao orderDetailDao;
    
    @Autowired
    private OrderMasterDao orderMasterDao;
    
    @Autowired
    private PayService payService;
    
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
	
	/**  生成订单id. */
	String orderId = KeyUtil.genUniqueKey();
	/**  定义总价. */
	BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
	
	/**  订单生成时间. */
	Date CREATE_TIME = new Date();
	Date UPDATE_TIME = new Date();
	
	//1.查询商品（数量， 价格）
	for (OrderDetail orderDetail : orderDTO.getOrderDetailsList()) {
	    ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
	    if (productInfo == null) {
		throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
	    }
	    
	    //2.计算订单总价
	    orderAmount =  productInfo.getProductPrice().multiply(
		    new BigDecimal(orderDetail.getProductQuantity()))
		    .add(orderAmount);
	    
	    //订单详情入库  
	    orderDetail.setDetailId(KeyUtil.genUniqueKey());
	    orderDetail.setOrderId(orderId);
	    orderDetail.setCreateTime(CREATE_TIME);
	    orderDetail.setUpdateTime(UPDATE_TIME);
	    //拷贝productInfo中的属性到orderDetail
	    BeanUtils.copyProperties(productInfo, orderDetail);
	    orderDetailDao.save(orderDetail);
	}
	
	//3.写入订单数据库
	OrderMaster orderMaster = new OrderMaster();	
	orderDTO.setOrderId(orderId);
	BeanUtils.copyProperties(orderDTO, orderMaster);
	orderMaster.setOrderAmount(orderAmount);
	orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
	orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
	orderMaster.setCreateTime(CREATE_TIME);
	orderMaster.setUpdateTime(UPDATE_TIME);
	orderMasterDao.save(orderMaster);
	
	//4.扣库存
	System.out.println("进入减库存");
	List<CartDTO> cartDTOList =  orderDTO.getOrderDetailsList().stream()
		.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
		.collect(Collectors.toList());
	productInfoService.decreaseStock(cartDTOList);
	return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
	//查询订单
	OrderMaster orderMaster = orderMasterDao.getOne(orderId);
	if (orderMaster == null) {//订单判空
	    throw new SellException(ResultEnum.ORDER_NOT_EXIT);
	}
	
	//查询订单详情
	List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
	if (orderDetailList == null) {//订单详情判空
	    throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIT);
	}
	
	//创建OrderDTO对象，用于储存订单数据
	OrderDTO orderDTO = new OrderDTO();
	//拷贝orderMaster的数据到orderDTO
	BeanUtils.copyProperties(orderMaster, orderDTO);
	//设置订单详情
	orderDTO.setOrderDetailsList(orderDetailList);
	
	return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
	Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
	
	List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
	
	Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancle(OrderDTO orderDTO) {
	
	OrderMaster orderMaster = new OrderMaster();
	
	//判断订单状态
	if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
	    log.error("【取消订单】 订单状态不正确， orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
	    throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
	}
	
	//修改订单状态
	orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
	BeanUtils.copyProperties(orderDTO, orderMaster);
	OrderMaster updateResult = orderMasterDao.save(orderMaster);
	if (updateResult == null) {
	    log.error("取消订单】更新失败， orderMaster={}", orderMaster);
	    throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
	}
	
	//返回库存
	if (CollectionUtils.isEmpty(orderDTO.getOrderDetailsList())) {
	    log.error("取消订单】订单中无商品详情，  orderDTO={}", orderDTO);
	    throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
	}
	List<CartDTO> cartDTOList = orderDTO.getOrderDetailsList().stream()
		.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
		.collect(Collectors.toList());
	productInfoService.increaseStock(cartDTOList);
	
	//如果已支付，需要退款
	if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
	    payService.refund(orderDTO);
	}
	
	return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
	//判断订单状态
	if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
	    log.error("【完结订单】 订单状态不正确， orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
	    throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
	}
		
	//修改状态
	orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
	OrderMaster orderMaster = new OrderMaster();
	BeanUtils.copyProperties(orderDTO, orderMaster);
	OrderMaster updateResult = orderMasterDao.save(orderMaster);
	if (updateResult == null) {
	    log.error("【完结订单】更新失败， orderMaster={}", orderMaster);
	    throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
	}
	
	return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
	//判断订单状态
	if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
	    log.error("【订单支付完成】 订单状态不正确， orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
	    throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
	}
	
	//判断支付状态
	if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
	    log.error("【订单支付完成】 订单状态不正确， orderDTO={}",  orderDTO);
	    throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
	}
	
	//修改支付状态
	orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
	OrderMaster orderMaster = new OrderMaster();
	BeanUtils.copyProperties(orderDTO, orderMaster);
	OrderMaster updateResult = orderMasterDao.save(orderMaster);
	if (updateResult == null) {
	    log.error("【订单支付完成】更新失败， orderMaster={}", orderMaster);
	    throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
	}
	
	return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
	Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
	List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
	Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	return orderDTOPage;
    }

}
