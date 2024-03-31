package ru.yandex.practicum.filmorate.storage.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;


import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class DbFilmStorageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void addFilm() {
        DbFilmStorage filmStorage = new DbFilmStorage(jdbcTemplate);
        Film film = new Film(null, "Test Film", "Test Description", LocalDate.of(2022, 1, 1), Duration.ofMinutes(90), null, null);

        filmStorage.addFilm(film);

        Film retrievedFilm = filmStorage.getFilm(film.getId());
        assertThat(retrievedFilm).isEqualTo(film);
    }

    @Test
    void updateFilm() {
        DbFilmStorage filmStorage = new DbFilmStorage(jdbcTemplate);
        Film film = new Film(null, "Test Film", "Test Description", LocalDate.of(2022, 1, 1), Duration.ofMinutes(90), null, null);

        filmStorage.addFilm(film);
        film.setMpa(new MpaRating(2L));
        filmStorage.updateFilm(film);

        Film retrievedFilm = filmStorage.getFilm(film.getId());
        assertThat(retrievedFilm).isEqualTo(film);
    }

    @Test
    void getFilm() {
        DbFilmStorage filmStorage = new DbFilmStorage(jdbcTemplate);
        Film film = new Film(null, "Test Film", "Test Description", LocalDate.of(2022, 1, 1), Duration.ofMinutes(90), null, null);

        filmStorage.addFilm(film);

        Film retrievedFilm = filmStorage.getFilm(film.getId());
        assertThat(retrievedFilm).isEqualTo(film);
    }

    @Test
    void deleteFilm() {
        DbFilmStorage filmStorage = new DbFilmStorage(jdbcTemplate);
        Film film = new Film(999L, "Test Film", "Test Description", LocalDate.of(2022, 1, 1), Duration.ofMinutes(90), null, null);

        filmStorage.addFilm(film);
        filmStorage.deleteFilm(999L);

        Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> filmStorage.getFilm(1L));
        assertThat(thrown).isInstanceOf(NotFoundException.class);
    }

    @Test
    void toList() {
        DbFilmStorage filmStorage = new DbFilmStorage(jdbcTemplate);
        Film film1 = new Film(null, "Test Film 1", "Test Description 1", LocalDate.of(2022, 1, 1), Duration.ofMinutes(90), null, null);
        Film film2 = new Film(null, "Test Film 2", "Test Description 2", LocalDate.of(2023, 2, 2), Duration.ofMinutes(85), null, null);

        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);

        List<Film> filmList = filmStorage.getAllFilmsList();
        assertThat(filmList).containsExactly(film1, film2);
    }
}
