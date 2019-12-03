package com.clown.sell.service;

import com.clown.sell.dto.OrderDTO;

public interface BuyerService {
    
    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);
    
    //取消订单
    OrderDTO cancleOrderOne(String openid, String orderId);
}
