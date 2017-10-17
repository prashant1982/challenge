package com.demo.challenge.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.demo.challenge.domain.UserSession;

/**
 * User repository implementation class
 * 
 * @author Prashant
 *
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

	private static final String KEY = "UserSession";

	private RedisTemplate<String, UserSession> redisTemplate;
	private HashOperations<String, String, UserSession> hashOps;

	@Autowired
	public UserRepositoryImpl(RedisTemplate<String, UserSession> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	public void saveSession(UserSession userSession) {
		hashOps.put(KEY, userSession.getId(), userSession);
	}

	public UserSession findSession(String id) {
		return (UserSession) hashOps.get(KEY, id);
	}

	public void deleteSession(String id) {
		hashOps.delete(KEY, id);
	}
}
