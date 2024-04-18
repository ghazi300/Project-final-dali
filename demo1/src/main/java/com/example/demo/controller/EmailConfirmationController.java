package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailConfirmationController {

    private final UsersRepository userRepository;
    private final JavaMailSender emailSender;

    @Autowired
    public EmailConfirmationController(UsersRepository usersRepository, JavaMailSender emailSender) {
        this.userRepository = usersRepository;
        this.emailSender = emailSender;
    }

    @GetMapping("/confirm")
    @Transactional
    public String confirmRegistration(@RequestParam("token") String token) {
        Users user = userRepository.findByConfirmationToken(token);
        if (user != null) {
            user.setEnabled(true);
            user.setConfirmationToken(null); // Clear the confirmation token
            userRepository.save(user);
            return "redirect:http://localhost:4200/login";
        } else {
            return "redirect:http://localhost:4200/login?error=invalidToken";
        }
    }

    public void sendConfirmationEmail(Users user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Email Confirmation");
        String confirmationUrl = "http://localhost:4200/api/auth/confirm?token=" + user.getConfirmationToken();
        message.setText("Click the link to confirm your email: " + confirmationUrl);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
}
