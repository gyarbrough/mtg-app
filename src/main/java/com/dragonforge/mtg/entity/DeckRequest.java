package com.dragonforge.mtg.entity;

import java.util.List;
import lombok.Data;

@Data
public class DeckRequest {
  
  private String user;
  private String deckName;
  private List<String> cards;

}
