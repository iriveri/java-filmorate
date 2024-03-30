package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;


@Repository
@Qualifier("DbGenreStorage")
public class DbGenreStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addGenre(Long userId, Genre genre) {
        String sql = "INSERT INTO film_genres (film_id, genre) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, genre.toString());
    }

    @Override
    public void removeGenre(Long userId, Genre genre) {
        String sql = "DELETE FROM film_genres WHERE film_id = ? AND genre = ?";
        jdbcTemplate.update(sql, userId, genre.toString());
    }

    @Override
    public List<Genre> getGenres(Long userId) {
        String sql = "SELECT genre FROM film_genres WHERE film_id = ?";
        return jdbcTemplate.queryForList(sql, Genre.class, userId);
    }
}