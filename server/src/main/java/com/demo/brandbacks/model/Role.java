package com.demo.brandbacks.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
public class Role {

	public Role(String name) {
		this.name = name;
	}

	private String name;
	
}
