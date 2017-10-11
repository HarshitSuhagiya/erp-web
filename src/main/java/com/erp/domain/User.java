package com.erp.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;

	@Column(name="client_name")
	private String clientName;

	@Column(name="created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	private String email;

	@Column(name="is_active")
	private boolean isActive;
	
	@Column(name="is_self_active")
	private boolean isSelfActive;

//	@Column(name="last_access_id")
//	private Long lastAccessId;

	private String mobile;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="mobile_updated_date")
	private Date mobileUpdatedDate;

	@Column(name="modified_by")
	private Long modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="password_updated_date")
	private Date passwordUpdatedDate;

	@Column(name="service_provider_id")
	private Long serviceProviderId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sign_up_date")
	private Date signUpDate;

	private String username;
	
	@Column(name="terms_accepted")
	private boolean termsAccepted;
	
	@Column(name="otp_varified")
	private boolean otpVarified;
	
	@Column(name="email_varified")
	private boolean emailVarified;
	
	public boolean isOtpVarified() {
		return otpVarified;
	}

	public void setOtpVarified(boolean otpVarified) {
		this.otpVarified = otpVarified;
	}

	public boolean isEmailVarified() {
		return emailVarified;
	}

	public void setEmailVarified(boolean emailVarified) {
		this.emailVarified = emailVarified;
	}

	public boolean isTermsAccepted() {
		return termsAccepted;
	}

	public void setTermsAccepted(boolean termsAccepted) {
		this.termsAccepted = termsAccepted;
	}



	public User() {
	}

	public User(Long userId) {
		// TODO Auto-generated constructor stub
		this.id = userId;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isSelfActive() {
		return isSelfActive;
	}

	public void setSelfActive(boolean isSelfActive) {
		this.isSelfActive = isSelfActive;
	}

//	public Long getLastAccessId() {
//		return lastAccessId;
//	}
//
//	public void setLastAccessId(Long lastAccessId) {
//		this.lastAccessId = lastAccessId;
//	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getMobileUpdatedDate() {
		return this.mobileUpdatedDate;
	}

	public void setMobileUpdatedDate(Date mobileUpdatedDate) {
		this.mobileUpdatedDate = mobileUpdatedDate;
	}

	public Long getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPasswordUpdatedDate() {
		return this.passwordUpdatedDate;
	}

	public void setPasswordUpdatedDate(Date passwordUpdatedDate) {
		this.passwordUpdatedDate = passwordUpdatedDate;
	}

	public Long getServiceProviderId() {
		return this.serviceProviderId;
	}

	public void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public Date getSignUpDate() {
		return this.signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	

}