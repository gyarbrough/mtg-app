package com.dragonforge.mtg.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dragonforge.mtg.dao.MtgCardDao;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.CardRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultMtgCardService implements MtgCardService {
  
  @Autowired
  private MtgCardDao mtgCardDao;

  @Transactional
  @Override
  public List<Card> fetchCards(String searchType, String keyWord, String sortBy) {
    log.debug("searchType={}, keyWord={}, sortBy={}", searchType, keyWord, sortBy);
    return mtgCardDao.fetchCards(searchType, keyWord, sortBy);
  }

  @Transactional
  @Override
  public Card createCard(CardRequest cardRequest) {
    log.debug("Card={}", cardRequest);
    
    String cardId = cardRequest.getCardId();
    String name = cardRequest.getName();
    String releaseDate = cardRequest.getReleaseDate();
    String manaCost = cardRequest.getManaCost();
    int cmc = cardRequest.getCmc();
    String typeLine = cardRequest.getTypeLine();
    String oracleText = cardRequest.getOracleText();
    int power = cardRequest.getPower();
    int toughness = cardRequest.getToughness();
    String setCode = cardRequest.getSetCode();
    String setName = cardRequest.getSetName();
    int collectorNumber = cardRequest.getCollectorNumber();
    String rarity = cardRequest.getRarity();
    String flavorText = cardRequest.getFlavorText();
    String artist = cardRequest.getArtist();
    double priceUsd = cardRequest.getPriceUsd();
    return mtgCardDao.saveCard(cardId, name, releaseDate, manaCost, cmc, typeLine, oracleText,
        power, toughness, setCode, setName, collectorNumber, rarity, flavorText, artist, priceUsd);
  }

}
