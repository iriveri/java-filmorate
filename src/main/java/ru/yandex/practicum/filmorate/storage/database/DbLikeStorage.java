package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.List;

@Repository
@Qualifier("DbLikeStorage")
public class DbLikeStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbLikeStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String sql = "INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        String sql = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Long> getLikes(Long filmId) {
        String sql = "SELECT user_id FROM film_likes WHERE film_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, filmId);
    }

    @Override
    public Long getLikesAmount(Long filmId) {
        String sql = "SELECT COUNT(*) FROM film_likes WHERE film_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, filmId);
    }

    @Override
    public List<Long> getPopularFilmsId(int size) {
        String sql = "SELECT film_id FROM film_likes GROUP BY film_id ORDER BY COUNT(*) DESC LIMIT ?";
        return jdbcTemplate.queryForList(sql, Long.class, size);
    }
}