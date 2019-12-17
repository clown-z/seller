package com.clown.sell.service;

import com.clown.sell.dto.OrderDTO;

/**
 * 推送消息
 * @author Clown
 *
 */
public interface PushMessage {
    
    /**
     * 订单状态更新
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
