package com.capitaworld.service.auth.model;

import java.io.Serializable;

public class MobilelAppLoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String message;
	private boolean isValid;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	
	
	
}
