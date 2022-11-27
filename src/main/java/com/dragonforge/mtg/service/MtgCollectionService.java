package com.dragonforge.mtg.service;

import java.util.List;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Collection;
import com.dragonforge.mtg.entity.CollectionRequest;

public interface MtgCollectionService {

  Collection createCollection(CollectionRequest collectionRequest);

  Collection updateCollection(CollectionRequest collectionRequest);

  List<Card> fetchCollectionCards(String username);

}
