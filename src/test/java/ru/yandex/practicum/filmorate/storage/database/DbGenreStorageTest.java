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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class DbGenreStorageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO films (id, name, description, releaseDate, duration, RATING_ID) VALUES (?, ?, ?, ?, ?, ?)", 1L, "Film 1", "Description 1", Date.valueOf(LocalDate.of(2022, 1, 1)), Duration.ofMinutes(90).toMillis(), 1);
    }

    @Test
    void addGenre() {
        DbGenreStorage genreStorage = new DbGenreStorage(jdbcTemplate);
        Genre genre = new Genre(1L);

        genreStorage.addFilmGenre(1L, genre);

        List<String> genres = genreStorage.getFilmGenre(1L)
                .stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
        assertThat(genres).contains("Комедия");
    }

    @Test
    void removeGenre() {
        DbGenreStorage genreStorage = new DbGenreStorage(jdbcTemplate);
        Genre genre = new Genre(1L);

        genreStorage.addFilmGenre(1L, genre);
        genreStorage.removeFilmGenre(1L, genre);

        List<String> genres = genreStorage.getFilmGenre(1L)
                .stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
        assertThat(genres).doesNotContain("Комедия");
    }

    @Test
    void getGenres() {
        DbGenreStorage genreStorage = new DbGenreStorage(jdbcTemplate);
        Genre genre1 = new Genre(1L);
        Genre genre2 = new Genre(2L);

        genreStorage.addFilmGenre(1L, genre1);
        genreStorage.addFilmGenre(1L, genre2);

        List<String> genres = genreStorage.getFilmGenre(1L)
                .stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
        assertThat(genres).containsExactlyInAnyOrder("Комедия", "Драма");
    }
}
