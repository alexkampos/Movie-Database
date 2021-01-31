CREATE SEQUENCE m_movies_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  MAXVALUE 2147483647;

CREATE TABLE m_movies (
  movie_id int8 NOT NULL DEFAULT nextval('m_movies_id_seq'),
  title VARCHAR (255) NOT NULL UNIQUE,
  duration_minutes int4 NOT NULL,
  PRIMARY KEY (movie_id)
);


CREATE SEQUENCE m_actors_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  MAXVALUE 2147483647;

CREATE TABLE m_actors (
  actor_id int8 NOT NULL DEFAULT nextval('m_actors_id_seq'),
  full_name VARCHAR(255) NOT NULL,
  age int4 NOT NULL,
  UNIQUE (full_name, age),  
  PRIMARY KEY(actor_id)
);


CREATE TABLE m_movies_actors (
  movie_id int8 NOT NULL ,
  actor_id int8 NOT NULL ,
  PRIMARY KEY (movie_id, actor_id),
  CONSTRAINT fk_movie
   FOREIGN KEY (movie_id)
    REFERENCES m_movies (movie_id)
     ON DELETE CASCADE,
  CONSTRAINT fk_actor
   FOREIGN KEY (actor_id)
    REFERENCES m_actors (actor_id)
     ON DELETE CASCADE
);

CREATE SEQUENCE m_genres_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  MAXVALUE 2147483647;

CREATE TABLE m_genres (
  genre_id int8 NOT NULL DEFAULT nextval('m_genres_id_seq'),
  name VARCHAR (255) NOT NULL UNIQUE,
  PRIMARY KEY (genre_id)
);

CREATE TABLE m_movies_genres (
  movie_id int8 NOT NULL ,
  genre_id int8 NOT NULL ,
  PRIMARY KEY (movie_id, genre_id),
  CONSTRAINT fk_movie
   FOREIGN KEY (movie_id)
    REFERENCES m_movies (movie_id)
     ON DELETE CASCADE,
  CONSTRAINT fk_genre
   FOREIGN KEY (genre_id)
    REFERENCES m_genres (genre_id)
     ON DELETE CASCADE
);

CREATE SEQUENCE m_languages_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  MAXVALUE 2147483647;

CREATE TABLE m_languages (
  language_id int8 NOT NULL DEFAULT nextval('m_languages_id_seq'),
  name VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (language_id)
);

CREATE TABLE m_movies_languages (
  movie_id int8 NOT NULL ,
  language_id int8 NOT NULL ,
  PRIMARY KEY (movie_id, language_id),
  CONSTRAINT fk_movie
   FOREIGN KEY (movie_id)
    REFERENCES m_movies (movie_id)
     ON DELETE CASCADE,
  CONSTRAINT fk_language
   FOREIGN KEY (language_id)
    REFERENCES m_languages (language_id)
     ON DELETE CASCADE
);

