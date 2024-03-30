package ru.yandex.practicum.filmorate.storage.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Qualifier("DbRatingStorage")
@Slf4j
public class DbRatingStorage implements RatingStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbRatingStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MpaRating> getAllMpas() {
        String sqlQuery = "SELECT id, name FROM rating";
        try {
            return jdbcTemplate.query(sqlQuery, new RatingMapper());
        } catch (Exception e) {
            log.error("Ошибка при получении списка рейтингов", e);
            throw new RuntimeException("Ошибка при получении списка рейтингов");
        }
    }

    @Override
    public MpaRating getMpaById(Long id) {
        String sqlQuery = "SELECT id, name FROM rating WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new RatingMapper(), id);
        } catch (Exception e) {
            log.error("Рейтинг по данному id не найден", e);
            throw new NotFoundException("Рейтинг по данному id не найден");
        }
    }

    @Override
    public void validate(MpaRating rating) {
        String sqlQuery = "SELECT MAX(id) FROM rating";
        Integer id = jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        if (rating != null && rating.getId() > id) {
            throw new NotValidException("Неверное значение параметра Mpa");
        }
    }

    private static class RatingMapper implements RowMapper<MpaRating> {
        @Override
        public MpaRating mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MpaRating(rs.getLong("id"), rs.getString("name"));
        }
    }
}
