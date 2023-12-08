package com.example.adam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.adam.model.TestUser;

public interface TestUserRepository extends JpaRepository<TestUser, Long> {
    
    public TestUser findByUsername(String username);
}
