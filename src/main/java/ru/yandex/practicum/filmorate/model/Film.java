package ru.yandex.practicum.filmorate.model;


import lombok.Data;

import javax.validation.constraints.Size;
import javax.validation.constraints.Past;
import javax.validation.constraints.NotBlank;

import ru.yandex.practicum.filmorate.validation.DateMin;
import ru.yandex.practicum.filmorate.validation.DurationPositive;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.yandex.practicum.filmorate.serialization.DurationSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;


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

    private MpaRating mpa;

    private List<Genre> genres;

    public Film() {
    }

    public Film(Long id, String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(Long id, String name, String description, LocalDate releaseDate, Duration duration, MpaRating mpa, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genres;
    }
}