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
import com.dragonforge.mtg.entity.Deck;
import com.dragonforge.mtg.entity.User;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultMtgDeckDao implements MtgDeckDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  //sqlparams
  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }

  //Save deck to database
  @Override
  public Deck saveDeck(User user, String deckName, List<Card> cards, double purchasePrice) {
    SqlParams params = generateInsertSql(user, deckName, cards, purchasePrice);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    Long deckPK = keyHolder.getKey().longValue();
    saveCards(cards, deckPK);

  // @formatter:off
  return Deck.builder()
      .deckPK(deckPK)
      .user(user)
      .deckName(deckName)
      .cards(cards)
      .purchasePrice(purchasePrice)
      .build();
  // @formatter:on
  }

  //Generate INSERT sql for saveDeck method.
  private SqlParams generateInsertSql(User user, String deckName, List<Card> cards,
      double purchasePrice) {
    // @formatter:off
    String sql = ""
        + "INSERT INTO decks ("
        + "user_fk, deck_name, purchase_price"
        + ") VALUES ("
        + ":user_fk, :deck_name, :purchase_price"
        + ")";
    // @formatter:on
    
    SqlParams params = new SqlParams();
    
    params.sql = sql;
    params.source.addValue("user_fk", user.getUserPK());
    params.source.addValue("deck_name", deckName);
    params.source.addValue("purchase_price", purchasePrice);
    return params;
  }

  //Save deck cards
  private void saveCards(List<Card> cards, Long deckPK) {
    for(Card card : cards) {
      SqlParams params = generateInsertSql(card, deckPK);
      jdbcTemplate.update(params.sql, params.source);
    }
    
  }

  //Generate INSERT sql for saveCards method.
  private SqlParams generateInsertSql(Card card, Long deckPK) {
    SqlParams params = new SqlParams();
    
    // @formatter:off
    params.sql = ""
        + "INSERT INTO deck_cards ("
        + "deck_fk, card_fk"
        + ") VALUES ("
        + ":deck_fk, :card_fk"
        + ")";
    // @formatter:on
    
    params.source.addValue("deck_fk", deckPK);
    params.source.addValue("card_fk", card.getCardPK());
    
    return params;
  }

  //Fetch cards for deck
  @Override
  public List<Card> fetchCards(List<String> cards) {
    if (cards.isEmpty()) {
      return new LinkedList<>();
    }

    Map<String, Object> params = new HashMap<>();

    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM cards "
        + "WHERE name IN(";
    // @formatter:on

    for (int index = 0; index < cards.size(); index++) {
      String key = "card_" + index;
      sql += ":" + key + ", ";
      params.put(key, cards.get(index));
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

  //Fetch a User from the database.
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
  
  //ResultSetExtractor for User objects.
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
  
  //ResultSetExtractor for Deck Objects.
  class DeckResultSetExtractor implements ResultSetExtractor<Deck> {
    
    @Override
    public Deck extractData(ResultSet rs) throws SQLException {
      rs.next();
      
      // @formatter:off
      return Deck.builder()
          .deckPK(rs.getLong("deck_pk"))
          .deckName(rs.getString("deck_name"))
          .purchasePrice(rs.getDouble("purchase_price"))
          .build();
      // @formatter:on
    }
  }
  
  //Delete a deck
  @Override
  public void deleteDeck(Long userPK, Long deckPK) {
    // @formatter:off
    String sql = ""
        + "DELETE FROM decks "
        + "WHERE user_fk = :user_fk "
        + "AND deck_pk = :deck_pk";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("user_fk", userPK);
    params.put("deck_pk", deckPK);
    
    jdbcTemplate.update(sql, params);
    System.out.println("Deck has been deleted.");
  }

  
  //Delete existing deck cards.
  @Override
  public void deleteDeckCards(Deck deck) {
    Long deckPK = deck.getDeckPK();
    SqlParams params = generateDeleteDeckCardsSql(deckPK);
    jdbcTemplate.update(params.sql, params.source);
    
  }

  //Delete existing cards from the given deck.
  private SqlParams generateDeleteDeckCardsSql(Long deckPK) {
    SqlParams params = new SqlParams();
    
    // @formatter:off
    params.sql = ""
        + "DELETE FROM deck_cards "
        + "WHERE deck_fk = :deck_fk";
    // @formatter:on
    
    params.source.addValue("deck_fk", deckPK);
    
    return params;
  }

  //Save new deck cards and update purchase price.
  @Override
  public Deck updateDeck(User user, Deck deck, List<Card> cards, double purchasePrice) {
    SqlParams params = generateUpdateSql(user, cards, purchasePrice);
    Long deckPK = deck.getDeckPK();
    
    jdbcTemplate.update(params.sql, params.source);
    
    saveCards(cards, deckPK);

  // @formatter:off
  return Deck.builder()
      .deckPK(deckPK)
      .user(user)
      .deckName(deck.getDeckName())
      .cards(cards)
      .purchasePrice(purchasePrice)
      .build();
  // @formatter:on
  }

  //Generate UPDATE sql for updateDeck method.
  private SqlParams generateUpdateSql(User user, List<Card> cards, double purchasePrice) {
    // @formatter:off
    String sql = ""
        + "UPDATE decks "
        + "SET purchase_price = :purchase_price "
        + "WHERE user_fk = :user_fk";
    // @formatter:on
    
    SqlParams params = new SqlParams();
    
    params.sql = sql;
    params.source.addValue("purchase_price", purchasePrice);
    params.source.addValue("user_fk", user.getUserPK());
    return params;
  }

  @Override
  public List<Card> fetchDeckCards(Deck deck) {
    log.debug("DAO: deck={}", deck);
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM deck_cards "
        + "INNER JOIN cards "
        + "ON deck_cards.card_fk = cards.card_pk "
        + "WHERE deck_cards.deck_fk = :deck_fk";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("deck_fk", deck.getDeckPK());
    
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

  @Override
  public Deck fetchDeck(User user, String deckName) {
    log.debug("DAO: User={}, deckName={}", user, deckName);
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM decks "
        + "WHERE user_fk = :user_fk AND deck_name = :deck_name";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("user_fk", user.getUserPK());
    params.put("deck_name", deckName);
    
    return jdbcTemplate.query(sql, params, new DeckResultSetExtractor());
  }

}
