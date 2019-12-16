package com.clown.sell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clown.sell.dao.SellerInfoDao;
import com.clown.sell.domain.SellerInfo;
import com.clown.sell.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoDao SellerInfo;
    
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
	return SellerInfo.findByOpenid(openid);
    }

}
