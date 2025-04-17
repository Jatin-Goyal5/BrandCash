package com.demo.brandbacks.util;

import java.net.URI;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.brandbacks.dto.DecentroTransferRequest;
import com.demo.brandbacks.dto.DecentroUpiVerificationRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HttpRequestUtil {

	
	
	@Autowired
	UrlMappingUtils urlmappingUtil;
	
	public ResponseEntity<String> verifyUPI(DecentroUpiVerificationRequest  decentroUpiVerificationRequest) {
		try {
			
			
			RestTemplate restTemplate = new RestTemplate();
	        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());  // Ensure JSON conversion

			URL upiVerificationURL = new URL(urlmappingUtil.getUPIVerificationURL());
			URI upiVerificationURI = upiVerificationURL.toURI();
			HttpHeaders headers = new HttpHeaders();
			
			headers.add("client_id", "Deciwood_staging");
			headers.add("client_secret", "3myetu0ldX7Firk3pciCFuZF6sLnCCte");
			headers.add("module_secret", "s7ikZs3E4viJFMQe25shkue3UlLHsBGY");
			
			HttpEntity<DecentroUpiVerificationRequest> request = new HttpEntity<DecentroUpiVerificationRequest>(decentroUpiVerificationRequest,
					headers);
			return restTemplate.postForEntity(upiVerificationURI, request, String.class);
//			log.info("response {}", response);
//			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ResponseEntity<String> transferToUPI(DecentroTransferRequest  decentroTransferRequest) {
		try {
			
			
			RestTemplate restTemplate = new RestTemplate();
	        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());  // Ensure JSON conversion

			URL upiVerificationURL = new URL("https://in.staging.decentro.tech/core_banking/money_transfer/initiate");
			URI upiVerificationURI = upiVerificationURL.toURI();
			HttpHeaders headers = new HttpHeaders();
			
			headers.add("client_id", "Deciwood_staging");
			headers.add("client_secret", "3myetu0ldX7Firk3pciCFuZF6sLnCCte");
			headers.add("module_secret", "4fv2IVQL7rp5dazklkBgtJXGf6FvGOlD");
			headers.add("provider_secret", "HCVVAmo9AfsF15N8U7tPoah3wpYNpN3F");
			
			HttpEntity<DecentroTransferRequest> request = new HttpEntity<DecentroTransferRequest>(decentroTransferRequest,
					headers);
			return restTemplate.postForEntity(upiVerificationURI, request, String.class);
//			log.info("response {}", response);
//			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	
	
}
