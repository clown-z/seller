package com.clown.sell.exception;

import com.clown.sell.enums.ResultEnum;

import lombok.Getter;

@Getter
public class SellException extends RuntimeException{
    
    private Integer code;
    
    public SellException(ResultEnum resultEnum) {
	super(resultEnum.getMessage());
	
	this.code = resultEnum.getCode();
    }
}
