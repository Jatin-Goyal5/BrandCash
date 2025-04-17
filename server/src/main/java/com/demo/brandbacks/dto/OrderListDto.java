package com.demo.brandbacks.dto;

import lombok.Data;

@Data
public class OrderListDto {
	 private String id;
	    private String address;
	    private Long quantity;
	    private double cashbackAmount;
	    private double totalAmount;
	    private String userId;
	    private String status;
	    private String productName; 
}
