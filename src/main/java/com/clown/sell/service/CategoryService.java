package com.clown.sell.service;

import java.util.List;

import com.clown.sell.domain.ProductCategory;

public interface CategoryService {
    
    ProductCategory findOne(Integer categotyId);
    
    List<ProductCategory> findAll();
    
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    
    ProductCategory save(ProductCategory productCategory);
    
}
