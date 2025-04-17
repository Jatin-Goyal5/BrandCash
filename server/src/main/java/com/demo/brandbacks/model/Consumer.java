package com.demo.brandbacks.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.demo.brandbacks.dto.UpiRequest;

import lombok.Data;

@Data
@Document(collection="consumer")
public class Consumer {

	@Id
	String id;
	
	String userId;

	List<UpiRequest> upids;
}
