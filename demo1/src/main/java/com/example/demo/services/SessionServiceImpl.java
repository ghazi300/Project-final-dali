package com.example.demo.services;

import com.example.demo.dto.CompletedSessionDetails;
import com.example.demo.dto.SessionDetails;
import com.example.demo.entity.Session;
import com.example.demo.entity.Users;
import com.example.demo.repository.PockerRepository;
import com.example.demo.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final PockerRepository pokerCardRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, PockerRepository pokerCardRepository) {
        this.sessionRepository = sessionRepository;
        this.pokerCardRepository = pokerCardRepository;
    }

    @Override
    public Session createSession(Users users, com.example.demo.entity.Task task, LocalDateTime endTime) {
        Session session = new Session(users, task, endTime);
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public List<SessionDetails> getAllSessionsWithDetails() {
        List<Session> sessions = sessionRepository.findAll();
        List<SessionDetails> sessionDetailsList = new ArrayList<>();

        for (Session session : sessions) {
            Users user = session.getUser();
            com.example.demo.entity.Task task = session.getTask();

            if (user != null && task != null) {
                String userEmail = user.getEmail();
                String taskTitle = task.getTitle(); // Utilisation de getTitle() au lieu de getName()
                LocalDateTime endTime = session.getEndTime();

                sessionDetailsList.add(new SessionDetails(session.getId(), userEmail, taskTitle, endTime));
            }
        }

        return sessionDetailsList;
    }

    @Override
    public List<SessionDetails> getActiveSessions(LocalDateTime currentTime) {
        List<Session> activeSessions = sessionRepository.findAllByEndTimeAfter(currentTime);
        List<SessionDetails> activeSessionDetails = new ArrayList<>();

        for (Session session : activeSessions) {
            SessionDetails sessionDetails = new SessionDetails();
            sessionDetails.setSessionId(session.getId()); // Ajout de l'ID de session

            sessionDetails.setEndTime(session.getEndTime());
            sessionDetails.setUserEmail(session.getUser().getEmail());
            sessionDetails.setTaskTitle(session.getTask().getTitle()); // Utilisation de getTitle() au lieu de getName()
            activeSessionDetails.add(sessionDetails);
        }

        return activeSessionDetails;
    }

    public boolean isSessionActive(Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElse(null);
        if (session == null) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        return session.getEndTime().isAfter(currentTime);
    }

    @Override
    public Session getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId).orElse(null);
    }

    @Override
    public Session updateSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public SessionDetails getSessionDetailsById(Long sessionId) {
        Optional<Session> optionalSession = sessionRepository.findById(sessionId);

        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            String userEmail = session.getUser().getEmail();
            String taskTitle = session.getTask().getTitle(); // Utilisation de getTitle() au lieu de getName()
            LocalDateTime endTime = session.getEndTime();
            Long id = session.getId();

            return new SessionDetails(id, userEmail, taskTitle, endTime);
        } else {
            return null; // Ou lancez une exception appropriée si vous préférez
        }
    }

    @Override
    public List<CompletedSessionDetails> getCompletedSessionDetails() {
        List<Session> sessions = sessionRepository.findAllByEndTimeBefore(LocalDateTime.now());
        List<CompletedSessionDetails> detailsList = new ArrayList<>();

        for (Session session : sessions) {
            CompletedSessionDetails details = new CompletedSessionDetails();
            details.setUserEmail(session.getUser().getEmail());
            details.setTaskTitle(session.getTask().getTitle()); // Utilisation de getTitle() au lieu de getName()
            details.setEndTime(session.getEndTime());

            // Vérifiez si la session a une carte de poker
            if (session.getPokerCard() != null) {
                // Récupérez la complexité de la carte de poker
                int complexityValue = session.getPokerCard().getComplexityValue();
                // Convertissez en chaîne et définissez dans CompletedSessionDetails
                details.setComplexity(String.valueOf(complexityValue));
            } else {
                // S'il n'y a pas de carte de poker, définissez complexité à null ou une valeur par défaut
                details.setComplexity(null);
            }

            detailsList.add(details);
        }

        return detailsList;
    }
}
