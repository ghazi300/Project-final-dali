package com.example.demo.controller;
import java.util.*;

import com.example.demo.entity.Rolee;
import com.example.demo.dto.LoginDto;
import com.example.demo.dto.SignUpDto;
import com.example.demo.entity.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession httpSession; // Autowire HttpSession
    @Autowired
    private EmailConfirmationController emailConfirmationController; // Autowire HttpSession

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        // Check if the user is banned
        Optional<Users> optionalUser = usersRepository.findByUsername(loginDto.getUsernameOrEmail());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user.isBanned()) {
                // Return a forbidden response if the user is banned
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("User is banned. Unable to login.");
            }
        } else {
            // Return a not found response if the user does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found.");
        }

        // Proceed with authentication for non-banned users
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the authenticated user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Set user ID and roles in session
        httpSession.setAttribute("userId", userDetails.getUsername());
        httpSession.setAttribute("roles", userDetails.getAuthorities());

        // Extract user roles
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // You can include additional information in the response JSON if needed
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User signed-in successfully!");
        response.put("userId", userDetails.getUsername()); // Add user ID to the response
        response.put("roles", roles); // Add user roles to the response

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        // Check if username exists in the DB
        if (usersRepository.existsByUsername(signUpDto.getUsername())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Username is already taken!"));
        }

        // Check if email exists in the DB
        if (usersRepository.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Email is already taken!"));
        }

        // Create user object
        Users user = new Users();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        // Generate confirmation token
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);
        // Assign role "ROLE_USER"
        Rolee userRolee = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("User Role not found"));
        user.setRolees(Collections.singleton(userRolee));
        // Save user
        usersRepository.save(user);

        // Send confirmation email
        emailConfirmationController.sendConfirmationEmail(user);

        return ResponseEntity.ok().body(Collections.singletonMap("message", "User registered successfully"));
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            // Retrieve the user by ID
            Optional<Users> userOptional = usersRepository.findById(userId);
            if (!userOptional.isPresent()) {
                return ResponseEntity.notFound().build(); // User not found
            }

            Users user = userOptional.get();

            // Remove all roles associated with the user
            user.setRolees(null); // Or you can clear the roles set

            // Delete the user
            usersRepository.deleteById(userId);

            return ResponseEntity.ok().body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user: " + e.getMessage());
        }
    }
    @PostMapping("/admin/users/{userId}/ban")
    public String banUser(@PathVariable Long userId) {
        Optional<Users> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            // Toggle the banned status of the user
            user.setBanned(!user.isBanned());
            // Save the updated user entity
            usersRepository.save(user);
        }
        return "redirect:/admin/users"; // Redirect to user list page
    }
}
