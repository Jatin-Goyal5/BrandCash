package com.demo.brandbacks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value=Include.NON_NULL)
public class GenericResponseBuilder {

	private String status;
	private String message;
	private Object data;
}
