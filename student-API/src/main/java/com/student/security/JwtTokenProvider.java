package com.student.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${com.student.jwtSecret}")
	private String jwtSecret;

	@Value("${con.student.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	public String generateToken(Authentication authentication) {

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * User id from jwt toke, for token owner
	 * 
	 * @param token
	 * @return
	 */
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	/**
	 * Validating provided token at login and api calling
	 * 
	 * @param authToken
	 * @return
	 */
	public boolean validateToken(String authToken) {

		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;

		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
			ex.printStackTrace();
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
			ex.printStackTrace();
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
			ex.printStackTrace();
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
			ex.printStackTrace();
		}
		return false;
	}
}
