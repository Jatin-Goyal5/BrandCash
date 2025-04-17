package com.demo.brandbacks.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.brandbacks.constants.Constants;
import com.demo.brandbacks.dto.BrandRequestDto;
import com.demo.brandbacks.dto.UpiRequest;
import com.demo.brandbacks.exception.ResourceNotFoundException;
import com.demo.brandbacks.model.Brand;
import com.demo.brandbacks.model.Consumer;
import com.demo.brandbacks.model.Role;
import com.demo.brandbacks.model.User;
import com.demo.brandbacks.repository.BrandRepository;
import com.demo.brandbacks.repository.ConsumerRepository;
import com.demo.brandbacks.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ConsumerRepository consumerRepo;

	@Autowired
	BrandRepository brandRepo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public void saveCustomer(String phoneNumber) {
		Optional<User> customer = userRepository.findByPhoneNumber(phoneNumber);
		if (customer.isPresent()) {
			return;
		} else {
			User newCustomer = new User();
			newCustomer.setPhoneNumber(phoneNumber);
			Set<Role> set = new HashSet<>();
			set.add(new Role(Constants.Role.CONSUMER.name()));
			newCustomer.setRoles(set);
			userRepository.save(newCustomer);

			Consumer consumer = new Consumer();
			consumer.setUserId(newCustomer.getId());
			consumerRepo.save(consumer);

		}
	}

	public User getUser(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isEmpty()) {
			return null;
		} else {
			Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
			if (user.isPresent()) {
				return user.get();
			} else {
				return null;
			}
		}
	}

	public void addUpiId(UpiRequest upiRequest, String username) throws ResourceNotFoundException {

		User user = getUser(username);
		if (user == null) {
			throw new ResourceNotFoundException("user not found!");
		}

		Consumer consumer = consumerRepo.findById(user.getId()).get();
		List<UpiRequest> upiIds = consumer.getUpids();
		upiIds.add(upiRequest);
		consumerRepo.save(consumer);

	}

	public void validateBrandRegistration(BrandRequestDto brandRequestDto) throws Exception {
		if (brandRequestDto.getCompanyName().isBlank()) {
			throw new Exception("Company name cannot be blank!");
		} else if (brandRequestDto.getEmail().isBlank()) {
			throw new Exception("Email cannot be blank!");
		} else if (brandRequestDto.getFirstName().isBlank() || brandRequestDto.getLastName().isBlank()) {
			throw new Exception("Name is required");
		} else if (brandRequestDto.getPassword().isBlank()) {
			throw new Exception("Password cannot be blank");
		} else if (brandRequestDto.getLastName().isBlank()) {
			throw new Exception("Last Name cannot be blank");
		}else if(userRepository.findByEmail(brandRequestDto.getEmail()).isPresent()) {
				throw new Exception("user already present!");
		}
	}

	public void registerBrand(BrandRequestDto brandRequestDto) throws Exception {
		try {
			
				User newUser = saveUser(brandRequestDto.getEmail(), brandRequestDto.getFirstName(),
						brandRequestDto.getLastName(), brandRequestDto.getPassword(), Constants.Role.BRAND.name());
				Brand brand = new Brand();
				brand.setUserId(newUser.getId());
				brand.setCompanyName(brandRequestDto.getCompanyName());
				brandRepo.save(brand);
		} catch (Exception exp) {
			throw exp;
		}
	}

	User saveUser(String email, String firstName, String lastName, String password, String role) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(passwordEncoder.encode(password));
		Set<Role> set = new HashSet<>();
		set.add(new Role(role));
		user.setRoles(set);
		userRepository.save(user);
		return user;
	}
}
