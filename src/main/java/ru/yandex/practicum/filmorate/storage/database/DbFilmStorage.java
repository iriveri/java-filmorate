package ru.yandex.practicum.filmorate.storage.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.DuplicateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FilmRatingMPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;


@Slf4j
@Repository
@Qualifier("DbFilmStorage")
public class DbFilmStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilm(Film film) {
        Long id = film.getId();
        if (id != null && isFilmExistsById(id)) {
            log.warn("Фильм с таким ID {} уже существует", id);
            throw new DuplicateException("Фильм с таким ID " + id + " уже существует");
        }

        String sqlQuery = "INSERT INTO films (name, description, releaseDate, duration, rating) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                stmt.setString(1, film.getName());
                stmt.setString(2, film.getDescription());
                stmt.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
                stmt.setTime(4, java.sql.Time.valueOf(film.getDuration().toString()));
                stmt.setString(5, film.getRating().toString());
                return stmt;
            }, keyHolder);
            long newId = keyHolder.getKey().longValue();
            film.setId(newId);
            log.info("Фильм {} успешно добавлен", newId);
        } catch (DataAccessException e) {
            log.error("Ошибка при добавлении фильма", e);
            throw new RuntimeException("Ошибка при добавлении фильма");
        }
    }

    @Override
    public void updateFilm(Film film) {
        Long id = film.getId();
        if (!isFilmExistsById(id)) {
            log.warn("Фильм с таким ID {} не найден", id);
            throw new NotFoundException("Фильм с таким ID " + id + " не найден");
        }
        String sqlQuery = "UPDATE films SET name=?, description=?, releaseDate=?, duration=?, rating=? WHERE id = ? ";
        try {
            jdbcTemplate.update(sqlQuery,
                    film.getName(),
                    film.getDescription(),
                    java.sql.Date.valueOf(film.getReleaseDate()),
                    java.sql.Time.valueOf(film.getDuration().toString()),
                    film.getRating().toString());
            log.info("Фильм {} успешно обновлён", id);
        } catch (DataAccessException e) {
            log.error("Ошибка при добавлении фильма", e);
            throw new RuntimeException("Ошибка при добавлении фильма");
        }
    }

    @Override
    public Film getFilm(long id) {
        try{
        String sqlQuery = "SELECT * FROM films WHERE id=? LIMIT 1";
            Film film = jdbcTemplate.queryForObject(sqlQuery, new FilmMapper(), id);
            log.info("Фильм {} успешно извлечён", id);
            return film;
        } catch (DataAccessException e) {
            log.warn("Фильм с таким ID {} не найден", id);
            throw new NotFoundException("Фильм с таким ID " + id + " не найден");
        }
    }

    @Override
    public void deleteFilm(long id) {
        try {
            jdbcTemplate.update("DELETE FROM films WHERE id=?", id);
            log.info("Фильм {} успешно удалён", id);
        } catch (DataAccessException e) {
            log.warn("Фильм с таким ID {} не найден", id);
            throw new NotFoundException("Фильм с таким ID " + id + " не найден");
        }
    }

    @Override
    public List<Film> toList() {
        try {
            List<Film> films = jdbcTemplate.query("SELECT * FROM films", new FilmMapper());
            log.info("Список фильмов успешно извлечён");
            return films;
        } catch (DataAccessException e) {
            log.error("Ошибка при извлечении списка фильмов", e);
            throw new RuntimeException("Ошибка при извлечении списка фильмов");
        }
    }

    private boolean isFilmExistsById(long id) {
        String sqlQuery = "SELECT COUNT(*) FROM films WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return count > 0;
    }

    private static class FilmMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Film(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("releaseDate").toLocalDate(),
                    Duration.ofMillis(rs.getTime("duration").getTime()),
                    FilmRatingMPA.valueOf(rs.getString("rating")));
        }
    }
}