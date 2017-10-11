package com.erp.config;

import org.springframework.util.DigestUtils;

public class PasswordEncoderUtils implements org.springframework.security.crypto.password.PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		String rawPasswordDecode = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
		return rawPasswordDecode.equals(encodedPassword);
	}

	
}
