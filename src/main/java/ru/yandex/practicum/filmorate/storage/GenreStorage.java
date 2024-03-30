package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    void addGenre(Long filmId, Genre genre);

    void removeGenre(Long filmId, Genre genre);

    List<Genre> getGenres(Long filmId);
}
