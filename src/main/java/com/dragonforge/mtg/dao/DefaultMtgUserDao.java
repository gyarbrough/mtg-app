package com.dragonforge.mtg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dragonforge.mtg.entity.User;

@Component
public class DefaultMtgUserDao implements MtgUserDao {
  
  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  //SqlParams
  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }
  
  //Create a user
  @Override
  public User saveUser(String username, String email, String password) {
    
    SqlParams params = generateInsertSql(username, email, password);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);
    
    Long UserPK = keyHolder.getKey().longValue();
    
    // @formatter:off
    return User.builder()
        .userPK(UserPK)
        .username(username)
        .email(email)
        .password(password)
        .build();
    // @formatter:on
  }

  //Fetch a user to obtain data for other methods
  @Override
  public User fetchUser(String username) {
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM users "
        + "WHERE username = :username";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("username", username);
    
    return jdbcTemplate.query(sql, params, new UserResultSetExtractor());
  }

  //UPDATE user in the database
  @Override
  public User editUser(Long userPK, String dataField, String value) {

    SqlParams params = generateUpdateSql(userPK, dataField, value);
    jdbcTemplate.update(params.sql, params.source);
    
    String username = fetchUser(userPK).getUsername();
    String email = fetchUser(userPK).getEmail();
    String password = fetchUser(userPK).getPassword();
    
    // @formatter:off
    return User.builder()
        .userPK(userPK)
        .username(username)
        .email(email)
        .password(password)
        .build();
    // @formatter:on
  }

  public User fetchUser(Long userPK) {
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM users "
        + "WHERE user_pk = :user_pk";
    // @formatter:on
    Map<String, Object> params = new HashMap<>();
    params.put("user_pk", userPK);
    
    return jdbcTemplate.query(sql, params, new UserResultSetExtractor());
  }

  //Delete a user
  @Override
  public void deleteUser(String username) {
    // @formatter:off
    String sql = ""
        + "DELETE FROM users "
        + "WHERE username = :username";
    // @formatter:on
    Map<String, Object> params = new HashMap<>();
    params.put("username", username);
    jdbcTemplate.update(sql, params);
    System.out.println(String.format("User with username=%s has been deleted", username));
  }
  
  //Result Set Extractor for fetching Users.
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
  
  //Generate INSERT sql for saveUser method
  public SqlParams generateInsertSql(String username, String email, String password) {
    // @formatter:off
    String sql = ""
        + "INSERT INTO users ("
        + "username, email, password"
        + ") VALUES ("
        + ":username, :email, :password"
        + ")";
    // @formatter:on
    
    SqlParams params = new SqlParams();
    
    params.sql = sql;
    params.source.addValue("username", username);
    params.source.addValue("email", email);
    params.source.addValue("password", password);
    
    return params;
  }
  
  //Generate UPDATE sql for updateUser method
  public SqlParams generateUpdateSql(Long userPK, String dataField, String value) {
    SqlParams params = new SqlParams();
    if(dataField.equalsIgnoreCase("username")) {
    // @formatter:off
    String sql = ""
        + "UPDATE users "
        + "SET username = :username "
        + "WHERE user_pk = :user_pk";
    // @formatter:on
    
    params.sql = sql;
    params.source.addValue("username", value);
    params.source.addValue("user_pk", userPK);
    
    } else if(dataField.equalsIgnoreCase("email")) {
   // @formatter:off
      String sql = ""
          + "UPDATE users "
          + "SET email = :email "
          + "WHERE user_pk = :user_pk";
      // @formatter:on
      
      params.sql = sql;
      params.source.addValue("email", value);
      params.source.addValue("user_pk", userPK);
      
    } else if(dataField.equalsIgnoreCase("password")) {
   // @formatter:off
      String sql = ""
          + "UPDATE users "
          + "SET password = :password "
          + "WHERE user_pk = :user_pk";
      // @formatter:on
      
      params.sql = sql;
      params.source.addValue("password", value);
      params.source.addValue("user_pk", userPK);
      
    }
    
    return params;
  }

}
