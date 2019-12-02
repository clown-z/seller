package com.clown.sell.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.clown.sell.domain.OrderMaster;

public interface OrderMasterDao extends JpaRepository<OrderMaster, String>{

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    
}
