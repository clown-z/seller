package com.clown.sell.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    
    /**
     * 公众平台id
     */
    private String myAppId;
        
    /**
     * 公众平台密钥
     */
    private String myAppSecret;
    
    /**
     * 开放平台id
     */
    private String openAppId;
    
    /**
     * 开放平台密钥
     */
    private String openAppSecret;
    
    /**
     * 商户号
     */
    private String mchId;
    
    
    /**
     * 商户密钥
     */
    private String mchkey;
    
    
    /**
     * 商户证书路径
     */
    private String keyPath;
    
    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;
    
    /**
     * 微信模板Id
     */
    private Map<String, String> templateId;
}
