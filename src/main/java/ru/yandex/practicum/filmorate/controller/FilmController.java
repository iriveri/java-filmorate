package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

@RestController
public class FilmController {
    private final HashMap<Integer,Film> filmMap = new HashMap<>();
    @PostMapping("/film")
    public Film addFilm(@RequestBody Film film) {
        filmMap.put(film.getId(),film);
        return film;
    }

    @PutMapping("/film")
    public Film updateFilm(@RequestBody Film film) {
        filmMap.put(film.getId(),film);
        return film;
    }
    @GetMapping("/film")
    public HashMap<Integer,Film> getAllFilms() {
        return filmMap;
    }
}
