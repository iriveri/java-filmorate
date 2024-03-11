package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    FilmStorage storage;

    public void addFilm(Film newFilm){
        storage.addFilm(newFilm);
    }
    public void updateFilm(Film existingFilm){
        storage.updateFilm(existingFilm);
    }
    public Film getFilm(int id)
    {
        return storage.getFilm(id);
    }
    public void deleteFilm(int id){
        storage.deleteFilm(id);
    }
    public void likeFilm(int id){

    }
    public void dislikeFilm(int id){

    }
    public List<Film> getTenMostLikedFilms(){
        return List.of();
    }
}
