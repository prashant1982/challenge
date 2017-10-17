package com.demo.challenge.web.rest.vm;

import javax.validation.constraints.NotNull;

/**
 * View Model object for storing a user's credentials.
 * @author Prashant
 */
public class UserLoginVM {

    @NotNull
    private String username;

    @NotNull
    private String deviceType;

    public UserLoginVM() {
    	 // Empty constructor needed for Jackson.
	}
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Override
    public String toString() {
        return "UserLoginVM{" +
            "username='" + username + '\'' +
            ", deviceType=" + deviceType +
            '}';
    }
}
