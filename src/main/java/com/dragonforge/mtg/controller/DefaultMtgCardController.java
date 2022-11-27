package com.dragonforge.mtg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.CardRequest;
import com.dragonforge.mtg.service.MtgCardService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultMtgCardController implements MtgCardController {

  @Autowired
  private MtgCardService mtgCardService;
  
  @Override
  public List<Card> fetchCards(String searchType,String keyWord, String sortBy) {
    log.debug("searchType={}, keyWord={}, sortBy={}", searchType, keyWord, sortBy);
    return mtgCardService.fetchCards(searchType, keyWord, sortBy);
  }

  @Override
  public Card createCard(CardRequest cardRequest) {
    log.debug("Card={}", cardRequest);
    return mtgCardService.createCard(cardRequest);
  }



}
