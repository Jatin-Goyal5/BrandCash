package com.demo.brandbacks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.brandbacks.dto.GenericResponseBuilder;
import com.demo.brandbacks.dto.QrConfigDto;
import com.demo.brandbacks.model.QrConfig;
import com.demo.brandbacks.service.QrConfigService;

@RestController
@RequestMapping("/api/qr-config")
public class QrConfigController {

	@Autowired
	private QrConfigService qrConfigService;
	
	  @PostMapping("/save")
	    public ResponseEntity<?> saveQrConfig(@Validated @RequestBody QrConfigDto qrConfigDto) {
	      try {
	    	  
	    	  QrConfig qrConfig =  qrConfigService.saveQrConfig(qrConfigDto);
	    	  return new ResponseEntity<>(GenericResponseBuilder.builder().message("QR config saved successfully").data(qrConfig).build(), HttpStatus.OK);
	      } catch(Exception exp) {

	    	  return new ResponseEntity<>(GenericResponseBuilder.builder().message(exp.getMessage()).build(), HttpStatus.EXPECTATION_FAILED);
	      }
	    }
}
