package com.demo.brandbacks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecentroTransferRequest {

		@JsonProperty("reference_id")
	 	private String referenceId;
		
		@JsonProperty("purpose_message")
	    private String purposeMessage;
		
		@JsonProperty("from_account")
	    private String fromAccount;
		
		@JsonProperty("to_upi")
		private String toUpi;
	    
		@JsonProperty("transfer_type")
		private String transferType;
	    
		@JsonProperty("transfer_amount")
		private String transferAmount;
		
		@JsonProperty("payee_name")
		private String payeeName;
	    
		@JsonProperty("beneficiary_details")
		private BeneficiaryDetailsDTO beneficiaryDetails;
	    
}
