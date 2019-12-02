package com.clown.sell.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clown.sell.domain.ProductInfo;
import com.clown.sell.dto.CartDTO;

public interface ProductInfoService {
    
    ProductInfo findOne(String productId);
    
    /**
     **查询所有在架商品列表
     *@return
     */
    List<ProductInfo> findUpAll();
    
    Page<ProductInfo> findAll(Pageable pageable);
    
    ProductInfo save(ProductInfo productInfo);
    
    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
