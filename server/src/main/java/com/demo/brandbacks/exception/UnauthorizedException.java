package com.demo.brandbacks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 401
public class UnauthorizedException extends Exception{

	private static final long serialVersionUID = -4111539867750498771L;

	public UnauthorizedException(String message) {
		super(message);
	}
	
	public UnauthorizedException(String message, Throwable exp) {
		super(message, exp);
	}
}
