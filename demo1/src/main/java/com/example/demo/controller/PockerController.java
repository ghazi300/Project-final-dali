package com.example.demo.controller;


import com.example.demo.entity.Poker_Cards;
import com.example.demo.repository.PockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokercards")
@CrossOrigin(origins = "http://localhost:4200")
public class PockerController {

    @Autowired
    private PockerRepository pockerRepository;

    // Récupérer toutes les cartes
    @GetMapping("/getAll")
    public List<Poker_Cards> getAllPokerCards() {
        return pockerRepository.findAll();
    }

    // Récupérer une carte par son ID
    @GetMapping("/get/{id}")
    public Poker_Cards getPokerCardById(@PathVariable Long id) {
        return pockerRepository.findById(id).orElse(null);
    }

    // Ajouter une nouvelle carte
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Poker_Cards addPokerCard(@RequestBody Poker_Cards pokerCard) {
        return pockerRepository.save(pokerCard);
    }

    // Mettre à jour une carte existante
    @PutMapping("/put/{id}")
    public Poker_Cards updatePokerCard(@PathVariable Long id, @RequestBody Poker_Cards updatedPokerCard) {
        if (pockerRepository.existsById(id)) {
            updatedPokerCard.setId(id);
            return pockerRepository.save(updatedPokerCard);
        } else {
            // Gérer le cas où la carte n'existe pas
            return null;
        }
    }

    // Supprimer une carte par son ID
    @DeleteMapping("/delete/{id}")
    public void deletePokerCard(@PathVariable Long id) {
        pockerRepository.deleteById(id);
    }
}
