package com.demo.challenge.repository;

import com.demo.challenge.domain.UserSession;

/**
 * Spring data repository for managing UserSession with Redis
 * 
 * @author Prashant
 *
 */
public interface UserRepository {

	/**
	 * Save User Session object to REDIS
	 * 
	 * @param userSession
	 *            UserSession
	 */
	public void saveSession(UserSession userSession);

	/**
	 * Find UserSession for the given key
	 * 
	 * @param id
	 *            key
	 * @return UserSession object
	 */
	public UserSession findSession(String id);

	/**
	 * Delete UserSession for the given key
	 * 
	 * @param id
	 *            key
	 */
	public void deleteSession(String id);
}
