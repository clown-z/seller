package com.clown.sell.service.impl;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clown.sell.dao.ProductInfoDao;
import com.clown.sell.domain.ProductInfo;
import com.clown.sell.service.ProductInfoService;

@Service
public class ProductServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao piDao;
    
    @Override
    public ProductInfo findOne(String productId) {
	return piDao.getOne(productId);
    }
    
    @Override
    public List<ProductInfo> findUpAll() {
	return null;
    }

    @Override
    public List<ProductInfo> findAll() {
	return piDao.findAll();
    }

    @Override
    public List<ProductInfo> findAll(Pageable pageable) {
	return null;
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
	return null;
    }

}
