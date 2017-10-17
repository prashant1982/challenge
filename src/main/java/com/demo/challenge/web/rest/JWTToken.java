package com.demo.challenge.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to return as body in JWT Authentication.
 * 
 * @author Prashant
 */
public class JWTToken {

	private String idToken;

	public JWTToken(String idToken) {
		this.idToken = idToken;
	}

	@JsonProperty("id_token")
	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
}
