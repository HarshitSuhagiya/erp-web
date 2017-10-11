package com.erp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="user_token_mapping")
public class UserTokenMapping {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_type")
	private Long userType;
	
	@Column(name="refresh_token")
	private String refreshToken;
	
	@Column(name="access_token")
	private String accessToken;
	
	@Column(name="login_token")
	private Integer loginToken;
	
	@Column(name="expires_in")
	private String expiresIn;
	
	@Column(name="active")
	private boolean active;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_date")
	private Date loginDate;
	
	@Column(name="user_ip")
	private String userIp;
	
	@Column(name="user_browser")
	private String userBrowser;
	
	@Column(name="domain_isactive")
	private boolean domainIsactive;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Integer getLoginToken() {
		return loginToken;
	}
	public void setLoginToken(Integer loginToken) {
		this.loginToken = loginToken;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getUserBrowser() {
		return userBrowser;
	}
	public void setUserBrowser(String userBrowser) {
		this.userBrowser = userBrowser;
	}
	public Long getUserType() {
		return userType;
	}
	public void setUserType(Long userType) {
		this.userType = userType;
	}
	public boolean isDomainIsactive() {
		return domainIsactive;
	}
	public void setDomainIsactive(boolean domainIsactive) {
		this.domainIsactive = domainIsactive;
	}
	
	
	
	
}
