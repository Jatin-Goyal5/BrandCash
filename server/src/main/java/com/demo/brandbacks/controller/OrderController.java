package com.demo.brandbacks.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.brandbacks.constants.Constants;
import com.demo.brandbacks.dto.GenericResponseBuilder;
import com.demo.brandbacks.dto.OrderListDto;
import com.demo.brandbacks.dto.OrderRequest;
import com.demo.brandbacks.dto.OrderResponse;
import com.demo.brandbacks.security.UserPrincipal;
import com.demo.brandbacks.service.OrderService;
import com.demo.brandbacks.service.UserPrincipalService;
import com.demo.brandbacks.util.EmailUtil;

import io.jsonwebtoken.lang.Arrays;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService; // Service to handle business logic
	
	@Autowired
	UserPrincipalService userPrincipalService;

	@Autowired
	EmailUtil emailSender;
	// Endpoint to place a new order
	@PostMapping("/place")
	public ResponseEntity<?> placeOrder(			@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
				@Validated @RequestBody OrderRequest orderRequest) {
		try {
			String jwt = authorizationHeader.substring(7);

			UserPrincipal userDetails =(UserPrincipal) userPrincipalService.fetchUserPrincipal(jwt);
			
			OrderResponse orderResponse = orderService.saveOrder(orderRequest, userDetails);
			
			emailSender.sendEmail(orderResponse.getOrderId());

			return new ResponseEntity<>(GenericResponseBuilder.builder().message("Order placed successfully").data(orderResponse)
					.status(Constants.Status.SUCCESS.name()).build(), HttpStatus.OK);
		} catch (Exception exp) {
			return new ResponseEntity<>(GenericResponseBuilder.builder().message(exp.getMessage())
					.status(Constants.Status.FAILURE.name()).build(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> getList(@RequestHeader(value = "Authorization", required = true) String authorizationHeader
) {
		try {

			String jwt = authorizationHeader.substring(7);

			UserPrincipal userDetails =(UserPrincipal) userPrincipalService.fetchUserPrincipal(jwt);

			List<OrderListDto> orders = orderService.getAll(userDetails);


			return new ResponseEntity<>(GenericResponseBuilder.builder().message("Order found successfully").data(orders)
					.status(Constants.Status.SUCCESS.name()).build(), HttpStatus.OK);
		} catch (Exception exp) {
			return new ResponseEntity<>(GenericResponseBuilder.builder().message(exp.getMessage())
					.status(Constants.Status.FAILURE.name()).build(), HttpStatus.EXPECTATION_FAILED);
		}
	}
 
	 @GetMapping("/{orderId}/qrcodes/pdf")
	    public ResponseEntity<Resource> downloadOrderQrCodes(@PathVariable String orderId) {
	        try {
	        	List<String> qrCodes = java.util.Arrays.asList("asddas21","dsadadd2");
	            ByteArrayOutputStream pdfStream = orderService.generateQrCodesPdf(qrCodes);

	            // Convert ByteArrayOutputStream to InputStreamResource
	            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdfStream.toByteArray()));

	            // Set response headers
	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-" + orderId + "-qrcodes.pdf");
	            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

	            return ResponseEntity.ok()
	                    .headers(headers)
	                    .body(resource);
//	        } catch (Exception ex) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } 
	        catch (Exception ex) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
}
