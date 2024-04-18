package com.example.demo.dto;

import lombok.Data;

@Data // Lombok annotation to create getters, setters, toString, equals, and hashCode methods
public class SessionRequest {
    private String userEmail;
    private String taskTitle; // Renamed from taskTitle to title
}
