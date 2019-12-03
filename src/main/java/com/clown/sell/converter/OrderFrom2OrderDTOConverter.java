package com.clown.sell.converter;

import java.util.ArrayList;
import java.util.List;

import com.clown.sell.domain.OrderDetail;
import com.clown.sell.dto.OrderDTO;
import com.clown.sell.enums.ResultEnum;
import com.clown.sell.exception.SellException;
import com.clown.sell.from.OrderFrom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderFrom2OrderDTOConverter {

    public static OrderDTO convert(OrderFrom orderFrom) {
	Gson gson = new Gson();
	OrderDTO orderDTO = new OrderDTO();
	
	orderDTO.setBuyerName(orderFrom.getName());
	orderDTO.setBuyerPhone(orderFrom.getPhone());
	orderDTO.setBuyerAddress(orderFrom.getAddress());
	orderDTO.setBuyerOpenid(orderFrom.getOpenid());
	
	List<OrderDetail> orderDetailsList = new ArrayList<OrderDetail>();
	try {
	    orderDetailsList = gson.fromJson(orderFrom.getItem(), 
			new TypeToken<List<OrderDetail>>() {}.getType() );
	} catch (Exception e) {
	    log.error("【对象转换】 错误。 string={}", orderFrom.getItem());
	    throw new SellException(ResultEnum.PARAM_ERROR);
	}
	
	orderDTO.setOrderDetailsList(orderDetailsList);
	return orderDTO;
    }
}
