package com.dragonforge.mtg.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Collection {
  private Long collectionPK;
  private User user;
  private List<Card> cards;
  private double collectionValue;

}
