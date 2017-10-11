package com.erp.config;


import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.erp.domain.User;
import com.erp.repositories.UserRepository;


@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        
    	//find user object by user name
   
        User user = userRepository.findOneByEmail(username);

        if (user == null) {
        	log.info("User " + username + " was not found in the database");
        	throw new UsernameNotFoundException("User " + username + " was not found in the database");
        } else if (!user.isSelfActive()) {
        	log.info("User " + username + " is not activated");
        	throw new UserNotActivatedException("User " + username + " is not activated");
        }

        //send user name and password to user security class for check valid or not
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.EMPTY_LIST);

    }

}

