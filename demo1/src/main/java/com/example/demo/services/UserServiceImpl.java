package com.example.demo.services;


import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UsersRepository userRepository;

    @Autowired
    public UserServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public List<String> getAllUserEmails() {
        // Implémentez la logique pour récupérer toutes les adresses e-mail des utilisateurs
        // Par exemple, vous pouvez récupérer tous les utilisateurs et extraire leurs adresses e-mail
        List<Users> usersList = userRepository.findAll();
        List<String> userEmails = new ArrayList<>();
        for (Users user : usersList) {
            userEmails.add(user.getEmail());
        }
        return userEmails;
    }
}
