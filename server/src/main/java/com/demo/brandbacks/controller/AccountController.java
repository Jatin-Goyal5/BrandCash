package com.demo.brandbacks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.brandbacks.constants.Constants;
import com.demo.brandbacks.dto.GenericResponseBuilder;
import com.demo.brandbacks.dto.VirtualAccountDto;
import com.demo.brandbacks.service.PaymentService;

@RestController
public class AccountController {

	@Autowired
	PaymentService paymentService;
	
	public ResponseEntity<?> createAccount(@RequestBody VirtualAccountDto virtualAccountDto){
		
		try {
			paymentService.createVirtualAccount(virtualAccountDto);
			return new ResponseEntity<>(GenericResponseBuilder.builder().message("Account Created successfully").status(Constants.Status.SUCCESS.name()).build(), HttpStatus.OK );

			
		}catch(Exception exp) {
			return new ResponseEntity<>(GenericResponseBuilder.builder().message(exp.getMessage()).status(Constants.Status.FAILURE.name()).build(), HttpStatus.EXPECTATION_FAILED );
		}
	}
	
}
