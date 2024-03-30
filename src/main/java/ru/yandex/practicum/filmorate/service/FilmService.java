package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;
    private final RatingStorage ratingStorage;


    @Autowired

    public FilmService(@Qualifier("DbFilmStorage") FilmStorage storage, @Qualifier("DbGenreStorage") GenreStorage genreStorage, @Qualifier("DbLikeStorage") LikeStorage likeStorage, @Qualifier("DbRatingStorage") RatingStorage ratingStorage) {
        this.filmStorage = storage;
        this.genreStorage = genreStorage;
        this.likeStorage = likeStorage;
        this.ratingStorage = ratingStorage;
    }

    public void addFilm(Film newFilm) {
        ratingStorage.validate(newFilm.getMpa());
        genreStorage.validate(newFilm.getGenres());

        filmStorage.addFilm(newFilm);
        genreStorage.addFilmGenre(newFilm.getId(), newFilm.getGenres());
    }

    public void updateFilm(Film existingFilm) {
        filmStorage.updateFilm(existingFilm);
    }

    public Film getFilm(long id) {
        Film film = filmStorage.getFilm(id);
        film.setMpa(ratingStorage.getMpaById(film.getMpa().getId()));
        film.setGenres(genreStorage.getFilmGenre(id));
        return film;
    }

    public List<Film> getAllFilms() {
        return filmStorage.toList();
    }

    public void deleteFilm(long id) {
        filmStorage.deleteFilm(id);
    }

    public void likeFilm(long filmId, long userId) {
        likeStorage.addLike(filmId, userId);
    }

    public void removeLike(long filmId, long userId) {
        likeStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int size) {

        List<Long> filmsId = likeStorage.getPopularFilmsId(size);

        return filmsId.stream().map(this::getFilm).collect(Collectors.toList());
    }
}
