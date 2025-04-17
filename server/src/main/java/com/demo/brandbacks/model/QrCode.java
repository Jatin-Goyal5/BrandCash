package com.demo.brandbacks.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "qr_code")
public class QrCode {
	


	@Id
	private String id;
	
	private String qrCodeId;
	private String productId;
	private Integer status; // 0- inactive 1- active  2- claimed 
	
	private String orderId;
	
}
