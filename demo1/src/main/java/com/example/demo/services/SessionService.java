package com.example.demo.services;

import com.example.demo.dto.CompletedSessionDetails;
import com.example.demo.dto.SessionDetails;
import com.example.demo.entity.Session;
import com.example.demo.entity.Users;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionService {
    Session createSession(Users users, com.example.demo.entity.Task task, LocalDateTime endTime);
    List<Session> getAllSessions();

    List<SessionDetails> getAllSessionsWithDetails();
    List<SessionDetails> getActiveSessions(LocalDateTime currentTime);
    boolean isSessionActive(Long sessionId);
    Session getSessionById(Long sessionId);
    Session updateSession(Session session);
    SessionDetails getSessionDetailsById(Long sessionId);
    List<CompletedSessionDetails> getCompletedSessionDetails();

    // Other methods as needed
}
