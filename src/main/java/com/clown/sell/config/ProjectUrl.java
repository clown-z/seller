package com.clown.sell.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrl {
    
    /**
     * 微信公众平台授权url
     */
    public String wechatMpAuthorize;
    
    /**
     * 微信开放平台授权url
     */
    public String wechatOpenAuthorize;
    
    /**
     *点餐系统
     */
    public String sell;
}
