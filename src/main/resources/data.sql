
INSERT INTO rating (name)
SELECT 'G'
WHERE NOT EXISTS (
        SELECT * FROM rating WHERE name = 'G'
    );
INSERT INTO rating (name)
SELECT 'PG'
WHERE NOT EXISTS (
        SELECT * FROM rating WHERE name = 'PG'
    );
INSERT INTO rating (name)
SELECT 'PG-13'
WHERE NOT EXISTS (
        SELECT * FROM rating WHERE name = 'PG-13'
    );
INSERT INTO rating (name)
SELECT 'R'
WHERE NOT EXISTS (
        SELECT * FROM rating WHERE name = 'R'
    );
INSERT INTO rating (name)
SELECT 'NC-17'
WHERE NOT EXISTS (
        SELECT * FROM rating WHERE name = 'NC-17'
    );

INSERT INTO genre (name)
SELECT 'Комедия'
WHERE NOT EXISTS (
        SELECT * FROM genre WHERE name = 'Комедия'
    );
INSERT INTO genre (name)
SELECT 'Драма'
WHERE NOT EXISTS (
        SELECT * FROM genre WHERE name = 'Драма'
    );
INSERT INTO genre (name)
SELECT 'Мультфильм'
WHERE NOT EXISTS (
        SELECT * FROM genre WHERE name = 'Мультфильм'
    );
INSERT INTO genre (name)
SELECT 'Триллер'
WHERE NOT EXISTS (
        SELECT * FROM genre WHERE name = 'Триллер'
    );
INSERT INTO genre (name)
SELECT 'Документальный'
WHERE NOT EXISTS (
        SELECT * FROM genre WHERE name = 'Документальный'
    );
INSERT INTO genre (name)
SELECT 'Боевик'
WHERE NOT EXISTS (
        SELECT * FROM genre WHERE name = 'Боевик'
    );