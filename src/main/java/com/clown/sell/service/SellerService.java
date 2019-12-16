package com.clown.sell.service;

import com.clown.sell.domain.SellerInfo;

/**
 * 卖家端
 * @author Clown
 *
 */
public interface SellerService {
    
    SellerInfo findSellerInfoByOpenid(String openid);
}
