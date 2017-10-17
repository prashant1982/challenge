package com.demo.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.challenge.domain.UserSession;
import com.demo.challenge.repository.UserRepository;
import com.demo.challenge.service.UserService;
import com.demo.challenge.util.UserSessionMap;
import com.demo.challenge.web.rest.vm.UserLoginVM;

/**
 * Service Service Implementation for managing users.
 * 
 * @author Prashant
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	// TODO: REDIS server to be setup for storing session to REDIS
	private final UserRepository userRepository;
	private UserSessionMap userSessionMap;

	public UserServiceImpl(UserRepository userRepository,
			UserSessionMap userSessionMap) {
		this.userRepository = userRepository;
		this.userSessionMap = userSessionMap;
	}

	@Override
	public void persistSession(UserLoginVM userLoginVM, String token) {
		log.debug(
				"Method call for persist user session to REDIS: Token : {}, Data: {}",
				token, userLoginVM);
		
		/** Inserting token and UserSession to REDIS */
		UserSession userSession = new UserSession();
		userSession.setId(token);
		userSession.setUser(userLoginVM.getUsername());
		userSession.setDeviceType(userLoginVM.getDeviceType());

//		userRepository.saveSession(userSession);
		userSessionMap.saveSession(userSession, token);
	}

	@Override
	public UserSession getDetails(String key) {
		log.debug("Getting details for the key: {}", key);
//		return userRepository.findOne(key);
		return userSessionMap.getSession(key.substring(7, key.length()));
	}

	@Override
	public void deleteSession(String key) {
		log.debug("Delete called for the key: {}", key);
//		userRepository.delete(key);
		userSessionMap.deleteSession(key.substring(7, key.length()));
	}
	
	

}
