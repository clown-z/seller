package com.clown.sell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao piDao;
    
    @Override
    public ProductInfo findOne(String productId) {
	return piDao.getOne(productId);
    }
    
    @Override
    public List<ProductInfo> findUpAll() {
	return piDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
	return piDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
	return piDao.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
	
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

}
