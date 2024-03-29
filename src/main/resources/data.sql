INSERT INTO friend_status (status)
SELECT 'REQUESTED'
    WHERE NOT EXISTS (
    SELECT * FROM friend_status WHERE status = 'REQUESTED'
);
INSERT INTO friend_status (status)
SELECT 'WAITING'
    WHERE NOT EXISTS (
    SELECT * FROM friend_status WHERE status = 'WAITING'
);
INSERT INTO friend_status (status)
SELECT 'ACCEPTED'
    WHERE NOT EXISTS (
    SELECT * FROM friend_status WHERE status = 'ACCEPTED'
);
INSERT INTO friend_status (status)
SELECT 'DECLINED'
    WHERE NOT EXISTS (
    SELECT * FROM friend_status WHERE status = 'DECLINED'
);
INSERT INTO friend_status (status)
SELECT 'BLOCKED'
    WHERE NOT EXISTS (
    SELECT * FROM friend_status WHERE status = 'BLOCKED'
);


INSERT INTO rating (rating)
SELECT 'G'
    WHERE NOT EXISTS (
    SELECT * FROM rating WHERE rating = 'G'
);
INSERT INTO rating (rating)
SELECT 'PG'
    WHERE NOT EXISTS (
    SELECT * FROM rating WHERE rating = 'PG'
);
INSERT INTO rating (rating)
SELECT 'R'
    WHERE NOT EXISTS (
    SELECT * FROM rating WHERE rating = 'R'
);
INSERT INTO rating (rating)
SELECT 'NC_17'
    WHERE NOT EXISTS (
    SELECT * FROM rating WHERE rating = 'NC_17'
);

INSERT INTO genre (genre)
SELECT 'Comedy'
    WHERE NOT EXISTS (
    SELECT * FROM genre WHERE genre = 'Comedy'
);
INSERT INTO genre (genre)
SELECT 'Drama'
    WHERE NOT EXISTS (
    SELECT * FROM genre WHERE genre = 'Drama'
);
INSERT INTO genre (genre)
SELECT 'Cartoon'
    WHERE NOT EXISTS (
    SELECT * FROM genre WHERE genre = 'Cartoon'
);
INSERT INTO genre (genre)
SELECT 'Thriller'
    WHERE NOT EXISTS (
    SELECT * FROM genre WHERE genre = 'Thriller'
);
INSERT INTO genre (genre)
SELECT 'Documentary'
    WHERE NOT EXISTS (
    SELECT * FROM genre WHERE genre = 'Documentary'
);
INSERT INTO genre (genre)
SELECT 'ActionFilm'
    WHERE NOT EXISTS (
    SELECT * FROM genre WHERE genre = 'ActionFilm'
);