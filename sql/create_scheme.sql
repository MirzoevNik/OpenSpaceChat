DROP DATABASE IF EXISTS open_space_chat;
CREATE DATABASE open_space_chat CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE open_space_chat;

CREATE TABLE IF NOT EXISTS User (
  id INT NOT NULL AUTO_INCREMENT ,
  login VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  name VARCHAR(45) ,
  surname VARCHAR(45) ,
  country VARCHAR(70),
  PRIMARY KEY (id),
  UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS Admin (
  id INT NOT NULL AUTO_INCREMENT ,
  user_id INT NOT NULL ,
  PRIMARY KEY (id)
);