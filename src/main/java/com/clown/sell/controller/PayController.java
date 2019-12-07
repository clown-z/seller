package com.clown.sell.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.clown.sell.dto.OrderDTO;
import com.clown.sell.enums.ResultEnum;
import com.clown.sell.exception.SellException;
import com.clown.sell.service.OrderService;
import com.clown.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;

/**
 * 支付
 * @author Clown
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PayService payService;
    
    
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, 
	    @RequestParam("returnUrl") String returnUrl, 
	    Map<String, Object> map) {
	//1.查询订单
	OrderDTO orderDTO = orderService.findOne(orderId);
	if (orderDTO == null) {
	    throw new SellException(ResultEnum.ORDER_NOT_EXIT); 
	}
	
	 //2.发起支付
	PayResponse payResponse = payService.create(orderDTO);
	map.put("payResponse", payResponse);
	map.put("returnUrl", returnUrl);
	map.put("orderDTO", orderDTO);
	return new ModelAndView("pay/create", map);
    }
    
   /**
    * 微信异步通知
    * @param notifyData
    * @return
    */
    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String notifyData) {
	payService.notify(notifyData);
	
	//返回给微信处理结果
	return new ModelAndView("pay/success");
    }
    
    @GetMapping("/notify")
    @ResponseBody
    public void create() {
	System.out.println("【】notify");
    }
}
