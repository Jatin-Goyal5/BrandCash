package com.demo.brandbacks.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.brandbacks.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);
}