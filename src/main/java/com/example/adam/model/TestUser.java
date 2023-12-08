package com.example.adam.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test_user")
public class TestUser  {

    @Id
    private int id;

    private String username;
    private String password;
    private  String role;

    public TestUser() {
    }

    // Getters and setters for username and password
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password= password;
    }

}
