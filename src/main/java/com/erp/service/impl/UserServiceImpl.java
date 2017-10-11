package com.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.erp.service.UserService;
import com.erp.repositories.UserRepository;


public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository; 
	
	@Override
	public UserRequest findOneByEmail(String email) {
		User user =  userRepository.findOneByEmail(email);
		UserRequest req = new UserRequest();
		if(user != null){
			req.setId(user.getId());
			req.setEmail(user.getEmail());
			req.setPassword(user.getPassword());
			req.setUserType(user.getUserTypeMaster().getId());
		}
		return req;
	}

}
