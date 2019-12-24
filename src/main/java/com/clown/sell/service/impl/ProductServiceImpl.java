package com.clown.sell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clown.sell.dao.ProductInfoDao;
import com.clown.sell.domain.ProductInfo;
import com.clown.sell.dto.CartDTO;
import com.clown.sell.enums.ProductStatusEnum;
import com.clown.sell.enums.ResultEnum;
import com.clown.sell.exception.SellException;
import com.clown.sell.service.ProductInfoService;

@Service
//@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao piDao;
    
    @Override  
    public ProductInfo findOne(String productId) {
	return piDao.getOne(productId);
    }
    
    @Override
    //@Cacheable(key = "332")
    public List<ProductInfo> findUpAll() {
	return piDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
	return piDao.findAll(pageable);
    }

    @Override
    //@CachePut(key = "332")
    public ProductInfo save(ProductInfo productInfo) {
	return piDao.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
	for (CartDTO cartDTO : cartDTOList) {
	    ProductInfo productInfo = piDao.getOne(cartDTO.getProductId());
	    if(productInfo == null) {
		throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
	    }
	    
	    Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
	    
	    productInfo.setProductStock(result);
	    
	    piDao.save(productInfo);
	}
	
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
	for (CartDTO cartDTO : cartDTOList) {
	    ProductInfo productInfo = piDao.getOne(cartDTO.getProductId());
	    System.out.println(productInfo);
	    if(productInfo == null) {
		throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
	    }
	    
	    Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
	    System.out.println(result);
	    if(result < 0) {
		throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
	    }
	    
	    productInfo.setProductStock(result);
	    
	    piDao.save(productInfo);
	}
    }

    @Override
    public ProductInfo onSale(String productId) {
	ProductInfo productInfo= piDao.getOne(productId);
	if (productId == null) {
	    throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
	}
	if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
	    throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
	}
	//更新
	productInfo.setProductStatus(ProductStatusEnum.UP.getCode());	
	return piDao.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
	ProductInfo productInfo= piDao.getOne(productId);
	if (productId == null) {
	    throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
	}
	if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
	    throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
	}
	//更新
	productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());	
	return piDao.save(productInfo);
    }

}
