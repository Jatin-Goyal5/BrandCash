package com.demo.brandbacks.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection= "user_otp")
public class UserOtp extends DateAudit {

	@Id
	private String id;
	
	private String phoneNumber;
	private String otp;
	
	private boolean isVerified;
	
    private LocalDateTime expiresAt; 
	
}
