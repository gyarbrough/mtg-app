package com.dragonforge.mtg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dragonforge.mtg.dao.MtgUserDao;
import com.dragonforge.mtg.entity.User;
import com.dragonforge.mtg.entity.UserRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultMtgUserService implements MtgUserService {
  
  @Autowired
  private MtgUserDao mtgUserDao;

  @Transactional
  @Override
  public User createUser(UserRequest userRequest) {
    log.debug("User={}", userRequest);
    
    String username = userRequest.getUsername();
    String email = userRequest.getEmail();
    String password = userRequest.getPassword();
    String confirmPassword = userRequest.getConfirmPassword();
    
    if(password.equals(confirmPassword)) {
      return mtgUserDao.saveUser(username, email, password);
    } else {
      System.out.println("Passwords do not match.");
      return null;
    }
  }

  @Transactional
  @Override
  public User editUser(String username, String dataField, String value, String password) {
    log.debug("username={}, dataField={}, value={}, password={}", dataField, value, password);
    
    if(mtgUserDao.fetchUser(username).getPassword().equals(password)) {
      Long userPK = mtgUserDao.fetchUser(username).getUserPK();
      return mtgUserDao.editUser(userPK, dataField, value);
    } else {
      System.out.println("Password is incorrect.");
      return null;
    }
    
  }

  @Transactional
  @Override
  public void deleteUser(String username, String password) {
    log.debug("user={}, password={}", username, password);
    
    if(mtgUserDao.fetchUser(username).getPassword().equals(password)) {
      mtgUserDao.deleteUser(username);
    } else {
      System.out.println("Password is incorrect.");
    }
    
  }

}
