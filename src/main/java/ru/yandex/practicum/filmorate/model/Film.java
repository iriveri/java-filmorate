package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.DateMin;
import ru.yandex.practicum.filmorate.validation.DurationPositive;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {

    private Integer id;

    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @Past
    @DateMin(value = "1895-12-28")
    public LocalDate releaseDate;

    @DurationPositive
    private Duration duration;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
