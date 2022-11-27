package com.dragonforge.mtg.dao;

import java.util.List;
import java.util.Optional;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Collection;
import com.dragonforge.mtg.entity.User;

public interface MtgCollectionDao {

  Collection saveCollection(User user, List<Card> cards, double collectionValue);

  List<Card> fetchCards(List<String> cards);

  Optional<User> fetchUser(String user);

  Collection updateCollection(Collection collection, List<Card> cards, double collectionValue);

  List<Card> fetchCollectionCards(Long collectionPK);

  Collection fetchCollection(Long userPK);

  void deleteCollectionCards(Collection fetchCollection);

}
