package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.DateMin;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {

    private int id;

    @NotBlank
    private String name;

    @Size(max=200)
    private String description;

    @Past
    @DateMin(value = "1895-12-28")
    private LocalDate releaseDate;

    @Positive
    private Duration duration;
}
