package com.demo.brandbacks.model;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.demo.brandbacks.dto.UpiRequest;

import lombok.Data;

@Data
@Document(collection = "users") // Specify the MongoDB collection name
public class User {
	@Id
	private String id; 
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String password;

	private Set<Role> roles;

	// Getters and setters
}