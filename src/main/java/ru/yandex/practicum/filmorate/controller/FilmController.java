package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> filmMap = new HashMap<>();
    private static int counter = 0;

    @PostMapping("/films")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Film film) {
        if (film.getId() != null && filmMap.containsKey(film.getId())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Пользователь с таким ID уже существует"));
        }

        if (film.getId() == null) {
            film.setId(generateUniqueId());
        }

        try {
            filmMap.put(film.getId(), film);
            log.info("Фильм успешно добавлен");
            return ResponseEntity.ok(film);
        } catch (Exception e) {
            log.error("Ошибка при добавлении пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/films")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody Film film) {
        if (!filmMap.containsKey(film.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(film);
        }

        try {
            filmMap.put(film.getId(), film);
            log.info("Пользователь успешно обновлён");
            return ResponseEntity.ok(film);
        } catch (Exception e) {
            log.error("Ошибка при обновлении пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/films")
    public ArrayList<Film> getAllUsers() {
        return new ArrayList<>(filmMap.values());
    }

    private int generateUniqueId() {
        counter++;
        while (filmMap.containsKey(counter)) {
            counter++;
        }
        return counter;
    }
}
