package com.clown.sell.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    
    /**
     * 设置Cookie
     * @param response 
     * @param name cookie名
     * @param value cookie值
     * @param maxAge 过期时间
     */
    public static void set(HttpServletResponse response, 
	    String name, 
	    String value, 
	    int maxAge) {
	Cookie cookie = new Cookie(name, value);
	cookie.setPath("/");
	cookie.setMaxAge(7200);
	response.addCookie(cookie);
    }
    
    public static Cookie get(HttpServletRequest request, String name) {
	Map<String, Cookie> cookieMap = readCookieMap(request);
	if (cookieMap.containsKey(name)) {
	    return cookieMap.get(name);
	}else {
	    return null;
	}
	
    }
    
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
	Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
	    for (Cookie cookie : cookies) {
		cookieMap.put(cookie.getName(), cookie);
	    }
	}
	return cookieMap;
    }
}
