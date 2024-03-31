package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    void addFilm(Film newFilm);

    void updateFilm(Film existingFilm);

    Film getFilm(long id);

    void deleteFilm(long id);

    List<Film> getAllFilmsList();
}
