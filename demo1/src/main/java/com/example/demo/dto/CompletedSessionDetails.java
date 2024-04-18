package com.example.demo.dto;

import java.time.LocalDateTime;

public class CompletedSessionDetails {
    private String userEmail;
    private String taskTitle; // Renamed from taskName to taskTitle
    private LocalDateTime endTime;
    private String complexity; // Store complexity as a string

    // Getters and setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTaskTitle() { // Renamed from getTaskName to getTaskTitle
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) { // Renamed from setTaskName to setTaskTitle
        this.taskTitle = taskTitle;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }
}
