package com.demo.brandbacks.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "orders")
public class Order extends DateAudit {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String id;
    private String address;
    private String productId;
    private Long quantity;
    private double cashbackAmount;
    private double totalAmount;
    @Field("userId") 
    private String userId;
    
    private String status;

}