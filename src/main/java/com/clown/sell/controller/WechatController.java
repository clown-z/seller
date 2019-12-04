package com.clown.sell.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clown.sell.enums.ResultEnum;
import com.clown.sell.exception.SellException;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    
    private final static String APPID = "";
    private final static String SECRET = "";
    private final static String GRANT_TYPE = "authorization_code";
    
    @Autowired
    private WxMpService wxMpService;
    
    //手动获取openid
//    @GetMapping("/auth")
//    public void auth(@RequestParam("code") String code) {
//	log.info("进入auth方法");
//	log.info("code={}", code);
//    
//	String url = " https://api.weixin.qq.com/sns/oauth2/access_token?"
//		+ "appid=" + APPID
//		+ "&secret"+ SECRET
//		+ "&code=" + code
//		+ "&grant_type" + GRANT_TYPE;
//	
//	RestTemplate restTemplate = new RestTemplate(); 
//	String  response = restTemplate.getForObject(url, String.class);
//	log.info("response={}", response);
//    }
    
    /** 第三方SDK获取openid*/
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {	
	//1.配置
	//2.调用方法
	String url = "http://clown.com/sell/wechat/userinfo";
	String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
	log.info("【微信网页授权】 获取code, redirectUrl={}", redirectUrl);
	
	return "redirect:" + redirectUrl;
    }
    
    
    @GetMapping("/userinfo")
    public String userInfo(@RequestParam("code") String code, 
	    @RequestParam("state") String returnUrl) {
	 WxMpOAuth2AccessToken wxAuth2AccessToken = new WxMpOAuth2AccessToken();
	try {
	    wxAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
	} catch (WxErrorException e) {	    
	    log.info("【微信网页授权】 {}", e);
	    throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), 
		    e.getError().getErrorMsg());
	}
	String openId = wxAuth2AccessToken.getOpenId();
	
	return "redirect:" + returnUrl + "?openid=" + openId;
    }
}
