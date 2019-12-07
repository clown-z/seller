package com.clown.sell.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import com.clown.sell.domain.OrderDetail;
import com.clown.sell.enums.OrderStatusEnum;
import com.clown.sell.enums.PayStatusEnum;
import com.clown.sell.util.EnumUtil;
import com.clown.sell.util.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;


@Data
@DynamicUpdate
@Proxy(lazy = false)
//@JsonSerialize(include =Inclusion.NON_NULL)
//@JsonInclude(value = Include.NON_NULL)
public class OrderDTO {
    
    /** 订单id. */
    private String orderId;
    
    /** 买家名字. */
    private String buyerName;
    
    /** 买家手机号. */
    private String buyerPhone;
    
    /** 买家地址. */
    private String buyerAddress;
    
    /** 买家微信openid. */
    private String buyerOpenid;
    
    /** 订单总金额. */
    private BigDecimal orderAmount;
    
    /** 订单状态,默认0新下单. */
    private Integer orderStatus;
    
    /** 支付状态,默认0未支付. */
    private Integer payStatus;
    
    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    
    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    
    /** 订单详情List. */
    List<OrderDetail> orderDetailsList;
    
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
	return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
	return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
