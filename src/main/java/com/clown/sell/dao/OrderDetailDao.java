package com.clown.sell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clown.sell.domain.OrderDetail;

public interface OrderDetailDao extends JpaRepository<OrderDetail, String>{

    List<OrderDetail> findByOrderId(String orderId);
}
