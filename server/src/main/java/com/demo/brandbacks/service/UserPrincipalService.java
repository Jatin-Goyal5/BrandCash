package com.demo.brandbacks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.brandbacks.security.JwtUtil;
import com.demo.brandbacks.security.UserPrincipal;

@Service
public class UserPrincipalService {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomDetailService customUserDetailService;
	
	public UserPrincipal fetchUserPrincipal(String authorizationToken) {
		
		String username = jwtUtil.extractUsername(authorizationToken);
		UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService.loadUserByUsername(username);
		
		return userPrincipal;
		
	}
}
