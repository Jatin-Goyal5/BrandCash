package com.demo.brandbacks.dto;

import lombok.Data;

@Data
public class DecentroUpiVerificationRequest {

		String reference_id;
		
		String upi_id;
		
		String type;
//		String consumer_urn;
}
