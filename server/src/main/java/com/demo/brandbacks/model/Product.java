package com.demo.brandbacks.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection= "product")
public class Product extends DateAudit{

	@Id
	private String id;
	
	private String name;
	private String description;
	private String logoURL;
	private Double price;
}
