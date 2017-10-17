package com.demo.challenge.security;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filters incoming requests and installs a Spring Security principal if a
 * header corresponding to a valid user is found.
 * 
 * @author Prashant
 */
public class JWTFilter extends GenericFilterBean {

	private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

	private TokenProvider tokenProvider;

	public JWTFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String jwt = resolveToken(httpServletRequest);

			if (this.tokenProvider.validateToken(jwt)) {
				Authentication authentication = this.tokenProvider
						.getAuthentication(jwt);
				SecurityContextHolder.getContext().setAuthentication(
						authentication);
			}

			filterChain.doFilter(servletRequest, servletResponse);
		} catch (ExpiredJwtException eje) {
			log.error("Security exception for user {} - {}", eje.getClaims()
					.getSubject(), eje.getMessage());

			((HttpServletResponse) servletResponse)
					.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private String resolveToken(HttpServletRequest request)
			throws ServletException {
		String bearerToken = request.getHeader("Authorization");

		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			throw new ServletException(
					"Missing or invalid Authorization header");
		}

		return bearerToken.substring(7, bearerToken.length());
	}
}
