package com.demo.brandbacks.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.demo.brandbacks.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserPrincipal implements UserDetails {

	private String id;
	
	@JsonIgnore
	private String email;

	@JsonIgnore
	private String password;
	
	@JsonIgnore
	private String username;

	private Collection<? extends GrantedAuthority> authorities;
	
	String lastName;
	
	String firstName;
	
	public UserPrincipal() {
	}
	public UserPrincipal(Collection<? extends GrantedAuthority> authorities, String username, String password, String id, String firstName, String lastName, String email) {
		this.authorities = authorities;
		this.username = username;
		this.password = password;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public static UserPrincipal create(String username, User user) {
		 List<GrantedAuthority> authorities = user.getRoles().stream()
	                .map(role -> new SimpleGrantedAuthority(role.getName()))
	                .collect(Collectors.toList());
		return new UserPrincipal(authorities, username, user.getPassword(), user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
		}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	
}
