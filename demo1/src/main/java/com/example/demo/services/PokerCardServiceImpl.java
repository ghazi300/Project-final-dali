package com.example.demo.services;


import com.example.demo.entity.Poker_Cards;
import com.example.demo.repository.PockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokerCardServiceImpl implements PokerCardService {
    @Autowired
    private PockerRepository pockerRepository; // Vérifiez ici

    @Override
    public Poker_Cards getPokerCardById(Long pokerCardId) {
        return pockerRepository.findById(pokerCardId).orElse(null);
    }
    @Override
    public List<Poker_Cards> getAllPokerCards() {
        return pockerRepository.findAll(); // Récupère toutes les cartes de poker enregistrées dans la base de données
    }
}
