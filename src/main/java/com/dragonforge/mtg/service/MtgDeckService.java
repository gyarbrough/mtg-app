package com.dragonforge.mtg.service;

import java.util.List;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Deck;
import com.dragonforge.mtg.entity.DeckRequest;

public interface MtgDeckService {

  Deck createDeck(DeckRequest deckRequest);

  Deck updateDeck(DeckRequest deckRequest);

  void deleteDeck(String username, String password, String deckName);

  List<Card> fetchDeckCards(String username, String deckName);

}
