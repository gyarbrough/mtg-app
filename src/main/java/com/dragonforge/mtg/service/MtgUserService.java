package com.dragonforge.mtg.service;

import com.dragonforge.mtg.entity.User;
import com.dragonforge.mtg.entity.UserRequest;

public interface MtgUserService {

  User createUser(UserRequest userRequest);

  User editUser(String username, String dataField, String value, String password);

  void deleteUser(String user, String password);

}
