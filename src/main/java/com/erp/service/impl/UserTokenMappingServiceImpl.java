package com.capitaworld.service.auth.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitaworld.service.auth.domain.User;
import com.capitaworld.service.auth.domain.UserTokenMapping;
import com.capitaworld.service.auth.model.AuthClientResponse;
import com.capitaworld.service.auth.model.AuthRequest;
import com.capitaworld.service.auth.model.AuthResponse;
import com.capitaworld.service.auth.model.CustomUserTokenMapping;
import com.capitaworld.service.auth.model.LogResponse;
import com.capitaworld.service.auth.repositories.UserRepository;
import com.capitaworld.service.auth.repositories.UserTokenMappingRepository;
import com.capitaworld.service.auth.service.UserTokenMappingService;

@Service
@Transactional
public class UserTokenMappingServiceImpl implements UserTokenMappingService {

	public static Random random = new Random();
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserTokenMappingRepository userTokenMappingRepository;
	
	@Override
	public AuthClientResponse checkAccessToken(AuthRequest req) {
		List<UserTokenMapping> userTokenMapping = userTokenMappingRepository.checkAccessToken(req.getUsername(), req.getAccessToken(),req.getLoginToken());
		AuthClientResponse response = new AuthClientResponse();
		if(userTokenMapping.isEmpty() || userTokenMapping == null) {
			return response;
		} else {
			response.setUserId(userTokenMapping.get(0).getUserId());
			response.setUserType(userTokenMapping.get(0).getUserType());
			return response;
		}
	}

	@Override
	public void updateAccessToken(CustomUserTokenMapping mapping) {
		userTokenMappingRepository.updateAccessToken(mapping.getUserName(),mapping.getRefreshToken(),mapping.getAccessToken(),mapping.getExpiresIn(),mapping.getLoginToken());
	}

	
	@Override
	public Map<String,Object> createNewUserWithToken(CustomUserTokenMapping mapping) {
		
		Map<String,Object> map = new HashMap<>();
		
		UserTokenMapping userTokenMapping = new UserTokenMapping();
		BeanUtils.copyProperties(mapping, userTokenMapping, "id","active");
		User user = userRepository.findOneByEmail(mapping.getUserName());
		userTokenMapping.setActive(true);
		userTokenMapping.setLoginDate(new Date());
		userTokenMapping.setUserIp(mapping.getUserIp());
		userTokenMapping.setUserBrowser(mapping.getUserBrowser());
		userTokenMapping.setDomainIsactive(mapping.isDomainLogin());
		Integer randomNumber = getRandomNumber();
		userTokenMapping.setLoginToken(randomNumber);
		if(user != null){
			userTokenMapping.setUserId(user.getId());
			userTokenMapping.setUserType(user.getUserTypeMaster().getId());
			map.put("userType", user.getUserTypeMaster().getId());
		}
		userTokenMappingRepository.save(userTokenMapping);
		map.put("loginToken", randomNumber);
		return map;
	}
	
	@Override
	public AuthResponse getTokenByLoginToken(Integer loginToken){
		UserTokenMapping userTokenMapping = userTokenMappingRepository.getTokenByLoginToken(loginToken);
		AuthResponse response = new AuthResponse();
		if(userTokenMapping != null){
			userTokenMappingRepository.inactiveDomainLogin(loginToken);
			response.setAccess_token(userTokenMapping.getAccessToken());
			response.setLoginToken(userTokenMapping.getLoginToken());
			response.setRefresh_token(userTokenMapping.getRefreshToken());
			response.setExpires_in(String.valueOf(userTokenMapping.getExpiresIn()));
			response.setUserType(userTokenMapping.getUserType());
			response.setEmail(userTokenMapping.getUserName());
		}
		return response;
	}
	
	
	@Override
	public void logoutuser(AuthRequest req){
		userTokenMappingRepository.logoutUser(req.getUsername(),req.getRefreshToken(),req.getLoginToken());
	}
	
	@Override
	public boolean isUserAlreadyActive(String userName,String refreshToken){
		Long count = userTokenMappingRepository.isUserAlreadyActive(userName, refreshToken);
		return count > 0;
	}

	private Integer getRandomNumber() {
		Integer randomNumber = 1000000 + random.nextInt(9000000);
		Long count = userTokenMappingRepository.checkLoginToken(randomNumber);
		if(count > 0) {
			getRandomNumber();
		}
		return 1000000 + random.nextInt(9000000);
	}
	
	@Override
	public LogResponse getLastLoginDetailsFromUserId(Long userId){
		LogResponse response = new LogResponse();
		UserTokenMapping lastLoginDetails = userTokenMappingRepository.getLastLoginDetailsFromUserId(userId);
		if(lastLoginDetails != null){
			response.setUserEmail(lastLoginDetails.getUserName());
			response.setLoginDate(lastLoginDetails.getLoginDate());
			response.setUserId(lastLoginDetails.getUserId());
		}
		return response;
	}
	
}
