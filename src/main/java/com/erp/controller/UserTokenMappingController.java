package com.erp.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.erp.model.AuthClientResponse;
import com.erp.model.AuthRequest;
import com.erp.model.AuthResponse;
import com.erp.model.CommonResponse;
import com.erp.model.CustomUserTokenMapping;
import com.erp.model.LogResponse;
import com.erp.service.UserTokenMappingService;
import com.erp.utils.AuthCredentialUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserTokenMappingController {

	private static Logger logger = LoggerFactory.getLogger(UserTokenMappingController.class);

	@Autowired
	private Environment environment;

	private static final String OAUTH_URL_KEY = "capitaworld.auth.base.url";

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserTokenMappingService userTokenMappingService;

	@RequestMapping(value = "/getRefreshToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getRefreshToken(@RequestBody AuthRequest authRequest) {
		
		logger.info("Enter in Get RefreshToken When User Try To Login");
		String oauthToken = environment.getRequiredProperty(OAUTH_URL_KEY).concat(AuthCredentialUtils.OAUTH_TOKEN_URI);

		String url = oauthToken.concat("?username=" + authRequest.getUsername() + "&password="
				+ authRequest.getPassword() + "&grant_type=" + authRequest.getGrant_type());

		RestTemplate restTemplate = new RestTemplate();
		try {
			// call oauth using restTemplate
			ResponseEntity<String> responseInString = restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<String>(createHeader(authRequest.getClientId(), authRequest.getClientSecret())),
					String.class);
			// convert response to AuthResponse class
			AuthResponse response = responseAdapter(responseInString.getBody());
			// create new user with refresh token and access token in
			// UserTokenMapping Table
			Map<String, Object> map = userTokenMappingService
					.createNewUserWithToken(bindResponseInCustomObject(response,authRequest));
			response.setUserType((Long)map.get("userType"));
			response.setLoginToken((int)map.get("loginToken"));
			logger.info("Successfully Generate Refreshtoken-----------------" + authRequest.getUsername());
			return response;
		} catch (Exception e) {
			logger.info("Get RefreshToken, Access Token --------------> " + authRequest.getAccessToken());
			logger.info("Get RefreshToken, UserName ------------------> " + authRequest.getUsername());
			logger.info("Get RefreshToken, Refresh Token  ------------> " + authRequest.getRefreshToken());
			logger.info("Get RefreshToken, Login Token ---------------> " + authRequest.getLoginToken());
			logger.error("Throw Exception While Generate Refresh Token When Login User");
			e.printStackTrace();
			return null;
		}

	}
	
	@RequestMapping(value = "/getTokenByLoginToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getTokenByLoginToken(@RequestBody AuthRequest authRequest) {
		logger.info("Enter in tokens by login token for domain login method");
		try {
			AuthResponse response = userTokenMappingService.getTokenByLoginToken(authRequest.getLoginToken());
			if(response != null){
				logger.info("Successfully get tokens by login token for domain login");
				return response;
			}
			logger.warn("Token not found by login token when domain login");
			return response;
		} catch (Exception e) {
			logger.info("Get Token By LoginToken, Access Token --------------> " + authRequest.getAccessToken());
			logger.info("Get Token By LoginToken, UserName ------------------> " + authRequest.getUsername());
			logger.info("Get Token By LoginToken, Refresh Token  ------------> " + authRequest.getRefreshToken());
			logger.info("Get Token By LoginToken, Login Token ---------------> " + authRequest.getLoginToken());
			logger.error("Throw Exception While Get TOkens By Login Token When Domain Login");
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/getAccessToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getAccessToken(@RequestBody AuthRequest authRequest) {
		logger.info("Enter in get accessToken");
		String checkOauthTokenUrl = environment.getRequiredProperty(OAUTH_URL_KEY)
				.concat(AuthCredentialUtils.OAUTH_TOKEN_URI);
		String url = checkOauthTokenUrl
				.concat("?grant_type=refresh_token&refresh_token=" + authRequest.getRefreshToken());
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> responseInString = restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<String>(createHeader(authRequest.getClientId(), authRequest.getClientSecret())),
					String.class);
			AuthResponse response = responseAdapter(responseInString.getBody());
			// if successfully create access token then update new access token
			// in UserTokenMappin Table
			userTokenMappingService.updateAccessToken(bindResponseInCustomObject(response, authRequest));
			logger.info("Successfully Generate Accesstoken---" + authRequest.getUsername());
			return response;
		} catch (Exception e) {
			logger.info("Get AccessToken, Access Token --------------> " + authRequest.getAccessToken());
			logger.info("Get AccessToken, UserName ------------------> " + authRequest.getUsername());
			logger.info("Get AccessToken, Refresh Token  ------------> " + authRequest.getRefreshToken());
			logger.info("Get AccessToken, Login Token ---------------> " + authRequest.getLoginToken());
			logger.error("Throw Exception While Generate New Access Token");
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/checkAccessToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthClientResponse checkAccessToken(@RequestBody AuthRequest req) {
		logger.info("Enter in check accessToken");
		// first we check oauth check token using oauth/check_token
		String oauthCheckToken = environment.getRequiredProperty(OAUTH_URL_KEY)
				.concat(AuthCredentialUtils.OAUTH_CHECK_TOKEN_URI);
		String url = oauthCheckToken.concat("?token=" + req.getAccessToken());
		// if oauth check token is valid then we check our custom user token
		// mapping table using username and accesstoken
		boolean isValid = validateAccessTokenWithOauth(req, url);
		AuthClientResponse response = new AuthClientResponse();
		if (isValid) {
			response = userTokenMappingService.checkAccessToken(req);
			if (response.getUserId() != null) {
				response.setAuthenticate(true);
			} else {
				logger.info("Check AccessToken, Access Token --------------> " + req.getAccessToken());
				logger.info("Check AccessToken, UserName ------------------> " + req.getUsername());
				logger.info("Check AccessToken, Refresh Token  ------------> " + req.getRefreshToken());
				logger.info("Check AccessToken, Login Token ---------------> " + req.getLoginToken());
				logger.warn("Check AccessToken on Every Request, Invalid Accesstoken or Refreshtoken or Logintoken");
				response.setAuthenticate(false);
			}
			return response;
		}
		logger.info("Check AccessToken, Access Token --------------> " + req.getAccessToken());
		logger.info("Check AccessToken, UserName ------------------> " + req.getUsername());
		logger.info("Check AccessToken, Refresh Token  ------------> " + req.getRefreshToken());
		logger.info("Check AccessToken, Login Token ---------------> " + req.getLoginToken());
		logger.warn("Check AccessToken on Every Request, AccessToken is Expire or Invalid");
		response.setAuthenticate(false);
		return response;
	}

	private boolean validateAccessTokenWithOauth(AuthRequest req, String url) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<String>(createHeader(req.getClientId(), req.getClientSecret())), String.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@RequestMapping(value = "/logoutUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthClientResponse logoutUser(@RequestBody AuthRequest request) {
		logger.info("Enter in logout user");
		AuthClientResponse response = new AuthClientResponse();
		response.setAuthenticate(false);
		try {
			if (!StringUtils.isEmpty(request.getAccessToken()) && !StringUtils.isEmpty(request.getUsername())) {
				if(userTokenMappingService.isUserAlreadyActive(request.getUsername(), request.getRefreshToken())){
					OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(request.getAccessToken());
					if (oAuth2AccessToken != null) {
						tokenStore.removeAccessToken(oAuth2AccessToken);
					}
				}
				userTokenMappingService.logoutuser(request);
				logger.info("Successfully logoutUser----" + request.getUsername() + "---LoginToken----" + request.getLoginToken());
				response.setAuthenticate(true);
			} else {
				logger.info("Logout User, Access Token --------------> " + request.getAccessToken());
				logger.info("Logout User, UserName ------------------> " + request.getUsername());
				logger.info("Logout User, Refresh Token  ------------> " + request.getRefreshToken());
				logger.info("Logout User, Login Token ---------------> " + request.getLoginToken());
				logger.warn("Logout User, Token and username not valid");
				response.setMessage("Token and username is not valid");
			}
			return response;
		} catch (Exception e) {
			logger.info("Logout User, Access Token --------------> " + request.getAccessToken());
			logger.info("Logout User, UserName ------------------> " + request.getUsername());
			logger.info("Logout User, Refresh Token  ------------> " + request.getRefreshToken());
			logger.info("Logout User, Login Token ---------------> " + request.getLoginToken());
			logger.error("Throw exception while logout user");
			e.printStackTrace();
			return response;
		}
	}

	/**
	 * CONVERT RESPONSE STRING TO AuthResponse OBJECT
	 * 
	 * @param response
	 * @return
	 */
	private AuthResponse responseAdapter(String response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(response, AuthResponse.class);
		} catch (Exception e) {
			logger.info("Response String -----------------> " +response);
			logger.error("Throw exception when convert string response to object");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * CREATE REQUEST HEADER FOR AUTHORIZATION
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	private HttpHeaders createHeader(String clientId, String clientSecret) {
		String authSecret = clientId + ":" + clientSecret;
		HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", "Basic " + Base64Utils.encodeToString(authSecret.getBytes()));
		headers.set("cache-control", "no-cache");
		return headers;
	}

	/**
	 * CONVERT RESPONSE TO CUSTOM OBJECT
	 * 
	 * @param response
	 * @param userName
	 * @return
	 */
	private CustomUserTokenMapping bindResponseInCustomObject(AuthResponse response, AuthRequest req) {
		CustomUserTokenMapping mapping = new CustomUserTokenMapping();
		mapping.setUserName(req.getUsername());
		mapping.setRefreshToken(response.getRefresh_token());
		mapping.setAccessToken(response.getAccess_token());
		mapping.setExpiresIn(response.getExpires_in());
		mapping.setLoginToken(req.getLoginToken());
		mapping.setUserIp(req.getUserIp());
		mapping.setUserBrowser(req.getUserBrowser());
		mapping.setDomainLogin(req.getIsDomainLogin());
		return mapping;
	}
	
	@RequestMapping(value = "/getLastLoginDetailsFromUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommonResponse> getLastLoginDetailsFromUserId(@RequestBody AuthRequest req) {
		logger.info("Enter in get last login details ");
		try {
			LogResponse logResponse = userTokenMappingService.getLastLoginDetailsFromUserId(req.getUserId());
			logger.info("Successfully get last login details");
			return new ResponseEntity<CommonResponse>(new CommonResponse(HttpStatus.OK.value(),"Successfully get result",logResponse), HttpStatus.OK);
		} catch(Exception e){
			logger.info("Throw Exception while get log details");
			e.printStackTrace();
			return new ResponseEntity<CommonResponse>(
					new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went wrong",null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
