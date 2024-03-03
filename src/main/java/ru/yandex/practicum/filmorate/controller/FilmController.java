package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@RestController
public class FilmController {
    private final HashMap<Integer,Film> filmMap = new HashMap<>();
    @PostMapping("/film")
    public Film addFilm(@Valid @RequestBody Film film) {
        filmMap.put(film.getId(),film);
        return film;
    }

    @PutMapping("/film")
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmMap.put(film.getId(),film);
        return film;
    }
    @GetMapping("/films")
    public HashMap<Integer,Film> getAllFilms() {
        return filmMap;
    }
}
