package com.demo.challenge.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * Entity object for usersession
 * @author Prashant
 *
 */
//@RedisHash("usersession")
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
	private String id;
	private String user;
	private String deviceType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}
