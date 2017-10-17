package com.demo.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.demo.challenge.domain.UserSession;

/**
 * Redis configuration class
 * 
 * @author Prashant
 *
 */
@EnableAutoConfiguration
@Configuration
@EnableCaching
public class RedisConfig /*extends CachingConfigurerSupport*/ {

	@Value("${spring.redis.host}")
	private String redisHost;
	
	@Value("${spring.redis.port}")
    private int redisPort;
	
	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(redisHost);
		jedisConnectionFactory.setPort(redisPort);
		jedisConnectionFactory.setUsePool(true);
		return jedisConnectionFactory;
	}

	/*@Bean
	public RedisSerializer redisStringSerializer() {
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		return stringRedisSerializer;
	}*/

	@Bean//(name = "redisTemplate")
	public RedisTemplate<String, UserSession> redisTemplateUserSession() {
		RedisTemplate<String, UserSession> redisTemplate = new RedisTemplate<String, UserSession>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		redisTemplate.setDefaultSerializer(redisSerializer);
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplateUserSession());
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}
}
