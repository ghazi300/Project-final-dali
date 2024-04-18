package com.example.demo.services;




import com.example.demo.entity.Poker_Cards;

import java.util.List;

public interface PokerCardService {
    Poker_Cards getPokerCardById(Long pokerCardId);
    List<Poker_Cards> getAllPokerCards();
}
