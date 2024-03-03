package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;

/**
 * Film.
 */
@Data
public class Film {
    private final int id;
    private String name;
    private String description;
    private Date releaseDate;
    private Duration duration;
}
