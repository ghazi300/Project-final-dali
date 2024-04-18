package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class ForgotPasswordController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        // Find user by email
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            // User with provided email not found
            return ResponseEntity.notFound().build();
        }
        // Generate a password reset token (you can use a UUID or any other method)
        String resetToken = generateResetToken();

        // Save the reset token to the user in the database
        user.setResetToken(resetToken);
        usersRepository.save(user);

        // Send password reset email
        sendPasswordResetEmail(user.getEmail(), resetToken);

        // Return success response
        return ResponseEntity.ok().build();
    }

    private String generateResetToken() {
        // Implement your logic to generate a unique reset token here
        return UUID.randomUUID().toString();
    }

    private void sendPasswordResetEmail(String email, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        String resetUrl = "http://localhost:4200/reset-password?token=" + resetToken;
        message.setText("Click the link to reset your password: " + resetUrl);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
}
