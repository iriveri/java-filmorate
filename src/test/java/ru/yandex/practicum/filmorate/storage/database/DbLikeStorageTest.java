package ru.yandex.practicum.filmorate.storage.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;


import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class DbLikeStorageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // Добавление двух пользователей
        jdbcTemplate.update("INSERT INTO users (id,email, login, name, birthday) " + "VALUES (?, ?, ?, ?, ?)", 1L, "user1@example.com", "user1", "User 1", Date.valueOf(LocalDate.of(1990, 1, 1)));

        jdbcTemplate.update("INSERT INTO users (id,email, login, name, birthday) VALUES (?, ?, ?, ?, ?)", 2L, "user2@example.com", "user2", "User 2", Date.valueOf(LocalDate.of(1990, 1, 1)));

        // Добавление двух фильмов
        jdbcTemplate.update("INSERT INTO films (id, name, description, releaseDate, duration, rating) VALUES (?, ?, ?, ?, ?, ?)", 1L, "Film 1", "Description 1", Date.valueOf(LocalDate.of(2022, 1, 1)), Duration.ofMinutes(90).toMillis(), "G");

        jdbcTemplate.update("INSERT INTO films (id, name, description, releaseDate, duration, rating) VALUES (?, ?, ?, ?, ?, ?)", 2L, "Film 2", "Description 2", Date.valueOf(LocalDate.of(2022, 1, 1)), Duration.ofMinutes(90).toMillis(), "G");
    }

    @Test
    void addLike() {
        DbLikeStorage likeStorage = new DbLikeStorage(jdbcTemplate);
        long filmId = 1L;
        long userId = 1L;

        likeStorage.addLike(filmId, userId);

        List<Long> likes = likeStorage.getLikes(filmId);
        assertThat(likes).contains(userId);
    }

    @Test
    void removeLike() {
        DbLikeStorage likeStorage = new DbLikeStorage(jdbcTemplate);
        long filmId = 1L;
        long userId = 1L;

        likeStorage.addLike(filmId, userId);
        likeStorage.removeLike(filmId, userId);

        List<Long> likes = likeStorage.getLikes(filmId);
        assertThat(likes).doesNotContain(userId);
    }

    @Test
    void getLikes() {
        DbLikeStorage likeStorage = new DbLikeStorage(jdbcTemplate);
        long filmId = 1L;
        long userId1 = 1L;
        long userId2 = 2L;

        likeStorage.addLike(filmId, userId1);
        likeStorage.addLike(filmId, userId2);

        List<Long> likes = likeStorage.getLikes(filmId);
        assertThat(likes).containsExactlyInAnyOrder(userId1, userId2);
    }

    @Test
    void getLikesAmount() {
        DbLikeStorage likeStorage = new DbLikeStorage(jdbcTemplate);
        long filmId = 1L;
        long userId1 = 1L;
        long userId2 = 2L;

        likeStorage.addLike(filmId, userId1);
        likeStorage.addLike(filmId, userId2);

        long likesAmount = likeStorage.getLikesAmount(filmId);
        assertThat(likesAmount).isEqualTo(2);
    }

    @Test
    void getPopularFilmsId() {
        DbLikeStorage likeStorage = new DbLikeStorage(jdbcTemplate);
        long filmId1 = 1L;
        long filmId2 = 2L;
        long userId1 = 1L;
        long userId2 = 2L;

        likeStorage.addLike(filmId1, userId1);
        likeStorage.addLike(filmId1, userId2);

        likeStorage.addLike(filmId2, userId2);

        List<Long> popularFilmsId = likeStorage.getPopularFilmsId(1);
        assertThat(popularFilmsId).contains(1L);
    }
}