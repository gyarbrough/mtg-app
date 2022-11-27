package com.dragonforge.mtg.dao;

import java.util.List;
import java.util.Optional;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Deck;
import com.dragonforge.mtg.entity.User;

public interface MtgDeckDao {

  Deck saveDeck(User user, String deckName, List<Card> cards, double purchasePrice);

  List<Card> fetchCards(List<String> cards);

  Optional<User> fetchUser(String user);

  void deleteDeck(Long userPK, Long deckPK);

  void deleteDeckCards(Deck deck);

  Deck updateDeck(User user, Deck deck, List<Card> cards, double purchasePrice);

  Deck fetchDeck(User user, String deckName);
  
  List<Card> fetchDeckCards(Deck deck);

}
