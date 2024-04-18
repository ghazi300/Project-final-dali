package com.example.demo.controller;

import com.example.demo.dto.CompletedSessionDetails;
import com.example.demo.dto.SessionDetails;
import com.example.demo.dto.SessionRequest;
import com.example.demo.entity.Poker_Cards;
import com.example.demo.entity.Session;
import com.example.demo.entity.Users;
import com.example.demo.services.PokerCardService;
import com.example.demo.services.SessionService;
import com.example.demo.services.TaskService;
import com.example.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sessions")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {
    private static final Logger log = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PokerCardService pokerCardService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/createsess")
    public ResponseEntity<Session> createSession(@RequestBody SessionRequest request) {
        log.info("Received request to create session: {}", request);

        // Récupérer l'utilisateur par son e-mail
        Users users = userService.getUserByEmail(request.getUserEmail());

        // Récupérer la tâche par son titre
        com.example.demo.entity.Task task = taskService.getTaskByTitle(request.getTaskTitle());

        // Vérifier si l'utilisateur et la tâche existent
        if (users == null || task == null) {
            log.warn("User or task not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Récupérer la carte de poker par son ID

        LocalDateTime endTime = LocalDateTime.now().plusMinutes(1);
        Session session = sessionService.createSession(users, task, endTime);

        log.info("Created session: {}", session);

        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    @GetMapping("/user-emails")
    public ResponseEntity<List<String>> getAllUserEmails() {
        List<String> userEmails = userService.getAllUserEmails();
        return ResponseEntity.ok(userEmails);
    }

    @GetMapping("/task-titles")
    public ResponseEntity<List<String>> getAllTaskTitles() {
        List<String> taskTitles = taskService.getAllTaskTitles();
        return ResponseEntity.ok(taskTitles);
    }

    @GetMapping("/all-sessions")
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/sessions-with-details")
    public ResponseEntity<List<SessionDetails>> getAllSessionsWithDetails() {
        List<SessionDetails> sessionDetailsList = sessionService.getAllSessionsWithDetails();
        return ResponseEntity.ok(sessionDetailsList);
    }

    @GetMapping("/active-sessions")
    public ResponseEntity<List<SessionDetails>> getActiveSessions() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<SessionDetails> activeSessions = sessionService.getActiveSessions(currentTime);
        return ResponseEntity.ok(activeSessions);
    }

    @PostMapping("/choose-poker-card")
    public ResponseEntity<Session> choosePokerCard(@RequestParam Long sessionId, @RequestParam Long pokerCardId) {
        log.info("Choosing poker card {} for session {}", pokerCardId, sessionId);

        // Vérifier si la session est active
        if (!sessionService.isSessionActive(sessionId)) {
            log.warn("Session {} is not active", sessionId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Sélectionner la carte de poker par son ID
        Poker_Cards pokerCard = pokerCardService.getPokerCardById(pokerCardId);

        // Vérifier si la carte de poker existe
        if (pokerCard == null) {
            log.warn("Poker card {} not found", pokerCardId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Sélectionner la session par son ID
        Session session = sessionService.getSessionById(sessionId);

        // Vérifier si la session existe
        if (session == null) {
            log.warn("Session {} not found", sessionId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Mettre à jour la carte de poker pour la session
        session.setPokerCard(pokerCard);
        sessionService.updateSession(session);

        log.info("Poker card {} chosen for session {}", pokerCardId, sessionId);

        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @GetMapping("/completed-sessions")
    public ResponseEntity<List<CompletedSessionDetails>> getCompletedSessions() {
        List<CompletedSessionDetails> completedSessions = sessionService.getCompletedSessionDetails();
        return ResponseEntity.ok(completedSessions);
    }

    @GetMapping("/cards-for-session")
    public ResponseEntity<List<Poker_Cards>> getCardsForSession() {
        List<Poker_Cards> cards = pokerCardService.getAllPokerCards();

        if (cards.isEmpty()) {
            log.warn("No poker cards found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Returning {} poker cards", cards.size());
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/session-details/{sessionId}")
    public ResponseEntity<SessionDetails> getSessionDetailsById(@PathVariable Long sessionId) {
        SessionDetails sessionDetails = sessionService.getSessionDetailsById(sessionId);

        if (sessionDetails != null) {
            return ResponseEntity.ok(sessionDetails);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
