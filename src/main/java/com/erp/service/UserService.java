package com.erp.service;

import com.erp.model.UserRequest;

public interface UserService {

	public UserRequest findOneByEmail(String email);
}
