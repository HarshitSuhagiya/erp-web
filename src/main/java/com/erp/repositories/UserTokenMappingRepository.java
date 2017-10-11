package com.capitaworld.service.auth.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.capitaworld.service.auth.domain.UserTokenMapping;

public interface UserTokenMappingRepository extends JpaRepository<UserTokenMapping,Long> {

	@Query("select o from UserTokenMapping o where o.userName =:username and o.accessToken =:accessToken and o.loginToken =:loginToken and o.active = true")
	public List<UserTokenMapping> checkAccessToken(@Param("username") String username,@Param("accessToken")  String accessToken,@Param("loginToken") Integer loginToken);
	
	@Query("select o from UserTokenMapping o where o.userName =:username and o.active = true")
	public List<UserTokenMapping> findByActiveUserName(@Param("username") String username);
	
	@Modifying
	@Query("update UserTokenMapping set accessToken =:accessToken,expiresIn =:expireIn,refreshToken =:refreshToken where userName =:username and loginToken =:loginToken and active = true")
	public void updateAccessToken(@Param("username") String username,@Param("refreshToken") String refreshToken,@Param("accessToken") String accessToken,@Param("expireIn")  String expireIn,@Param("loginToken") Integer loginToken);
	
	@Modifying
	@Query("update UserTokenMapping set active = false where userId =:userId and active = true")
	public void inactiveLastUserLogin(@Param("userId") Long userId);
	
	@Transactional
	@Modifying
	@Query("update UserTokenMapping um set um.active = false where um.userName =:username and um.refreshToken =:refreshToken and um.loginToken =:loginToken and um.active = true")
	public int logoutUser(@Param("username") String username,@Param("refreshToken") String refreshToken,@Param("loginToken") Integer loginToken);
	
	@Query("select count(*) from UserTokenMapping o where o.userName =:username and o.refreshToken =:refreshToken and o.active = true")
	public Long isUserAlreadyActive(@Param("username") String username,@Param("refreshToken") String refreshToken);
	
	@Query("select count(*) from UserTokenMapping o where o.loginToken =:loginToken and o.active = true")
	public Long checkLoginToken(@Param("loginToken") Integer loginToken);
	
	@Query("select o from UserTokenMapping o where o.loginToken =:loginToken and o.domainIsactive = true")
	public UserTokenMapping getTokenByLoginToken(@Param("loginToken") Integer loginToken);
	
	@Modifying
	@Query("update UserTokenMapping set domainIsactive = false where loginToken =:loginToken")
	public void inactiveDomainLogin(@Param("loginToken") Integer loginToken);
	
	@Query("select o from UserTokenMapping o where o.id IN (select max(usr.id) from UserTokenMapping usr where usr.userId =:userId)")
	public UserTokenMapping getLastLoginDetailsFromUserId(@Param("userId") Long userId);
	
}

