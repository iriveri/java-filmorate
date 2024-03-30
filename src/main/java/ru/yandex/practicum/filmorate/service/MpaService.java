package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.List;

@Service
public class MpaService {
    private final RatingStorage MpaStorage;

    public MpaService(@Qualifier("DbRatingStorage") RatingStorage mpaStorage) {
        MpaStorage = mpaStorage;
    }


    public List<MpaRating> getAllMpas(){
        return MpaStorage.getAllMpas();
    }

    public MpaRating getMpaById(Long id){
        return MpaStorage.getMpaById(id);
    }
}
