package com.clown.sell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clown.sell.domain.ProductCategory;

public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer>{
    
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
