package com.example.demo.repository;


import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findAll();
    Users findByEmail(String email);

    Optional<Users> findByUsernameOrEmail(String username, String email);
    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.rolees WHERE u.username = :username")
    Optional<Users> findByUsernameWithRoles(@Param("username") String username);


    Users findByConfirmationToken(String token);
}
