package com.demo.brandbacks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.brandbacks.constants.Constants;
import com.demo.brandbacks.dto.GenericResponseBuilder;
import com.demo.brandbacks.dto.ProductDto;
import com.demo.brandbacks.exception.UnauthorizedException;
import com.demo.brandbacks.model.Product;
import com.demo.brandbacks.security.JwtUtil;
import com.demo.brandbacks.security.UserPrincipal;
import com.demo.brandbacks.service.CustomDetailService;
import com.demo.brandbacks.service.ProductService;
import com.demo.brandbacks.service.UserPrincipalService;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	CustomDetailService userdetailService;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	UserPrincipalService userPrincipalService;

	@GetMapping("/get")
	public ResponseEntity<?> getProducts(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader
			) throws UnauthorizedException{
		try {
			String jwt = authorizationHeader.substring(7);

			UserPrincipal userDetails =(UserPrincipal) userPrincipalService.fetchUserPrincipal(jwt);

			
			List<Product> products =productService.getProducts(userDetails);

			return new ResponseEntity<>(GenericResponseBuilder.builder().message("Product list")
					.data(products).status(Constants.Status.SUCCESS.name()).build(), HttpStatus.OK);
		} catch (Exception exp) {
			return new ResponseEntity<>(GenericResponseBuilder.builder().message(exp.getMessage())
					.status(Constants.Status.FAILURE.name()).build(), HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PostMapping("/save")
	public ResponseEntity<?> saveProduct(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@RequestBody ProductDto productDto) {
		try {
			String jwt = authorizationHeader.substring(7);

			UserPrincipal userDetails =(UserPrincipal) userPrincipalService.fetchUserPrincipal(jwt);

			Product product = productService.saveProduct(productDto, userDetails);
			return new ResponseEntity<>(GenericResponseBuilder.builder().message("Product saved successfully")
					.data(product).status(Constants.Status.SUCCESS.name()).build(), HttpStatus.OK);

		} catch (Exception exp) {
			return new ResponseEntity<>(GenericResponseBuilder.builder().message(exp.getMessage())
					.status(Constants.Status.FAILURE.name()).build(), HttpStatus.EXPECTATION_FAILED);
		}

	}
}
