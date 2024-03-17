package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j
public class FilmController {

    @Autowired
    FilmService service;

    @PostMapping("/films")
    public ResponseEntity<Object> addFilm(@Valid @RequestBody Film film) {
        service.addFilm(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(film);
    }

    @PutMapping("/films")
    public ResponseEntity<Object> updateFilm(@Valid @RequestBody Film film) {
        service.updateFilm(film);
        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @GetMapping("/films/{filmId}")
    public ResponseEntity<Object> getFilm(@PathVariable("filmId") Long userId) {
        Film film = service.getFilm(userId);
        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @PutMapping("/films/{filmId}/like/{userId}")
    public ResponseEntity<Object> likeFilm(@PathVariable("filmId") Long filmId, @PathVariable("userId") Long userId) {
        service.likeFilm(filmId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/films/{filmId}/like/{userId}")
    public ResponseEntity<Object> removeLike(@PathVariable("filmId") Long filmId, @PathVariable("userId") Long userId) {
        service.removeLike(filmId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/films/popular")
    public ResponseEntity<Object> getPopularFilms(@RequestParam(value = "count", defaultValue = "10") int count) {
        List<Film> popularFilms = service.getPopularFilms(count);
        return ResponseEntity.status(HttpStatus.OK).body(popularFilms);
    }
}

