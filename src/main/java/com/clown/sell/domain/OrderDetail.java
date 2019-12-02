package com.clown.sell.domain;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
@Proxy(lazy = false)
public class OrderDetail {

    /**
    * detail_id
    */
    @Id
    private String detailId;

    /**
    * 订单Id
    */
    private String orderId;

    /**
    * product_id
    * 商品Id
    */
    private String productId;

    /**
    * 商品名称
    */
    private String productName;

    /**
    * 商品价格
    */
    private BigDecimal productPrice;

    /**
    * 商品数量
    */
    private Integer productQuantity;

    /**
    * 商品小图
    */
    private String productIcon;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;


}
