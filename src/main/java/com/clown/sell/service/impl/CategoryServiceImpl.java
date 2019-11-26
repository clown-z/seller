package com.clown.sell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clown.sell.dao.ProductCategoryDao;
import com.clown.sell.domain.ProductCategory;
import com.clown.sell.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryDao pcDao; 
    
    @Override
    public ProductCategory findOne(Integer categotyId) {
	return pcDao.getOne(categotyId);
    }

    @Override
    public List<ProductCategory> findAll() {
	return pcDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
	return pcDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
	return pcDao.save(productCategory);
    }

}
