package com.dragonforge.mtg.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
  private Long cardPK;
  private String cardId;
  private String name;
  private String releaseDate;
  private String manaCost;
  private int cmc;
  private String typeLine;
  private String oracleText;
  private int power;
  private int toughness;
  private String setCode;
  private String setName;
  private int collectorNumber;
  private String rarity;
  private String flavorText;
  private String artist;
  private double priceUsd;

}

