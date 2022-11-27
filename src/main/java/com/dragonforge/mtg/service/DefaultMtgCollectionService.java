package com.dragonforge.mtg.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dragonforge.mtg.dao.MtgCollectionDao;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Collection;
import com.dragonforge.mtg.entity.CollectionRequest;
import com.dragonforge.mtg.entity.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultMtgCollectionService implements MtgCollectionService {
 
  @Autowired
  private MtgCollectionDao mtgCollectionDao;
  
  
  //Initiate a collection for a user.
  @Transactional
  @Override
  public Collection createCollection(CollectionRequest collectionRequest) {
    log.debug("Collection={}", collectionRequest);
    
    User user = getUser(collectionRequest);
    List<Card> cards = getCard(collectionRequest);
    double collectionValue = 0;
    
    for(Card card : cards) {
      collectionValue += card.getPriceUsd();
    }
    return mtgCollectionDao.saveCollection(user, cards, collectionValue);
  }

  //Fetch cards from collectionRequest.
  private List<Card> getCard(CollectionRequest collectionRequest) {
    return mtgCollectionDao.fetchCards(collectionRequest.getCards());
  }

  //Fetch User from collectionRequest.
  private User getUser(CollectionRequest collectionRequest) {
    return mtgCollectionDao.fetchUser(collectionRequest.getUser())
        .orElseThrow(() -> new NoSuchElementException("User with ID="
            + collectionRequest.getUser() + " was not found."));
  }

  //Update user's card collection.
  @Transactional
  @Override
  public Collection updateCollection(CollectionRequest collectionRequest) {
    log.debug("Collection={}", collectionRequest);
    
    Long userPK = getUser(collectionRequest).getUserPK();
    Collection collection = mtgCollectionDao.fetchCollection(userPK);
    List<Card> cards = getCard(collectionRequest);
    double collectionValue = 0;
    
    for (Card card : cards) {
      collectionValue += card.getPriceUsd();
    }
    
    //Delete existing deck_cards
    mtgCollectionDao.deleteCollectionCards(collection);
    
    //Save new deck cards.
    return mtgCollectionDao.updateCollection(collection, cards, collectionValue);
  }

  //Fetch Collection cards.
  @Transactional
  @Override
  public List<Card> fetchCollectionCards(String username) {
    log.debug("username={}", username);
    Long userPK = getUser(username).getUserPK();
    
    return mtgCollectionDao.fetchCollectionCards(
        mtgCollectionDao.fetchCollection(userPK).getCollectionPK());
  }

  //Fetch User from String.
  private User getUser(String username) {
    return mtgCollectionDao.fetchUser(username)
        .orElseThrow(() -> new NoSuchElementException("User with ID="
            + username + " was not found."));
  }

}
