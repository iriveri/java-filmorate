package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.DuplicateException;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    protected final HashMap<Long, Film> filmMap;
    protected Long counter;

    public InMemoryFilmStorage() {
        filmMap = new HashMap<>();
        counter = 0L;
    }

    @Override
    public void addFilm(Film film) {
        if (film == null) {
            log.warn("Попытка добавить null вместо фильма");
            throw new IllegalArgumentException("Пустой фильм не может быть добавлен");
        }
        if (film.getId() != null && filmMap.containsKey(film.getId())) {
            log.warn("Попытка добавить уже существующий фильм");
            throw new DuplicateException("Фильм с таким ID уже существует");
        }
        if (film.getId() == null) {
            film.setId(generateUniqueId());
        }

        try {
            filmMap.put(film.getId(), film);
            log.info("Фильм успешно добавлен");
        } catch (Exception e) {
            log.error("Ошибка при добавлении фильма", e);
            throw new RuntimeException("Ошибка при добавлении фильма");
        }
    }

    @Override
    public void updateFilm(Film film) {
        if (!filmMap.containsKey(film.getId())) {
            log.warn("Попытка обновить несуществующий фильм");
            throw new NotFoundExeption("Фильм с таким ID не найден");
        }

        try {
            filmMap.put(film.getId(), film);
            log.info("Фильм успешно обновлён");
        } catch (Exception e) {
            log.error("Ошибка при обновлении фильма", e);
            throw new IllegalStateException("Ошибка при обновлении фильма");
        }
    }

    @Override
    public Film getFilm(long id) {
        Film film = filmMap.get(id);
        if (film == null) {
            throw new NullPointerException("Фильма с таким ID не существует");
        }
        return film;
    }

    @Override
    public void deleteFilm(long id) {
        if (filmMap.get(id) == null) {
            throw new NullPointerException("Фильма с таким ID не существует");
        }
        filmMap.remove(id);
    }

    private long generateUniqueId() {
        counter++;
        while (filmMap.containsKey(counter)) {
            counter++;
        }
        return counter;
    }

    public List<Film> toList() {
        return new ArrayList<>(filmMap.values());
    }
}
