package com.dragonforge.mtg.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
  private Long userPK;
  private String username;
  private String email;
  private String password;
}
