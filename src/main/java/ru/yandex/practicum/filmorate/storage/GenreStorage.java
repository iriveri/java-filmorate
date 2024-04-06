package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    void addFilmGenre(Long filmId, Genre genre);

    void addFilmGenre(Long filmId, List<Genre> genres);

    Genre getGenreById(Long id);

    List<Genre> getFilmGenre(Long filmId);

    void removeFilmGenre(Long filmId, Genre genre);

    void validate(List<Genre> genre);


    List<Genre> getGenresList();
}
