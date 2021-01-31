INSERT INTO m_movies (title, duration_minutes)
VALUES
('First Movie Title',120),
('Second Movie Title',150);

INSERT INTO m_actors (full_name, age)
VALUES
('First Actor', 43),
('Second Actor', 50);

INSERT INTO m_movies_actors (movie_id, actor_id)
VALUES
(1,1),
(1,2),
(2,1);

INSERT INTO m_genres ("name")
VALUES
('Horror'),
('Comedy'),
('Thriller');

INSERT INTO m_movies_genres (movie_id, genre_id)
VALUES
(1,1),
(1,3),
(2,2);

INSERT INTO m_languages ("name")
VALUES
('English'),
('Albanian'),
('Greek');

INSERT INTO m_movies_languages (movie_id, language_id)
VALUES
(1,1),
(2,2),
(2,3);

