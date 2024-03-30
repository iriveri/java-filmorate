package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Object> addFilm(@Valid @RequestBody Film film) {
        service.addFilm(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(film);
    }

    @PutMapping()
    public ResponseEntity<Object> updateFilm(@Valid @RequestBody Film film) {
        service.updateFilm(film);
        return ResponseEntity.ok().body(film);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllFilms() {
        List<Film> films = service.getAllFilms();
        return ResponseEntity.ok().body(films);
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<Object> getFilm(@PathVariable("filmId") Long userId) {
        Film film = service.getFilm(userId);
        return ResponseEntity.ok().body(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public ResponseEntity<Object> likeFilm(@PathVariable("filmId") Long filmId, @PathVariable("userId") Long userId) {
        service.likeFilm(filmId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{filmId}/like/{id}")
    public ResponseEntity<Object> removeLike(@PathVariable("filmId") Long filmId, @PathVariable("id") Long userId) {
        service.removeLike(filmId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam() int count) {
        List<Film> popularFilms = service.getPopularFilms(count);
        return ResponseEntity.ok(popularFilms);
    }
}

