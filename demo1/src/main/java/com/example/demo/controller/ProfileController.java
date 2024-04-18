package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileController {

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        // Retrieve authenticated user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Retrieve user details from the authentication object
        String username = authentication.getName();
        // Extract user roles
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // You may need to retrieve additional user information from your database
        // For demonstration purposes, let's just return the username and role
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("username", username);
        userProfile.put("roles", roles);

        return ResponseEntity.ok().body(userProfile);
    }
}
