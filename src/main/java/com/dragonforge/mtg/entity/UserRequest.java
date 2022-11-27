package com.dragonforge.mtg.entity;

import lombok.Data;

@Data
public class UserRequest {
  
  private String username;
  private String email;
  private String password;
  private String confirmPassword;

}
