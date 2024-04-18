package com.example.demo.repository;

import com.example.demo.entity.Rolee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Rolee, Long> {
    Optional<Rolee> findByName(String name);
}