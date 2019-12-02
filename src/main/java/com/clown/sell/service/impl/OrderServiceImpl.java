package com.clown.sell.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.clown.sell.service.ProductInfoService;
import com.clown.sell.util.KeyUtil;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;
    
    @Autowired
    private OrderDetailDao orderDetailDao;
    
    @Autowired
    private OrderMasterDao orderMasterDao;
    
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
	BeanUtils.copyProperties(orderDTO, orderMaster);
	orderMaster.setOrderId(orderId);
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
	return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
	return null;
    }

    @Override
    public OrderDTO cancle(OrderDTO orderDTO) {
	return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
	return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
	return null;
    }

}
