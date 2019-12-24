package com.clown.sell.vo;

import java.io.Serializable;
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
public class ProductVO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5403260624951598375L;

    @JsonProperty("name")
    private String categoryName;
    
    @JsonProperty("type")
    private Integer categoryType;
    
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList; 
}
