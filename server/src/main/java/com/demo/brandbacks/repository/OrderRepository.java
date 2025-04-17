package com.demo.brandbacks.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.brandbacks.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	@Query(value = "{ 'userId': ?0 }", sort = "{ '_id': -1 }")
	List<Order> findByUserId(String id);

}
