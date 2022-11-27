package com.dragonforge.mtg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dragonforge.mtg.entity.User;
import com.dragonforge.mtg.entity.UserRequest;
import com.dragonforge.mtg.service.MtgUserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultMtgUserController implements MtgUserController {
  
  @Autowired
  private MtgUserService mtgUserService;

  @Override
  public User createUser(UserRequest userRequest) {
    log.debug("User={}", userRequest);
    return mtgUserService.createUser(userRequest);
  }

  @Override
  public User editUser(String username, String dataField, String value, String password) {
    log.debug("username={}, dataField={}, value={}, password={}", username, dataField, value, password);
    return mtgUserService.editUser(username, dataField, value, password);
  }

  @Override
  public void deleteUser(String username, String password) {
    log.debug("user={}, password={}", username, password);
    mtgUserService.deleteUser(username, password);
  }

}
