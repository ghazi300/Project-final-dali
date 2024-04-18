package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class UserController {
    private final UsersRepository repository;

    public UserController(UsersRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    Users newUser(@RequestBody Users user) {
        return repository.save(user);
    }


    @GetMapping
    public List<Users> getAllUser() {
        return repository.findAll();
    }
}
