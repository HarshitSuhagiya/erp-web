package com.erp.service;

import java.util.Map;

import com.erp.model.AuthClientResponse;
import com.erp.model.AuthRequest;
import com.erp.model.AuthResponse;
import com.erp.model.CustomUserTokenMapping;
import com.erp.model.LogResponse;

public interface UserTokenMappingService {

	public AuthClientResponse checkAccessToken(AuthRequest req);
	
	public void updateAccessToken(CustomUserTokenMapping mapping);
	
	public Map<String,Object> createNewUserWithToken(CustomUserTokenMapping mapping);
	
	public void logoutuser(AuthRequest req);
	
	public boolean isUserAlreadyActive(String userName,String refreshToken);
	
	public AuthResponse getTokenByLoginToken(Integer loginToken);
	
	public LogResponse getLastLoginDetailsFromUserId(Long userId);

}
