package com.clown.sell.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clown.sell.converter.OrderFrom2OrderDTOConverter;
import com.clown.sell.dto.OrderDTO;
import com.clown.sell.enums.ResultEnum;
import com.clown.sell.exception.SellException;
import com.clown.sell.from.OrderFrom;
import com.clown.sell.service.BuyerService;
import com.clown.sell.service.OrderService;
import com.clown.sell.util.ResultVOUtil;
import com.clown.sell.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private BuyerService buyerService;    
    
    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderFrom orderFrom,
	    BindingResult bindingResult){
	//
	if (bindingResult.hasErrors()) {
	    log.error("【创建订单】 参数不正确， orderForm={}", orderFrom);
	    throw new SellException(ResultEnum.PARAM_ERROR.getCode(), 
		    bindingResult.getFieldError().getDefaultMessage());
	}
	//格式转换
	OrderDTO orderDTO = OrderFrom2OrderDTOConverter.convert(orderFrom);
	if (CollectionUtils.isEmpty(orderDTO.getOrderDetailsList())) {
	    log.error("【创建订单】 购物车不能为空");
	    throw new SellException(ResultEnum.CART_EMPTY);
	}
	
	OrderDTO createResult = orderService.create(orderDTO);
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("orderId", createResult.getOrderId());
	return ResultVOUtil.success(map);
    }
    
    
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
	    @RequestParam(value = "page", defaultValue = "0") Integer page, 
	    @RequestParam(value = "size", defaultValue = "10") Integer size){
	if (StringUtils.isEmpty(openid)) {
	    log.error("【查询订单列表】 openid为空");
	    throw new SellException(ResultEnum.PARAM_ERROR);
	}
	
	PageRequest request = PageRequest.of(page, size);
	Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);
	return ResultVOUtil.success(orderDTOPage.getContent());
    }
    
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
	OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);	
	return ResultVOUtil.success(orderDTO);
    }
    
    //取消订单
    @PostMapping("/cancle")
    public ResultVO cancle(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
	buyerService.findOrderOne(openid, orderId);	
	return ResultVOUtil.success();
    }
}
