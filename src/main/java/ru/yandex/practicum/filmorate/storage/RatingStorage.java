package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface RatingStorage {
    List<MpaRating> getAllMpas();

    MpaRating getMpaById(Long id);

    void validate(MpaRating rating);

}
