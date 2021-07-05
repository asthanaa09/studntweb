package com.student.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * commence(): This method is called whenever an exception is thrown due to an
 * unauthenticated user trying to access a resource that requires
 * authentication.
 * 
 * @author Anurag Asthana
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
	    AuthenticationException e) throws IOException, ServletException {
	logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
	httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}
