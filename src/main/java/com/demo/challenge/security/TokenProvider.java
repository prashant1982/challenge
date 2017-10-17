package com.demo.challenge.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.demo.challenge.util.UserSessionMap;

/**
 * Provider class for JWT token mechanism
 * 
 * @author Prashant
 *
 */
@Component
public class TokenProvider {

	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "auth";
	private String secretKey;
	private long tokenValidityInMilliseconds;

	private UserSessionMap userSessionMap;
	
	public TokenProvider(UserSessionMap userSessionMap) {
		this.userSessionMap = userSessionMap;
	}
	
	/**
	 * TODO: Initialize values from configuration
	 */
	@PostConstruct
	public void init() {
		this.secretKey = "my-secret-token-jwt";
		this.tokenValidityInMilliseconds = 86400000;
	}

	/**
	 * Create JWT Token
	 * 
	 * @param authentication
	 *            Authentication object
	 * @return JWT token
	 */
	public String createToken(String username) {
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);

		return Jwts.builder().setSubject(username)
				.claim(AUTHORITIES_KEY, "user")
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.setExpiration(validity).compact();
	}

	/**
	 * Get Authentication object
	 * 
	 * @param token
	 *            Bearer token
	 * @return Authentication Object
	 */
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(token).getBody();

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(claims.get(AUTHORITIES_KEY)
				.toString()));

		User principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "",
				authorities);
	}

	/**
	 * Validate authorization token
	 * 
	 * @param authToken
	 *            Bearer Token
	 * @return true | false
	 */
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			if (userSessionMap.getSession(authToken) == null) {
				throw new SignatureException("No User Session Found, Please re-login");
			}
			
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature : {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.trace("Invalid JWT token : {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.trace("Expired JWT token : {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.trace("Unsupported JWT token : {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.trace("JWT token compact of handler are invalid : {}",
					e.getMessage());
		}
		return false;
	}
}
