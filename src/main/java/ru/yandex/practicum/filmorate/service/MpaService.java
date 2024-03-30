package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.List;

@Service
public class MpaService {
    private final RatingStorage ratingStorage;

    public MpaService(@Qualifier("DbRatingStorage") RatingStorage mpaStorage) {
        ratingStorage = mpaStorage;
    }


    public List<MpaRating> getAllMpas() {
        return ratingStorage.getAllMpas();
    }

    public MpaRating getMpaById(Long id) {
        return ratingStorage.getMpaById(id);
    }
}
