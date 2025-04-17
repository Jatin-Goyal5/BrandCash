package com.demo.brandbacks.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.brandbacks.model.User;
import com.demo.brandbacks.repository.UserRepository;
import com.demo.brandbacks.security.UserPrincipal;

@Service
public class CustomDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);
		if (user.isPresent()) {
//			return org.springframework.security.core.userdetails.User.builder().username(user.get().getEmail())
//					.password(user.get().getPassword())
//					  .roles(user.get().getRoles() == null ? new String[]{} :
//	                        user.get().getRoles().stream().map(Role::getName).toArray(String[]::new))
//	              
//					.build();
			
			return UserPrincipal.create(username, user.get());
		}else
		{
			user = userRepository.findByPhoneNumber(username); 
			if(user.isPresent()) {
				return UserPrincipal.create(username, user.get());

			}
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
