package ru.yandex.practicum.filmorate.model;


import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import javax.validation.constraints.Past;
import javax.validation.constraints.NotBlank;

import ru.yandex.practicum.filmorate.validation.DateMin;
import ru.yandex.practicum.filmorate.validation.DurationPositive;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.yandex.practicum.filmorate.serialization.DurationSerializer;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {

    private Long id;

    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @Past
    @DateMin(value = "1895-12-28")
    private LocalDate releaseDate;

    @DurationPositive
    @JsonSerialize(using = DurationSerializer.class)
    private Duration duration;

    @Enumerated(EnumType.STRING)
    private FilmRatingMPA rating;

    public Film(Long id, String name, String description, LocalDate releaseDate, Duration duration, FilmRatingMPA rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rating = rating;
    }

}
