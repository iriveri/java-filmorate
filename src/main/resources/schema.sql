CREATE TABLE IF NOT EXISTS users
(
    id       INTEGER GENERATED BY DEFAULT AS IDENTITY,
    email    varchar(50) NOT NULL,
    login    varchar(30) NOT NULL,
    name     varchar(50) NOT NULL,
    birthday date        NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_friends
(
    user_id   INTEGER,
    friend_id INTEGER,
    CONSTRAINT pk_user_friends PRIMARY KEY (user_id, friend_id),
    CONSTRAINT fk_user_id_friends FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_friend_id_friends FOREIGN KEY (friend_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT chk_different_user CHECK (user_id <> friend_id)
);
CREATE TABLE IF NOT EXISTS rating
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY,
    name varchar(20),
    CONSTRAINT rating_pk PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS genre
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY,
    name varchar(20),
    CONSTRAINT genre_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS films
(
    id          INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        varchar(50) NOT NULL,
    description text        NOT NULL,
    releaseDate date        NOT NULL,
    duration    long        NOT NULL,
    rating_id   INTEGER,
    CONSTRAINT film_pk PRIMARY KEY (id),
    CONSTRAINT fk_rating FOREIGN KEY (rating_id) REFERENCES rating (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  INTEGER,
    genre_id INTEGER NOT NULL,
    CONSTRAINT pk_film_genres PRIMARY KEY (film_id, genre_id),
    CONSTRAINT fk_film_id_genres FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE,
    CONSTRAINT fk_genre_id_genres FOREIGN KEY (genre_id) REFERENCES genre (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS film_likes
(
    film_id INTEGER,
    user_id INTEGER,
    CONSTRAINT pk_film_likes PRIMARY KEY (film_id, user_id),
    CONSTRAINT fk_film_id_likes FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_id_likes FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);