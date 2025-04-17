package com.demo.brandbacks.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="brand")
public class Brand {

	@Id
	String id;
	
	String userId;
	
	String companyName;
	
	List<Product> products; 
	
}
