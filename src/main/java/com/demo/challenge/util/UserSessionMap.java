package com.demo.challenge.util;

import java.util.HashMap;
import java.util.Map;

import com.demo.challenge.domain.UserSession;

/**
 * Session cache class; temporary for test; REDIS to be used
 * 
 * @author 254392
 *
 */
public class UserSessionMap {

	public Map<String, UserSession> map = new HashMap<String, UserSession>();

	/**
	 * save into temporary map
	 * 
	 * @param userSession
	 * @param token
	 *            JWT token
	 */
	public void saveSession(UserSession userSession, String token) {
		map.put(token, userSession);
	}

	/**
	 * Get from temporary map
	 * 
	 * @param token
	 *            JWT token
	 * @return UserSession
	 */
	public UserSession getSession(String token) {
		return map.get(token);
	}

	/**
	 * delete from temporary map
	 * 
	 * @param token
	 *            JWT token
	 */
	public void deleteSession(String token) {
		map.remove(token);
	}
}
