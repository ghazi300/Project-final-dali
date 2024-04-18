package com.example.demo.repository;


import com.example.demo.entity.Poker_Cards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PockerRepository extends JpaRepository<Poker_Cards, Long> {
    // You can add custom query methods if needed
}
