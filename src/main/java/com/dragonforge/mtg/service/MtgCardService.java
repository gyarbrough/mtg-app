package com.dragonforge.mtg.service;

import java.util.List;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.CardRequest;

public interface MtgCardService {

  List<Card> fetchCards(String searchType, String keyWord, String sortBy);

  Card createCard(CardRequest cardRequest);

}
