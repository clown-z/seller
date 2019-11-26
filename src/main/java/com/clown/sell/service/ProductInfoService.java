package com.clown.sell.service;

import java.awt.print.Pageable;
import java.util.List;

import com.clown.sell.domain.ProductInfo;

public interface ProductInfoService {
    ProductInfo findOne(String productId);
    
    /**
     **查询所有在架商品列表
     *@return
     */
    List<ProductInfo> findAll();
    
    List<ProductInfo> findAll(Pageable pageable);
    
    ProductInfo save(ProductInfo productInfo);

    List<ProductInfo> findUpAll();
    
    //加库存
    
    //减库存
}
