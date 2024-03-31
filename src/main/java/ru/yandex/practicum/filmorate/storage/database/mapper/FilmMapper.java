package ru.yandex.practicum.filmorate.storage.database.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class FilmMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film(rs.getLong("id"), rs.getString("name"), rs.getString("description"), rs.getDate("releaseDate").toLocalDate(), Duration.ofMillis(rs.getLong("duration")), null, null);
        Integer ratingId = (Integer) rs.getObject("rating_id");
        if (rs.wasNull()) {
            film.setMpa(null);
        } else {
            film.setMpa(new MpaRating(ratingId.longValue()));
        }

        return film;
    }
}