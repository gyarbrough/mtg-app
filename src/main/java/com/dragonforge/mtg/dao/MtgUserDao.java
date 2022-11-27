package com.dragonforge.mtg.dao;

import com.dragonforge.mtg.entity.User;

public interface MtgUserDao {

  User saveUser(String username, String email, String password);

  User fetchUser(String username);

  User editUser(Long userPk, String dataField, String value);

  void deleteUser(String username);

}
