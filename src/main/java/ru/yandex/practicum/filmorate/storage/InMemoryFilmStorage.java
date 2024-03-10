package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    protected final HashMap<Integer, Film> filmMap;
    protected Integer counter;

    public InMemoryFilmStorage() {
        filmMap = new HashMap<>();
        counter = 0;
    }

    @Override
    public void addFilm(Film film) throws RuntimeException {
        if (film == null) {
            throw new RuntimeException("Передано пустое поле");
        }
        if (film.getId() != null && filmMap.containsKey(film.getId())) {
            throw new RuntimeException("Фильм с таким ID уже существует");
        }
        if (film.getId() == null) {
            film.setId(generateUniqueId());
        }

        try {
            filmMap.put(film.getId(), film);
            log.info("Фильм успешно добавлен");
        } catch (Exception e) {
            log.error("Ошибка при добавлении пользователя", e);
            throw new RuntimeException("Фильм с таким ID уже существует");
        }
    }
    @Override
    public void updateFilm(Film film) {
        if (!filmMap.containsKey(film.getId())) {
            throw new RuntimeException("Фильм с таким ID уже существует");
        }

        try {
            filmMap.put(film.getId(), film);
            log.info("Пользователь успешно обновлён");
        } catch (Exception e) {
            log.error("Ошибка при обновлении пользователя", e);
            throw new RuntimeException("Фильм с таким ID уже существует");
        }
    }

    @Override
    public Film getFilm(int id) {
        Film film = filmMap.get(id);
        if (film == null) {
            return null;
        }
        return film;
    }

    @Override
    public void deleteFilm(int id) {
        Film task = filmMap.get(id);


        filmMap.remove(id);
    }


    private int generateUniqueId() {
        counter++;
        while (filmMap.containsKey(counter)) {
            counter++;
        }
        return counter;
    }
}
