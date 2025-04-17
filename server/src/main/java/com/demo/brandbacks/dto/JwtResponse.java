package com.demo.brandbacks.dto;

import lombok.Data;

@Data
public class JwtResponse {

	String id;
	String accessToken;
	String email;
	String firstName;
	String lastName;
}
