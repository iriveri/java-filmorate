package ru.yandex.practicum.filmorate.storage.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.database.mapper.GenreMapper;

import java.util.List;

@Repository
@Qualifier("DbGenreStorage")
@Slf4j
public class DbGenreStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilmGenre(Long filmId, Genre genreId) {
        String sql = "INSERT INTO film_genres (film_id, genre_id)" + "SELECT ?, ?" + "WHERE NOT EXISTS (" + "    SELECT 1 FROM film_genres" + "    WHERE film_id = ? AND genre_id = ?" + ")";
        try {
            jdbcTemplate.update(sql, filmId, genreId.getId(), filmId, genreId.getId());
            log.info("Жанр фильма {} успешно добавлен", filmId);
        } catch (DataAccessException e) {
            log.warn("Ошибка при добавлении жанра", e);
            throw new RuntimeException("Ошибка при добавлении жанра");
        }
    }

    @Override
    public void addFilmGenre(Long userId, List<Genre> genres) {
        if (genres != null) {
            for (Genre g : genres) {
                addFilmGenre(userId, g);
            }
        }
    }

    @Override
    public void removeFilmGenre(Long filmId, Genre genreId) {
        String sql = "DELETE FROM film_genres WHERE film_id = ? AND genre_id = ?";
        try {
            jdbcTemplate.update(sql, filmId, genreId.getId());
            log.info("Жанр фильма {} успешно удалён", filmId);
        } catch (DataAccessException e) {
            log.error("Ошибка при удалении жанра", e);
            throw new RuntimeException("Ошибка при удалении жанра");
        }
    }

    @Override
    public void validate(List<Genre> genres) {
        String sqlQuery = "SELECT MAX(id) FROM GENRE";
        Integer id = jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        if (genres != null) {
            for (Genre g : genres) {
                if (g.getId() > id) {
                    throw new NotValidException("Неверное значение параметра genres");
                }
            }
        }
    }

    @Override
    public Genre getGenreById(Long id) {
        String sql = "SELECT id, name FROM genre Where id = ?";
        try {
            Genre genre = jdbcTemplate.queryForObject(sql, new GenreMapper(), id);
            log.info("Упешно излечено название жанра {}", id);
            return genre;
        } catch (Exception e) {
            log.warn("Название жанра {} не найдено", id);
            throw new NotFoundException("Название жанра " + id + " не найдено");
        }
    }

    @Override
    public List<Genre> getFilmGenre(Long filmId) {
        String sql = "SELECT g.id, g.name FROM FILM_GENRES f LEFT JOIN GENRE g ON g.ID = f.GENRE_ID WHERE f.FILM_ID = ?";
        try {
            List<Genre> genres = jdbcTemplate.query(sql, new GenreMapper(), filmId);
            log.info("Успешно извлечены жанры фильма {}", filmId);
            return genres;
        } catch (Exception e) {
            log.warn("Ошибка при извлечении жанров фильма", e);
            throw new NotFoundException("Ошибка при извлечении жанров фильма");
        }
    }

    @Override
    public List<Genre> getGenresList() {
        String sql = "SELECT id, name FROM genre";
        try {
            List<Genre> genres = jdbcTemplate.query(sql, new GenreMapper());
            log.info("Список жаноров успешно извлечён");
            return genres;
        } catch (Exception e) {
            log.warn("Ошибка при извлечении списка жанров");
            throw new NotFoundException("Ошибка при извлечении списка жанров");
        }
    }
}