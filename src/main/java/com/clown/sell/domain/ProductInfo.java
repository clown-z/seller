package com.clown.sell.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import com.clown.sell.enums.ProductStatusEnum;
import com.clown.sell.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Proxy(lazy = false)
@DynamicUpdate
public class ProductInfo implements Serializable{
 

    /**
     * 
     */
    private static final long serialVersionUID = -6998872210600789034L;

    @Id
    private String productId;
    
    /** 名字。 */
    private String productName;
    
    /** 单价。 */
    private BigDecimal productPrice;
    
    /** 库存。 */
    private Integer productStock;
    
    /** 描述。 */
    private String productDescription;
    
    /** 小图。 */
    private String productIcon;
    
    /** 状态，0正常1下架。 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    
    /** 类目编号。 */
    private Integer categoryType;
    
    /**创建时间.*/
    private Date createTime;
    
    /**修改时间.*/
    private Date updateTime;
    
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
	return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}
