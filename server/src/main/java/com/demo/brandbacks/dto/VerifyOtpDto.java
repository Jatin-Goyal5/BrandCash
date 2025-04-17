package com.demo.brandbacks.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VerifyOtpDto {

	private String phoneNumber;
	private String otp;
}
