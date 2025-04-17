package com.demo.brandbacks.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.brandbacks.model.QrCode;

public interface QrcodeRepository extends MongoRepository<QrCode, String> {
	
	Optional<QrCode> findByQrCodeId(String qrCodeId);
}
