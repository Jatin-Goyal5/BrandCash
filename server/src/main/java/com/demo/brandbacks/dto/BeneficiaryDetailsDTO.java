package com.demo.brandbacks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BeneficiaryDetailsDTO {
	
	public BeneficiaryDetailsDTO(String payeeName2) {
		// TODO Auto-generated constructor stub
		this.payeeName = payeeName2;
	}

	@JsonProperty("payee_name")
	private String payeeName;

}
