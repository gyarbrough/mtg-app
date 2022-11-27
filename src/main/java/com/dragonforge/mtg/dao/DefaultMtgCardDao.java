package com.dragonforge.mtg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dragonforge.mtg.entity.Card;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultMtgCardDao implements MtgCardDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  //SqlParams
  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }
  
  //fetch list of cards based on optional provided params
  @Override
  public List<Card> fetchCards(String searchType, String keyWord, String sortBy) {
    log.debug("DAO: searchType={}, keyWord={}, sortBy={}", searchType, keyWord, sortBy);
    Map<String, Object> params = new HashMap<>();
    String sql;
    
    //search for card name
    if (searchType.equalsIgnoreCase("name")) {
      // @formatter:off
      sql = ""
          + "SELECT * FROM cards WHERE name = :name";
      // @formatter:on
      params.put("name", keyWord);
    
    //search for 3-letter set code
    } else if (searchType.equalsIgnoreCase("set_code")) {
      
      //order results by card name
      if (sortBy.equalsIgnoreCase("name")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE set_code = :set_code"
            + " ORDER BY :name ASC";
        // @formatter:on
        params.put("set_code", keyWord);
        params.put("name", sortBy);
      
      //order results by card type
      } else if (sortBy.equalsIgnoreCase("type_line")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE set_code = :set_code"
            + " ORDER BY :type_line ASC";
        // @formatter:on
        params.put("set_code", keyWord);
        params.put("type_line", sortBy);
      
      //order results by rarity
      } else if (sortBy.equalsIgnoreCase("rarity")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE set_code = :set_code"
            + " ORDER BY :rarity ASC";
        // @formatter:on
        params.put("set_code", keyWord);
        params.put("rarity", sortBy);
     
      //incorrect or no sort parameter set
      } else {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE set_code = :set_code";
        // @formatter:on
        params.put("set_code", keyWord);
      }
    
    //search for set name
    } else if (searchType.equalsIgnoreCase("set_name")) {
      
      //order cards by name
      if (sortBy.equalsIgnoreCase("name")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE set_name = :set_name"
            + " ORDER BY :name ASC";
        // @formatter:on
        params.put("set_name", keyWord);
        params.put("name", sortBy);
      
      //order cards by type
      } else if (sortBy.equalsIgnoreCase("type_line")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE set_name = :set_name"
            + " ORDER BY :type_line ASC";
        // @formatter:on
        params.put("set_name", keyWord);
        params.put("type_line", sortBy);
      
      //order cards by rarity
      } else if (sortBy.equalsIgnoreCase("rarity")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE set_name = :set_name"
            + " ORDER BY :rarity ASC";
        // @formatter:on
        params.put("set_name", keyWord);
        params.put("rarity", sortBy);
      
      //incorrect or no sort parameter
      } else {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE set_name = :set_name";
        // @formatter:on
        params.put("set_name", keyWord);
      }
    
    //search for card rarity
    } else if (searchType.equalsIgnoreCase("rarity")) {
      
      //order by name
      if (sortBy.equalsIgnoreCase("name")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE rarity = :rarity"
            + " ORDER BY :name ASC";
        // @formatter:on
        params.put("rarity", keyWord);
        params.put("name", sortBy);
      
      //order by set code
      } else if (sortBy.equalsIgnoreCase("set_code")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE rarity = :rarity"
            + " ORDER BY :set_code ASC";
        // @formatter:on
        params.put("rarity", keyWord);
        params.put("set_code", sortBy);
      
      //order by type
      } else if (sortBy.equalsIgnoreCase("type_line")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE rarity = :rarity"
            + " ORDER BY :type_line ASC";
        // @formatter:on
        params.put("rarity", keyWord);
        params.put("type_line", sortBy);
      
      //incorrect or no sort parameter
      } else {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " WHERE rarity = :rarity";
        // @formatter:on
        params.put("rarity", keyWord);
      }
    
    //incorrect or no search parameter specified
    } else {
      
      //order by name
      if (sortBy.equalsIgnoreCase("name")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " ORDER BY :name ASC";
        // @formatter:on
        params.put("name", sortBy);
      
      //order by set code
      } else if (sortBy.equalsIgnoreCase("set_code")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " ORDER BY :set_code ASC";
        // @formatter:on
        params.put("set_code", sortBy);
      
      //order by type
      } else if (sortBy.equalsIgnoreCase("type_line")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " ORDER BY :type_line ASC";
        // @formatter:on
        params.put("type_line", sortBy);
      
      //order by rarity
      } else if (sortBy.equalsIgnoreCase("rarity")) {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards"
            + " ORDER BY :rarity ASC";
        // @formatter:on
        params.put("rarity", sortBy);
      
      //incorrect or no sort parameter
      } else {
        // @formatter:off
        sql = ""
            + "SELECT * FROM cards";
        // @formatter:on
      }
    }
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

  //Create a new card
  @Override
  public Card saveCard(String cardId, String name, String releaseDate, String manaCost, int cmc,
      String typeLine, String oracleText, int power, int toughness, String setCode, String setName,
      int collectorNumber, String rarity, String flavorText, String artist, double priceUsd) {
    
    SqlParams params = generateInsertSql(cardId, name, releaseDate, manaCost, cmc, typeLine,
        oracleText, power, toughness, setCode, setName, collectorNumber, rarity, flavorText,
        artist, priceUsd);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);
    Long cardPK = keyHolder.getKey().longValue();
    
    // @formatter:off
    return Card.builder()
        .cardPK(cardPK)
        .cardId(cardId)
        .name(name)
        .releaseDate(releaseDate)
        .manaCost(manaCost)
        .cmc(cmc)
        .typeLine(typeLine)
        .oracleText(oracleText)
        .power(power)
        .toughness(toughness)
        .setCode(setCode)
        .setName(setName)
        .collectorNumber(collectorNumber)
        .rarity(rarity)
        .flavorText(flavorText)
        .artist(artist)
        .priceUsd(priceUsd)
        .build();
    // @formatter:on
  }
  
  //Generate INSERT sql for saveCard method.
  SqlParams generateInsertSql(String cardId, String name, String releaseDate,
      String manaCost, int cmc, String typeLine, String oracleText, int power, int toughness,
      String setCode, String setName, int collectorNumber, String rarity, String flavorText,
      String artist, double priceUsd) {
    // @formatter:off
    String sql = ""
        + "INSERT INTO cards ("
        + "card_id, name, release_date, mana_cost, cmc, type_line, oracle_text, power, toughness, "
        + "set_code, set_name, collector_number, rarity, flavor_text, artist, price_usd"
        + ") VALUES ("
        + ":card_id, :name, :release_date, :mana_cost, :cmc, :type_line, :oracle_text, :power, "
        + ":toughness, :set_code, :set_name, :collector_number, :rarity, :flavor_text, "
        + ":artist, :price_usd)";
    // @formatter:on
    
    SqlParams params = new SqlParams();
    
    params.sql = sql;
    params.source.addValue("card_id", cardId);
    params.source.addValue("name", name);
    params.source.addValue("release_date", releaseDate);
    params.source.addValue("mana_cost", manaCost);
    params.source.addValue("cmc", cmc);
    params.source.addValue("type_line", typeLine);
    params.source.addValue("oracle_text", oracleText);
    params.source.addValue("power", power);
    params.source.addValue("toughness", toughness);
    params.source.addValue("set_code", setCode);
    params.source.addValue("set_name", setName);
    params.source.addValue("collector_number", collectorNumber);
    params.source.addValue("rarity", rarity);
    params.source.addValue("flavor_text", flavorText);
    params.source.addValue("artist", artist);
    params.source.addValue("price_usd", priceUsd);
    
    return params;
  }

}
