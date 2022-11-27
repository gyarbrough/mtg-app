package com.dragonforge.mtg.entity;

import lombok.Data;

@Data
public class CardRequest {
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
