package com.clown.sell.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import lombok.Data;

/**
 * 卖家信息表
 * @author Clown
 *
 */
@Data
@Entity
@DynamicUpdate
@Proxy(lazy = false)
public class SellerInfo {

    @Id
    private String id;
    
    private String username;
    
    private String password;
    
    /** 微信openid. */
    private String openid;
    
    /**创建时间.*/
    private Date createTime;
    
    /**修改时间.*/
    private Date updateTime;
       
}
