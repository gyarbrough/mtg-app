package com.dragonforge.mtg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Collection;
import com.dragonforge.mtg.entity.CollectionRequest;
import com.dragonforge.mtg.service.MtgCollectionService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultMtgCollectionController implements MtgCollectionController {
  
  @Autowired
  private MtgCollectionService mtgCollectionService;

  @Override
  public Collection createCollection(CollectionRequest collectionRequest) {
    log.debug("Collection={}", collectionRequest);
    return mtgCollectionService.createCollection(collectionRequest);
  }

  @Override
  public Collection updateCollection(CollectionRequest collectionRequest) {
    log.debug("Collection={}", collectionRequest);
    return mtgCollectionService.updateCollection(collectionRequest);
  }

  @Override
  public List<Card> fetchCollectionCards(String username) {
    log.debug("username={}", username);
    return mtgCollectionService.fetchCollectionCards(username);
  }

}
