package com.dragonforge.mtg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dragonforge.mtg.entity.Card;
import com.dragonforge.mtg.entity.Collection;
import com.dragonforge.mtg.entity.User;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultMtgCollectionDao implements MtgCollectionDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  //sqlparams
  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }
  
  
  //save collection to database
  @Override
  public Collection saveCollection(User user, List<Card> cards, double collectionValue) {
    SqlParams params = generateInsertSql(user, collectionValue);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);
    
    Long collectionPK = keyHolder.getKey().longValue();
    saveCards(cards, collectionPK);
    
    // @formatter:off
    return Collection.builder()
        .collectionPK(collectionPK)
        .user(user)
        .cards(cards)
        .collectionValue(collectionValue)
        .build();
    // @formatter:on
  }

  //save cards to database
  private void saveCards(List<Card> cards, Long collectionPK) {
    for(Card card : cards) {
      SqlParams params = generateInsertSql(card, collectionPK);
      jdbcTemplate.update(params.sql, params.source);
    }
    
  }

  //INSERT sql for collection cards
  private SqlParams generateInsertSql(Card card, Long collectionPK) {
    SqlParams params = new SqlParams();
    
    // @formatter:off
    params.sql = ""
        + "INSERT INTO collection_cards ("
        + "collection_fk, card_fk"
        + ") VALUES ("
        + ":collection_fk, :card_fk"
        + ")";
    // @formatter:on
    
    params.source.addValue("collection_fk", collectionPK);
    params.source.addValue("card_fk", card.getCardPK());
    
    return params;
  }

  //INSERT sql for user collection
  private SqlParams generateInsertSql(User user, double collectionValue) {
    // @formatter:off
    String sql = ""
        + "INSERT INTO collections ("
        + "user_fk, collection_value"
        + ") VALUES ("
        + ":user_fk, :collection_value"
        + ")";
    // @formatter:on
    
    SqlParams params = new SqlParams();
    
    params.sql = sql;
    params.source.addValue("user_fk", user.getUserPK());
    params.source.addValue("collection_value", collectionValue);
    return params;
  }

  //fetch cards from database
  @Override
  public List<Card> fetchCards(List<String> cardIds) {
    if (cardIds.isEmpty()) {
      return new LinkedList<>();
    }
    
    Map<String, Object> params = new HashMap<>();
    
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM cards "
        + "WHERE name IN(";
    // @formatter:on
    
    for (int index = 0; index < cardIds.size(); index++) {
      String key = "card_" + index;
      sql += ":" + key + ", ";
      params.put(key, cardIds.get(index));
    }
    
    sql = sql.substring(0, sql.length() - 2);
    sql += ")";
    
    return jdbcTemplate.query(sql, params, new RowMapper<Card>() {
      @Override
      public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        // @formatter:off
        return Card.builder()
            .cardPK(rs.getLong("card_pk"))
            .cardId(rs.getString("card_id"))
            .name(rs.getString("name"))
            .releaseDate(rs.getString("release_date"))
            .manaCost(rs.getString("mana_cost"))
            .cmc(rs.getInt("cmc"))
            .typeLine(rs.getString("type_line"))
            .oracleText(rs.getString("oracle_text"))
            .power(rs.getInt("power"))
            .toughness(rs.getInt("toughness"))
            .setCode(rs.getString("set_code"))
            .setName(rs.getString("set_name"))
            .collectorNumber(rs.getInt("collector_number"))
            .rarity(rs.getString("rarity"))
            .flavorText(rs.getString("flavor_text"))
            .artist(rs.getString("artist"))
            .priceUsd(rs.getDouble("price_usd"))
            .build();
        // @formatter:on
      }
    });
  }

  //fetch user from database
  @Override
  public Optional<User> fetchUser(String username) {
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM users "
        + "WHERE username = :username";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("username", username);
    return Optional.ofNullable(
        jdbcTemplate.query(sql, params, new UserResultSetExtractor()));
  }
  
  //User resultSetExtractor.
  class UserResultSetExtractor implements ResultSetExtractor<User> {
    @Override
    public User extractData(ResultSet rs) throws SQLException {
      rs.next();
      
      // @formatter:off
      return User.builder()
          .userPK(rs.getLong("user_pk"))
          .username(rs.getString("username"))
          .email(rs.getString("email"))
          .password(rs.getString("password"))
          .build();
      // @formatter:on
    }
  }

  //Update the cards in a user's collection
  @Override
  public Collection updateCollection(Collection collection, List<Card> cards, double collectionValue) {
    SqlParams params = generateUpdateSql(collection.getUser(), cards, collectionValue);
    Long collectionPK = collection.getCollectionPK();
    
    jdbcTemplate.update(params.sql, params.source);
    
    saveCards(cards, collectionPK);

  // @formatter:off
  return Collection.builder()
      .collectionPK(collectionPK)
      .user(collection.getUser())
      .cards(cards)
      .collectionValue(collectionValue)
      .build();
  // @formatter:on
  }

  private SqlParams generateUpdateSql(User user, List<Card> cards, double collectionValue) {
    // @formatter:off
    String sql = ""
        + "UPDATE collections "
        + "SET collection_value = :collection_value "
        + "WHERE user_pk = :user_pk";
    // @formatter:on
    
    SqlParams params = new SqlParams();
    
    params.sql = sql;
    params.source.addValue("collection_value", collectionValue);
    params.source.addValue("user_pk", user.getUserPK());
    return params;
  }

  //Fetch the cards in a user's collection.
  @Override
  public List<Card> fetchCollectionCards(Long collectionPK) {
    log.debug("collectionPK={}", collectionPK);
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM collection_cards "
        + "INNER JOIN cards "
        + "ON collection_cards.collection_fk = cards.card_pk "
        + "WHERE collection_cards.collection_fk = :collection_fk";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("collection_fk", collectionPK);
    
    return jdbcTemplate.query(sql, params, new RowMapper<>() {
      @Override
      public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        // @formatter:off
        return Card.builder()
            .cardPK(rs.getLong("card_pk"))
            .cardId(rs.getString("card_id"))
            .name(rs.getString("name"))
            .releaseDate(rs.getString("release_date"))
            .manaCost(rs.getString("mana_cost"))
            .cmc(rs.getInt("cmc"))
            .typeLine(rs.getString("type_line"))
            .oracleText(rs.getString("oracle_text"))
            .power(rs.getInt("power"))
            .toughness(rs.getInt("toughness"))
            .setCode(rs.getString("set_code"))
            .setName(rs.getString("set_name"))
            .collectorNumber(rs.getInt("collector_number"))
            .rarity(rs.getString("rarity"))
            .flavorText(rs.getString("flavor_text"))
            .artist(rs.getString("artist"))
            .priceUsd(rs.getDouble("price_usd"))
            .build();
        // @formatter:on
      }
    });
  }

  //fetch collection details for a given user.
  @Override
  public Collection fetchCollection(Long userPK) {
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM collections "
        + "WHERE user_fk = :user_fk";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("user_fk", userPK);
    
    return jdbcTemplate.query(sql, params, new CollectionResultSetExtractor());
  }
  
  //Collection result set Extractor.
  class CollectionResultSetExtractor implements ResultSetExtractor<Collection> {
    @Override
    public Collection extractData(ResultSet rs) throws SQLException {
      rs.next();
      
      // @formatter:off
      return Collection.builder()
          .collectionPK(rs.getLong("collection_pk"))
          .user(rs.getObject("user_fk", User.class))
          .collectionValue(rs.getDouble("collection_value"))
          .build();
      // @formatter:on
    }
  }

  //Delete existing collection cards.
  @Override
  public void deleteCollectionCards(Collection collection) {
    Long collectionPK = collection.getCollectionPK();
    SqlParams params = generateDeleteCollectionCardsSql(collectionPK);
    jdbcTemplate.update(params.sql, params.source);
    
  }

  private SqlParams generateDeleteCollectionCardsSql(Long collectionPK) {
    SqlParams params = new SqlParams();
    
    // @formatter:off
    params.sql = ""
        + "DELETE FROM collection_cards "
        + "WHERE collection_fk = :collection_fk";
    // @formatter:on
    
    params.source.addValue("collection_fk", collectionPK);
    
    return params;
  }


}
