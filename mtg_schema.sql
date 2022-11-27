DROP TABLE IF EXISTS deck_cards;
DROP TABLE IF EXISTS decks;
DROP TABLE IF EXISTS collection_cards;
DROP TABLE IF EXISTS collections;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cards;

CREATE TABLE cards (
  card_pk int unsigned NOT NULL AUTO_INCREMENT,
  card_id varchar(40) NOT NULL UNIQUE,
  name varchar(75) NOT NULL,
  release_date varchar(10) NOT NULL,
  mana_cost varchar(20),
  cmc int unsigned,
  type_line varchar(75) NOT NULL,
  oracle_text text,
  power int unsigned,
  toughness int unsigned,
  set_code char(3) NOT NULL,
  set_name varchar(45) NOT NULL,
  collector_number int NOT NULL,
  rarity varchar(20) NOT NULL,
  flavor_text text,
  artist varchar(50) NOT NULL,
  price_usd decimal(7,2) NOT NULL,
  PRIMARY KEY (card_pk)
);

CREATE TABLE users (
  user_pk int unsigned NOT NULL AUTO_INCREMENT,
  username varchar(40) NOT NULL UNIQUE,
  email varchar(50) NOT NULL UNIQUE,
  password varchar(20) NOT NULL,
  PRIMARY KEY (user_pk)
);

CREATE TABLE collections (
   collection_pk int unsigned NOT NULL AUTO_INCREMENT,
   user_fk int unsigned NOT NULL UNIQUE,
   collection_value decimal(7,2),
   PRIMARY KEY (collection_pk),
   FOREIGN KEY (user_fk) REFERENCES users (user_pk) ON DELETE CASCADE
);

CREATE TABLE collection_cards (
  collection_cards_pk int unsigned NOT NULL AUTO_INCREMENT,
  collection_fk int unsigned NOT NULL,
  card_fk int unsigned NOT NULL,
  PRIMARY KEY (collection_cards_pk),
  FOREIGN KEY (collection_fk) REFERENCES collections (collection_pk) ON DELETE CASCADE,
  FOREIGN KEY (card_fk) REFERENCES cards (card_pk) ON DELETE CASCADE
);

CREATE TABLE decks (
  deck_pk int unsigned NOT NULL AUTO_INCREMENT,
  user_fk int unsigned NOT NULL,
  deck_name varchar(50) NOT NULL,
  purchase_price decimal(7,2) NOT NULL,
  PRIMARY KEY (deck_pk),
  FOREIGN KEY (user_fk) REFERENCES users (user_pk) ON DELETE CASCADE
);

CREATE TABLE deck_cards (
  deck_cards_pk int unsigned NOT NULL AUTO_INCREMENT,
  deck_fk int unsigned NOT NULL,
  card_fk int unsigned NOT NULL,
  PRIMARY KEY (deck_cards_pk),
  FOREIGN KEY (deck_fk) REFERENCES decks (deck_pk) ON DELETE CASCADE,
  FOREIGN KEY (card_fk) REFERENCES cards (card_pk) ON DELETE CASCADE
);