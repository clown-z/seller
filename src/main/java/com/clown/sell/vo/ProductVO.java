package com.clown.sell.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 商品（包含类目）
 * VO(view Object)
 * @author Clown
 *
 */
@Data
public class ProductVO {
    
    @JsonProperty("name")
    private String categoryName;
    
    @JsonProperty("type")
    private Integer categoryType;
    
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList; 
}
