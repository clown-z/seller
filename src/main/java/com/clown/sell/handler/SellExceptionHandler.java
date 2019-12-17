package com.clown.sell.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.clown.sell.config.ProjectUrl;
import com.clown.sell.exception.SellerAuthorizeException;

@ControllerAdvice
public class SellExceptionHandler {
    
    @Autowired
    private ProjectUrl projectUrl;
    
    //拦截登录异常
    //http://127.0.0.1:8080/sell/seller/login?openid=abc
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthrizeException() {
	return new ModelAndView("redirect:"
		.concat(projectUrl.getSell())
		//.concat("/sell/wechat/qrAuthrize")
		//.concat("?returnUrl")
		.concat("/sell/seller/login?openid=token_abc"));
		
    }
}
