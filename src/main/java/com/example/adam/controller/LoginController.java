package com.example.adam.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.adam.config.JwtUtils;
import com.example.adam.model.CustomUserDetails;
import com.example.adam.model.TestUser;
import com.example.adam.model.dto.AuthenticationRequest;
import com.example.adam.repository.TestUserRepository;
import com.example.adam.service.TestUserService;
import com.example.adam.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    private final TestUserRepository testUserRepository;
    @Autowired
    private TestUserService testUserService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody AuthenticationRequest request ){
        TestUser user = testUserService.getUserByUsername(request.getUsername());
        if(user == null){
            return ResponseEntity.status(404).body("user not found");
        }
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails _user = new CustomUserDetails(user);
        if(user != null){
            return ResponseEntity.ok(jwtUtils.generateToken(_user));
        }
        return ResponseEntity.status(400).body("error logging in");
    }

}
