package com.demo.brandbacks.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class OtplessService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String OTPLESS_API_URL = "https://api.otpless.com/send-otp"; // Replace with actual Otpless URL
    private final String VERIFY_OTP_URL = "https://api.otpless.com/verify-otp"; // Replace with actual Otpless URL
    private final String API_KEY = "mPNWHSxf1k6F9Zd0hqKwjB8yRver7uDVzaUGO2bXtC3A4E5lQLpElcFjSbXZm9vhkJTwMLzqU6ftunVB"; // Get from Otpless dashboard
    private final String CLIENT_ID = "8G5D4J4X2XOTGUHSAIR4GFUJJA2L3SYL";
    private final String SECRET_KEY = "vlhkz27oprc54crnvagr3eb22iu6zfdq";
    
    
    public void sendOtp(String phoneNumber) {

     }
    
    public Boolean verifyOTP(String phoneNumber, String OTP) {
    	return false;
    }

	public boolean verifyOtp(String phoneNumber, String otp) {
		// TODO Auto-generated method stub
		return true;
	}

        
        
}
