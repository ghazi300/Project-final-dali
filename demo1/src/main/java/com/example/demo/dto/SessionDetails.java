package com.example.demo.dto;

import java.time.LocalDateTime;

public class SessionDetails {
    private String userEmail;
    private String taskTitle; // Renamed from taskTitle to taskName
    private LocalDateTime endTime;
    private Long sessionId; // Added session ID field

    // Default constructor
    public SessionDetails() {
    }

    // Constructor without session ID
    public SessionDetails(String userEmail, String taskTitle, LocalDateTime endTime) {
        this.userEmail = userEmail;
        this.taskTitle = taskTitle;
        this.endTime = endTime;
    }

    // Constructor with all fields
    public SessionDetails(Long sessionId, String userEmail, String taskTitle, LocalDateTime endTime) {
        this.sessionId = sessionId;
        this.userEmail = userEmail;
        this.taskTitle = taskTitle;
        this.endTime = endTime;
    }

    // Getters and Setters
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTaskTitle() { // Renamed from getTaskTitle to getTaskName
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) { // Renamed from setTaskTitle to setTaskName
        this.taskTitle = taskTitle;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
