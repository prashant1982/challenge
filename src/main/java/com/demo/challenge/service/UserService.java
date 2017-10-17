package com.demo.challenge.service;

import com.demo.challenge.domain.UserSession;
import com.demo.challenge.web.rest.vm.UserLoginVM;

/**
 * Service class for managing users.
 * 
 * @author Prashant
 *
 */
public interface UserService {

	/**
	 * Persist user session
	 * 
	 * @param userLoginVM
	 *            UserLoginVM
	 * @param token
	 *            JWT Token
	 */
	void persistSession(UserLoginVM userLoginVM, String token);

	/**
	 * Getting details from REDIS for the given key
	 * 
	 * @param key
	 *            Token
	 * @return User session object
	 */
	UserSession getDetails(String key);

	/**
	 * Deleting the session object for the given key
	 * 
	 * @param key
	 *            Token
	 */
	void deleteSession(String key);
}
