package com.student.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CookieHelper {
    
    public static final String COOKIE_NAME = "Authorization";
    /**
     * Expiration of cookie - 7days max
     */
    public static final Integer MAX_AGE = 7 * 24 * 60 * 60; 
      
    /**
     * Store generated jwt tokent on local storage of
     * clients browser
     * 
     * Ref: https://dzone.com/articles/how-to-use-cookies-in-spring-boot#:~:text=To%20set%20a%20cookie%20in,add%20it%20to%20the%20response.
     * 
     * @param request
     * @param response
     * @param jwtToken
     */
    public void setCookies(HttpServletRequest request, HttpServletResponse response, String jwtToken) {
	Cookie cookie = new Cookie(COOKIE_NAME, jwtToken);
	cookie.setMaxAge(MAX_AGE);
	cookie.setSecure(true);
	cookie.setHttpOnly(true);
	cookie.setPath("/");
	response.addCookie(cookie);
    }
    
    public void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
	Cookie cookie = new Cookie(COOKIE_NAME, null);
	cookie.setMaxAge(0);
	cookie.setSecure(true);
	cookie.setHttpOnly(true);
	cookie.setPath("/");

	//add cookie to response
	response.addCookie(cookie);
    }
}
