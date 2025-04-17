package com.demo.brandbacks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.brandbacks.model.Brand;

public interface BrandRepository extends MongoRepository<Brand, String> {

	Brand findByUserId(String id);

}
