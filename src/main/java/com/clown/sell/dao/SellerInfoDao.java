package com.clown.sell.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clown.sell.domain.SellerInfo;

public interface SellerInfoDao extends JpaRepository<SellerInfo, String>{
    
    SellerInfo findByOpenid(String openid);
    
}
