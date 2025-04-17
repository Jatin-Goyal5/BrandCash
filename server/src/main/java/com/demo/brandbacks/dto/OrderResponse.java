package com.demo.brandbacks.dto;

import lombok.Data;

@Data
public class OrderResponse {
	private String orderId;
	private String status;
	private double totalAmount;

}
