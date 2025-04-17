package com.demo.brandbacks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.brandbacks.model.Consumer;

public interface ConsumerRepository extends MongoRepository<Consumer, String> {

}
