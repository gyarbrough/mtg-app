package com.dragonforge.mtg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Deck;
import com.dragonforge.mtg.entity.DeckRequest;
import com.dragonforge.mtg.service.MtgDeckService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultMtgDeckController implements MtgDeckController {
  
  @Autowired
  private MtgDeckService mtgDeckService;

  @Override
  public Deck createDeck(DeckRequest deckRequest) {
    log.debug("Deck={}", deckRequest);
    return mtgDeckService.createDeck(deckRequest);
  }

  @Override
  public Deck updateDeck(DeckRequest deckRequest) {
    log.debug("Deck={}", deckRequest);
    return mtgDeckService.updateDeck(deckRequest);
  }

  @Override
  public void deleteDeck(String username, String password, String deckName) {
    log.debug("username={}, password={}, deckName={}", username, password, deckName);
    mtgDeckService.deleteDeck(username, password, deckName);
  }

  @Override
  public List<Card> fetchDeckCards(String username, String deckName) {
    log.debug("username={}, deckName={}", username, deckName);
    return mtgDeckService.fetchDeckCards(username, deckName);
  }



}
