package com.dragonforge.mtg.entity;

import java.util.List;
import lombok.Data;

@Data
public class CollectionRequest {
  
  private String user;
  private List<String> cards;

}
