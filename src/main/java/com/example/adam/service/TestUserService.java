package com.example.adam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adam.model.TestUser;
import com.example.adam.repository.TestUserRepository;

@Service
public class TestUserService {
    @Autowired
    private TestUserRepository testUserRepository;


    public TestUser getUserByUsername(String username) {
        return testUserRepository.findByUsername(username);
    }
}
