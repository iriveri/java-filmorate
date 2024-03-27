# java-filmorate
##Описание структуры БД
Структура бызы данных соответствует третьей нормальной форме (3NF) базы данных и представляет из себя способ хранения информации о пользователях и фильмах.
### 
**Таблица user:** Содержит информацию о пользователях, такую как идентификатор, электронная почта, логин, имя и дата рождения.

**Таблица user_friends:** Эта таблица представляет отношения дружбы между пользователями. Она содержит идентификаторы пользователей (user_id и friend_id), а также идентификатор дружбы (friendship_id).

**Таблица friend_status:** Содержит статус дружбы между пользователями, связанный с friendship_id.

**Таблица genre:** Содержит информацию о жанрах фильмов.

**Таблица film:** Хранит информацию о фильмах, такую как идентификатор, название, описание, дата выпуска, продолжительность и рейтинг.

**Таблица film_genre:** Эта таблица представляет отношение многие-ко-многим между фильмами и жанрами.

**Таблица film_likes:** Содержит информацию о том, какие пользователи лайкнули какие фильмы.

![DB_STRUCT](https://github.com/iriveri/java-filmorate/assets/61321955/a8aa1faa-a871-49fe-91c8-c3aba00fc222)
Эта схема представляет структуру базы данных
###Примеры запросов для основных операций приложения:

Получить всех друзей пользователя:
```sql
Copy code
SELECT u.name AS friend_name
FROM user u
INNER JOIN user_friends uf ON u.user_id = uf.friend_id
WHERE uf.user_id = {user_id};
```

Получить все фильмы, лайкнутые пользователем:
```sql
Copy code
SELECT f.name AS film_name
FROM film f
INNER JOIN film_likes fl ON f.film_id = fl.film_id
WHERE fl.user_id = {user_id};
```
Получить все фильмы определенного жанра:
```sql
SELECT f.name AS film_name
FROM film f
INNER JOIN film_genre fg ON f.film_id = fg.film_id
WHERE fg.genre_id = {genre_id};
```

Добавить нового друга для пользователя:
```sql
Copy code
INSERT INTO user_friends (user_id, friend_id, friendship_id)
VALUES ({user_id}, {friend_id}, {friendship_id});
```

Получить статус дружбы между двумя пользователями:
```sql
Copy code
SELECT fs.friend_status
FROM friend_status fs
INNER JOIN user_friends uf ON fs.friendship_id = uf.friendship_id
WHERE uf.user_id = {user_id} AND uf.friend_id = {friend_id};
```
