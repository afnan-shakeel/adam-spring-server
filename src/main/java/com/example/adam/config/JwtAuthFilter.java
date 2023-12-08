package com.example.adam.config;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.adam.model.CustomUserDetails;
import com.example.adam.model.TestUser;
import com.example.adam.repository.TestUserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private TestUserRepository testUserRepository;
    @Autowired
    private JwtUtils jwtUtils;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    throws ServletException, IOException{


        final String authHeader = request.getHeader("Authorization");
        final String username;
        final String token;
        System.out.println("authHeader: " + authHeader);
        
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        
        token = authHeader.substring(7);
        username = jwtUtils.extractUsername(token);
        System.out.println("username: " + username);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            TestUser user = testUserRepository.findByUsername(username);
            UserDetails userDetails = new CustomUserDetails(user);
            System.out.println("userDetails: " + userDetails);
            final boolean isTokenValid = jwtUtils.validateToken(token, userDetails);
            if(isTokenValid){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
