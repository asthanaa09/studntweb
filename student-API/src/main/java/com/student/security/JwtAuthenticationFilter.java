package com.student.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.student.utils.CookieHelper;

/**
 * JWTAuthenticationFilter to get the JWT token from the request, validate it,
 * load the user associated with the token, and pass it to Spring Security
 * 
 * @author kapil
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHENTICATION_HEADER = HttpHeaders.AUTHORIZATION;
    private final String TOKEN_PREFIX = "Bearer ";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	try {
	    String jwt = getJwtFromRequest(request);

	    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
		Long userId = tokenProvider.getUserIdFromJWT(jwt);

		UserDetails userDetails = customUserDetailsService.loadUserById(userId);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	    }

	} catch (Exception ex) {
	    logger.error("Could not set user authentication in security context", ex);
	    ex.printStackTrace();
	}

	filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
	String bearerToken = request.getHeader(AUTHENTICATION_HEADER);
	Cookie[] cookies = request.getCookies();
	
	if(cookies != null) {
	    for (Cookie cok : cookies)
		if (cok.getName().equals(CookieHelper.COOKIE_NAME)) {
		    bearerToken = TOKEN_PREFIX + cok.getValue();
		    break;
		}
	}
	
	if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX))
	    return bearerToken.substring(7, bearerToken.length());
	return null;
    }
}
