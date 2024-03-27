package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage storage;

    @Autowired
    public FilmService(FilmStorage storage) {
        this.storage = storage;
    }

    public void addFilm(Film newFilm) {
        storage.addFilm(newFilm);
    }

    public void updateFilm(Film existingFilm) {
        storage.updateFilm(existingFilm);
    }

    public Film getFilm(long id) {
        return storage.getFilm(id);
    }

    public List<Film> getAllFilms() {
        return storage.toList();
    }

    public void deleteFilm(long id) {
        storage.deleteFilm(id);
    }

    public void likeFilm(long filmId, long userId) {
        var film = storage.getFilm(filmId);
        film.addLike(userId);
    }

    public void removeLike(long filmId, long userId) {
        var film = storage.getFilm(filmId);
        film.removeLike(userId);
    }

    public List<Film> getPopularFilms(int size) {
        List<Film> allFilms = storage.toList();

        // Сортируем фильмы по количеству лайков в убывающем порядке
        allFilms.sort(Comparator.comparingInt(Film::getLikesSize).reversed());

        return allFilms.stream()
                .limit(size)
                .collect(Collectors.toList());
    }
}
