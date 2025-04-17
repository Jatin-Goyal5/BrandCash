package com.demo.brandbacks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.brandbacks.model.QrConfig;

public interface QrConfigRepository  extends MongoRepository<QrConfig, String> {
}