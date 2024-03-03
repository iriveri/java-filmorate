package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FilmController {
    private final Map<Integer,Film> filmMap = new HashMap<>();
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
    public Map<Integer,Film> getAllFilms() {
        return filmMap;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
