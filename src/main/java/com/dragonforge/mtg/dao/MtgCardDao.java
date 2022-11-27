package com.dragonforge.mtg.dao;

import java.util.List;
import com.dragonforge.mtg.entity.Card;

public interface MtgCardDao {

  List<Card> fetchCards(String searchType, String keyWord, String sortBy);

  Card saveCard(String cardId, String name, String releaseDate, String manaCost, int cmc,
      String typeLine, String oracleText, int power, int toughness, String setCode, String setName,
      int collectorNumber, String rarity, String flavorText, String artist, double priceUsd);

}
