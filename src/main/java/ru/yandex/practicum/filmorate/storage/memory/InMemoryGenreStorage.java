package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.*;


@Repository
@Qualifier("InMemoryGenreStorage")
public class InMemoryGenreStorage implements GenreStorage {
    private final Map<Long, List<Genre>> filmGenresMap;

    public InMemoryGenreStorage() {
        this.filmGenresMap = new HashMap<>();
    }

    @Override
    public void addFilmGenre(Long filmId, Genre genre) {
        filmGenresMap.computeIfAbsent(filmId, key -> new ArrayList<>()).add(genre);
    }

    @Override
    public void addFilmGenre(Long filmId, List<Genre> genres) {
        filmGenresMap.computeIfAbsent(filmId, key -> new ArrayList<>()).addAll(genres);
    }

    @Override
    public void removeFilmGenre(Long filmId, Genre genre) {
        List<Genre> genres = filmGenresMap.get(filmId);
        if (genres != null) {
            genres.remove(genre);
        }
    }

    @Override
    public void validate(List<Genre> genre) {
        // Метод validate не нужен для InMemoryGenreStorage
    }

    @Override
    public Genre getGenreById(Long id) {
        // Метод getGenreById не нужен для InMemoryGenreStorage
        return null;
    }

    @Override
    public List<Genre> getFilmGenre(Long filmId) {
        return filmGenresMap.getOrDefault(filmId, new ArrayList<>());
    }

    @Override
    public List<Genre> getGenresList() {
        // Метод getGenresList не нужен для InMemoryGenreStorage
        return null;
    }
}

