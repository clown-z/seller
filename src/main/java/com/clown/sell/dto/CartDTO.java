package com.clown.sell.dto;

import lombok.Data;

@Data
public class CartDTO {
    
    /** 商品Id。 */
    private String productId;
    
    /** 数量 */
    private Integer productQuantity;

    public CartDTO(String productId, Integer integer) {
	this.productId = productId;
	this.productQuantity = integer;
    }
  
}
