package ru.yandex.practicum.filmorate.storage.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class DbGenreStorageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO films (id, name, description, releaseDate, duration, rating) VALUES (?, ?, ?, ?, ?, ?)", 1L, "Film 1", "Description 1", Date.valueOf(LocalDate.of(2022, 1, 1)), Duration.ofMinutes(90).toMillis(), "G");
    }

    @Test
    void addGenre() {
        DbGenreStorage genreStorage = new DbGenreStorage(jdbcTemplate);
        Genre genre = Genre.ActionFilm;

        genreStorage.addGenre(1L, genre);

        List<Genre> genres = genreStorage.getGenres(1L);
        assertThat(genres).contains(genre);
    }

    @Test
    void removeGenre() {
        DbGenreStorage genreStorage = new DbGenreStorage(jdbcTemplate);
        Genre genre = Genre.ActionFilm;

        genreStorage.addGenre(1L, genre);
        genreStorage.removeGenre(1L, genre);

        List<Genre> genres = genreStorage.getGenres(1L);
        assertThat(genres).doesNotContain(genre);
    }

    @Test
    void getGenres() {
        DbGenreStorage genreStorage = new DbGenreStorage(jdbcTemplate);
        Genre genre1 = Genre.ActionFilm;
        Genre genre2 = Genre.Comedy;

        genreStorage.addGenre(1L, genre1);
        genreStorage.addGenre(1L, genre2);

        List<Genre> genres = genreStorage.getGenres(1L);
        assertThat(genres).containsExactlyInAnyOrder(genre1, genre2);
    }
}
