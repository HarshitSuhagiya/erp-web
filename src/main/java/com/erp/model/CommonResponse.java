package com.capitaworld.service.auth.model;

import java.io.Serializable;

public class CommonResponse  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private int status;
	private String message;
	private Object data;
	
	public CommonResponse(){
		
	}
	
	public CommonResponse(int status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
