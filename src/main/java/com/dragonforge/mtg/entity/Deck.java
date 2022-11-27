package com.dragonforge.mtg.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Deck {
  private Long deckPK;
  private User user;
  private String deckName;
  private List<Card> cards;
  private double purchasePrice;

}
