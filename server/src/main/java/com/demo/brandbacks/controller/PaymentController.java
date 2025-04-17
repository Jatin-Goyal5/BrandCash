package com.demo.brandbacks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.brandbacks.dto.GenericResponseBuilder;
import com.demo.brandbacks.dto.UpiRequest;
import com.demo.brandbacks.security.JwtUtil;
import com.demo.brandbacks.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	 @Autowired
    private JwtUtil jwtUtil;

	
	 @PostMapping("/verify")
	 public ResponseEntity<GenericResponseBuilder> verifyUpi(@RequestHeader(value = "Authorization", required= true) String authorizationHeader ,@RequestBody UpiRequest upiRequest ) {
		 	
		 	String jwt = authorizationHeader.substring(7);
		 	String username = jwtUtil.extractUsername(jwt);
	        
	        
	        // Call UPI verification service here (e.g., Decentro)
	        boolean isValid = paymentService.verifyUpiId(upiRequest, username);
	        
	        if (isValid) {
	            return new ResponseEntity(GenericResponseBuilder.builder().message("UPI Id is valid").build(), HttpStatus.OK);
	        } else {
	            return new ResponseEntity(GenericResponseBuilder.builder().message("Invalid UPI ID").build(), HttpStatus.BAD_REQUEST);
	        }
	    }

}
