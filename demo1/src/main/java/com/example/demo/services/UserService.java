package com.example.demo.services;



import com.example.demo.entity.Users;

import java.util.List;

public interface UserService {
    Users getUserByEmail(String email);
    List<String> getAllUserEmails();
    // Ajoutez d'autres méthodes au besoin
}
