package com.clown.sell.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clown.sell.domain.ProductInfo;

public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {
    
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
