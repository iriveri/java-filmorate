package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    void addFilm(Film newFilm);
    void updateFilm(Film existingFilm);
    Film getFilm(int id);
    void deleteFilm(int id);
}
