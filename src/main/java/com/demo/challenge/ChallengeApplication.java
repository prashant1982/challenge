package com.demo.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.challenge.util.UserSessionMap;

/**
 * Application Main
 * @author Prashant
 *
 */
@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}
	
	/** Using this for storing the user session as REDIS requires server to run */
	@Bean(name = "userSessionMap")
	public UserSessionMap getUserSessionMap(){
		return new UserSessionMap();
	}
}
