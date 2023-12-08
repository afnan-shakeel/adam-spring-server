package com.example.adam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adam.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    public User findByUsername(String username);

}
