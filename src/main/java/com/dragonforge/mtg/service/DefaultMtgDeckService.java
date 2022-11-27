package com.dragonforge.mtg.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dragonforge.mtg.dao.MtgDeckDao;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Deck;
import com.dragonforge.mtg.entity.DeckRequest;
import com.dragonforge.mtg.entity.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultMtgDeckService implements MtgDeckService {
  
  @Autowired
  private MtgDeckDao mtgDeckDao;

  //Create a new Deck
  @Transactional
  @Override
  public Deck createDeck(DeckRequest deckRequest) {
    log.debug("Deck={}", deckRequest);
    
    User user = getUser(deckRequest);
    String deckName = deckRequest.getDeckName();
    List<Card> cards = getCard(deckRequest);
    double purchasePrice = 0;
    
    for (Card card : cards) {
      purchasePrice += card.getPriceUsd();
    }
    return mtgDeckDao.saveDeck(user, deckName, cards, purchasePrice);
  }

  //Returns a list of Cards from deckRequest JSON
  private List<Card> getCard(DeckRequest deckRequest) {
    return mtgDeckDao.fetchCards(deckRequest.getCards());
  }

  //Returns a User object from deckRequest JSON
  private User getUser(DeckRequest deckRequest) {
    return mtgDeckDao.fetchUser(deckRequest.getUser())
        .orElseThrow(() -> new NoSuchElementException(
            "User with username=" + deckRequest.getUser() + " was not found."));
  }
  
  //Return a User object given a username.
  private User getUser(String username) {
    return mtgDeckDao.fetchUser(username).orElseThrow(() -> 
    new NoSuchElementException("User with username=" + username + " was not found."));
  }

  //Update the cards in a deck.
  @Transactional
  @Override
  public Deck updateDeck(DeckRequest deckRequest) {
    log.debug("Deck={}", deckRequest);
    
    User user = getUser(deckRequest);
    Deck deck = mtgDeckDao.fetchDeck(user, deckRequest.getDeckName());
    List<Card> cards = getCard(deckRequest);
    double purchasePrice = 0;
    
    for (Card card : cards) {
      purchasePrice += card.getPriceUsd();
    }
    
    //Delete existing deck_cards
    mtgDeckDao.deleteDeckCards(deck);
    
    //Save new deck cards.
    return mtgDeckDao.updateDeck(user, deck, cards, purchasePrice);
  }

  //Delete a deck for the given user with the given deck name.
  @Transactional
  @Override
  public void deleteDeck(String username, String password, String deckName) {
    Long userPK = getUser(username).getUserPK();
    Long deckPK = mtgDeckDao.fetchDeck(getUser(username), deckName).getDeckPK();
    if(getUser(username).getPassword().equals(password)) {
      mtgDeckDao.deleteDeck(userPK, deckPK);
    } else {
      log.debug("The password entered is incorrect for {}", username);
    }
    
  }

  //Return cards for a specific deck
  @Transactional
  @Override
  public List<Card> fetchDeckCards(String username, String deckName) {
    User user = getUser(username);
    Deck deck = mtgDeckDao.fetchDeck(user, deckName);
    return mtgDeckDao.fetchDeckCards(deck);
  }

}
