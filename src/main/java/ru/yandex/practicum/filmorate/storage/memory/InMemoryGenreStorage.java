package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.*;


@Repository
@Qualifier("InMemoryGenreStorage")
public class InMemoryGenreStorage implements GenreStorage {
    private final Map<Long, List<Genre>> userGenresMap;
    private static final String[] genreList = {
            "Action",
            "Comedy",
            "Drama",
            "Cartoon",
            "Thriller",
            "Documentary",
            "ActionFilm"
    };

    public InMemoryGenreStorage() {
        this.userGenresMap = new HashMap<>();
    }

    @Override
    public void addFilmGenre(Long filmId, Genre genre) {
        userGenresMap.computeIfAbsent(filmId, key -> new ArrayList<>()).add(genre);
    }

    @Override
    public void addFilmGenre(Long filmId, List<Genre> genres) {

    }

    @Override
    public void removeFilmGenre(Long filmId, Genre genre) {
        List<Genre> genres = userGenresMap.get(filmId);
        if (genres != null) {
            genres.remove(genre);
        }
    }

    @Override
    public void validate(List<Genre> genre) {

    }

    @Override
    public     Genre getGenreById(Long id) {
        return null;
    }
    @Override
    public List<Genre> getFilmGenre(Long filmId){
        return null;
    }

    @Override
    public List<Genre> getGenresList() {
        return null;
    }

}