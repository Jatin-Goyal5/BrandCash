package com.demo.brandbacks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.brandbacks.constants.Constants;
import com.demo.brandbacks.dto.GenericResponseBuilder;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<GenericResponseBuilder> handleUnauthorizedException(HttpServletRequest request,
			UnauthorizedException ex) {
		return new ResponseEntity<>(GenericResponseBuilder.builder().status(Constants.Status.FAILURE.name()).message(ex.getMessage()).build(), HttpStatus.UNAUTHORIZED);
	}

}