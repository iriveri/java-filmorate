package ru.yandex.practicum.filmorate.model;

import lombok.Data;

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
    private LocalDate releaseDate;

    @Positive
    private Duration duration;
}
