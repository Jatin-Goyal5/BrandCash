package com.demo.brandbacks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.brandbacks.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
