package com.demo.brandbacks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.brandbacks.dto.GenericResponseBuilder;
import com.demo.brandbacks.dto.QrcodeDto;
import com.demo.brandbacks.model.QrCode;
import com.demo.brandbacks.service.QrCodeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/qr-code")
public class QrcodeController {

	@Autowired
	QrCodeService qrCodeService;
	
	@PostMapping("/verify")
	private ResponseEntity<GenericResponseBuilder> getQrCodeInfo(@RequestBody QrcodeDto qrCodeDto) {
		try {
			log.info("{}",qrCodeDto);
			QrCode qrcode = qrCodeService.getQrCode(qrCodeDto.getQrCodeId());
			if(qrcode != null) {
				return new ResponseEntity<GenericResponseBuilder> (GenericResponseBuilder.builder().message("Qr code founded").data(qrcode).build(), HttpStatus.OK);
			}else {
				return new ResponseEntity<GenericResponseBuilder> (GenericResponseBuilder.builder().message("Qr Not found").build(), HttpStatus.NO_CONTENT);
					
			}
		}catch(Exception exp) {
			return  new ResponseEntity<GenericResponseBuilder> (GenericResponseBuilder.builder().message("Something went wrong ").build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/create")
	private ResponseEntity<GenericResponseBuilder> createdummyQrCode() {
		try {

			qrCodeService.saveQrcode();
			
		}catch(Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}
}
