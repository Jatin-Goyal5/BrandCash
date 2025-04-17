package com.demo.brandbacks.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.brandbacks.dto.BeneficiaryDetailsDTO;
import com.demo.brandbacks.dto.DecentroTransferRequest;
import com.demo.brandbacks.dto.DecentroUpiVerificationRequest;
import com.demo.brandbacks.dto.UpiRequest;
import com.demo.brandbacks.dto.VirtualAccountDto;
import com.demo.brandbacks.util.HttpRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	HttpRequestUtil httpRequestUtil;
	
	public boolean verifyUpiId(UpiRequest upiRequest, String username) {
		// TODO Auto-generated method stub
		try {
			
			ResponseEntity<String> response = httpRequestUtil.verifyUPI(getDecentroUpiVerificationRequest(upiRequest.getUpiId())); 
			if(response == null  ) {
				return false;
			}else {
				if(response.getStatusCode().is2xxSuccessful()) {
					// TODO : commening below code for now  
//					userService.addUpiId(upiRequest, username);
					
					 ObjectMapper mapper = new ObjectMapper();
			            Map<String, Object> jsonResponse = mapper.readValue(response.getBody(), Map.class);

			            // Extract main status
			            String mainStatus = (String) jsonResponse.get("status");

			            // Extract nested data map and verify the status
			            Map<String, Object> data = (Map<String, Object>) jsonResponse.get("data");
			            String dataStatus = (String) data.get("status");

			            if ("SUCCESS".equals(mainStatus) && "VALID".equals(dataStatus)) {
			                // Add UPI ID if verification is successful and valid
			             					
					
					
					ResponseEntity<String> response2 = httpRequestUtil.transferToUPI(getTransferRequestDto(upiRequest.getUpiId(), upiRequest.getPayeeName())); 
					log.info("{}",response2);
					return true;
			            }
			            return false;
				}
			}
		}
//		}catch(ResourceNotFoundException exp) {
//			return false;
//		}
		catch(  Exception exp ) {
			exp.printStackTrace();
		
		}
		
		return false;
	}
	
	public DecentroUpiVerificationRequest getDecentroUpiVerificationRequest(String upiId) {
		String referenceId ="REF"+  UUID.randomUUID().toString();
        
		DecentroUpiVerificationRequest decentroUpiVerificationRequest = new DecentroUpiVerificationRequest();
		decentroUpiVerificationRequest.setReference_id(referenceId);
		decentroUpiVerificationRequest.setType("Basic");
		decentroUpiVerificationRequest.setUpi_id (upiId);
		return decentroUpiVerificationRequest;
		
		
	}
	
	public DecentroTransferRequest getTransferRequestDto(String upiId, String payeeName) {
		String referenceId ="REF"+  UUID.randomUUID().toString();
		BeneficiaryDetailsDTO beneficiaryDetailsDTO = new BeneficiaryDetailsDTO(payeeName);
		DecentroTransferRequest decentroTransferRequest = new DecentroTransferRequest();
		
		decentroTransferRequest.setReferenceId(referenceId);
		decentroTransferRequest.setPurposeMessage("testmessage");
		decentroTransferRequest.setFromAccount("962390823382775476");
		decentroTransferRequest.setToUpi(upiId);
		decentroTransferRequest.setPayeeName(payeeName);
		decentroTransferRequest.setTransferAmount("2");
		decentroTransferRequest.setTransferType("UPI");
		decentroTransferRequest.setBeneficiaryDetails(beneficiaryDetailsDTO);
		return decentroTransferRequest;
		
	}

	public void createVirtualAccount(VirtualAccountDto virtualAccountDto) {
		// TODO Auto-generated method stub
		
	}

	
	
}
