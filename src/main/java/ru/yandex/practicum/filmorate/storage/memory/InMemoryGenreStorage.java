package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Qualifier("InMemoryGenreStorage")
public class InMemoryGenreStorage implements GenreStorage {
    private final Map<Long, List<Genre>> userGenresMap;

    public InMemoryGenreStorage() {
        this.userGenresMap = new HashMap<>();
    }

    @Override
    public void addGenre(Long filmId, Genre genre) {
        userGenresMap.computeIfAbsent(filmId, key -> new ArrayList<>()).add(genre);
    }

    @Override
    public void removeGenre(Long filmId, Genre genre) {
        List<Genre> genres = userGenresMap.get(filmId);
        if (genres != null) {
            genres.remove(genre);
        }
    }

    @Override
    public List<Genre> getGenres(Long filmId) {
        return userGenresMap.getOrDefault(filmId, new ArrayList<Genre>());
    }
}