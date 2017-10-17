package com.demo.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.challenge.domain.UserSession;
import com.demo.challenge.repository.UserRepository;
import com.demo.challenge.util.UserSessionMap;

/**
 * Authenticate a user from the persistence storage
 * @author Prashant
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory
			.getLogger(DomainUserDetailsService.class);

	private final UserRepository userRepository;
	private UserSessionMap userSessionMap;

	public DomainUserDetailsService(UserRepository userRepository,
			UserSessionMap userSessionMap) {
		this.userRepository = userRepository;
		this.userSessionMap = userSessionMap;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String jwtToken) {
		log.debug("Authenticating {}", jwtToken);

		/** get the data from REDIS for the token */
		// UserSession userSession = userRepository.findOne(jwtToken);
		UserSession userSession = userSessionMap.getSession(jwtToken);

		if (userSession != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
			grantedAuthorities.add(authority);

			return new User(userSession.getUser(), userSession.getDeviceType(),
					grantedAuthorities);
		} else {
			throw new UsernameNotFoundException("User " + jwtToken
					+ " was not found in the " + "database");
		}

	}
}
