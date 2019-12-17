package com.clown.sell.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import com.clown.sell.constant.CookieConstant;
import com.clown.sell.constant.RedisConstant;
import com.clown.sell.enums.ResultEnum;
import com.clown.sell.exception.SellException;
import com.clown.sell.exception.SellerAuthorizeException;
import com.clown.sell.util.CookieUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    
    @Pointcut("execution(public * com.clown.sell.controller.Seller*.*(..))"
	    + "!execution(public * com.clown.sell.controller.SellerUserController.*(..))")
    public void verify() {
	
    }
    
    
    @Before("verify()")
    public void doVerify() {
	ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	HttpServletRequest request = attributes.getRequest();
	//查询Cookie
	Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
	if (cookie == null) {
	    log.warn("【登录校验】 Cookie中查不到token");
	    throw new SellerAuthorizeException();
	}
	
	//去redis查询
	String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
	System.out.println("ss: "+String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
	if (StringUtils.isEmpty(tokenValue)) {
	    log.warn("【登录校验】 Redis中查不到token");
	    throw new SellerAuthorizeException();
	}
    }
}
