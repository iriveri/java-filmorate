package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmRatingMPA;
import ru.yandex.practicum.filmorate.model.Genre;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTests {

    @Autowired
    private FilmController filmController;

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @DisplayName("Correct film should be validated correctly")
    void correctFilmShouldNotThrowException() throws Exception {
        Film snatch = new Film(null, "Snatch", "Описание фильма \"Большой куш\":",
                LocalDate.of(2000, 5, 11),
                Duration.ofMinutes(104), List.of(Genre.Comedy, Genre.ActionFilm),
                FilmRatingMPA.R);

        performFilmPostAndExpectStatus(snatch, HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Film name should be validated correctly")
    void filmNameShouldNotBeEmpty() throws Exception {
        Film snatch = new Film(null, "", "Описание фильма \"Большой куш\":",
                LocalDate.of(2000, 5, 11),
                Duration.ofMinutes(104), List.of(Genre.Comedy, Genre.ActionFilm),
                FilmRatingMPA.R);

        performFilmPostAndExpectStatus(snatch, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Film description should be less than 200")
    void filmDescriptionShouldBeLessThan200() throws Exception {
        // 201 words description
        Film snatch = new Film(null, "Snatch", "Описание фильма \"Большой куш\":" +
                " Четверо друзей решают ограбить казино" +
                " в Лондоне, но все идет не по плану. Им" +
                " приходится столкнуться с мафией и полицией," +
                " чтобы выжить. Фильм полон черного юмора," +
                " неожиданных поворотов и интересных персонажей." +
                " Напряженный сюжет и харизматичные актеры" +
                " делают его шедевром жанра криминальной комедии.",
                LocalDate.of(2000, 5, 11),
                Duration.ofMinutes(104), List.of(Genre.Comedy, Genre.ActionFilm),
                FilmRatingMPA.R);

        performFilmPostAndExpectStatus(snatch, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Film release date should be validated correctly")
    void filmReleaseDateShouldBeInCorrectBounds() throws Exception {
        Film snatch = new Film(null, "Snatch", "Описание фильма \"Большой куш\":",
                LocalDate.of(1700, 5, 11),
                Duration.ofMinutes(104), List.of(Genre.Comedy, Genre.ActionFilm),
                FilmRatingMPA.R);

        performFilmPostAndExpectStatus(snatch, HttpStatus.BAD_REQUEST);

        snatch.setReleaseDate(LocalDate.of(2700, 5, 11));
        performFilmPostAndExpectStatus(snatch, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Film duration should be validated correctly")
    void filmDurationShouldBeGreaterThanZero() throws Exception {
        Film snatch = new Film(null, "Snatch",
                "Описание фильма \"Большой куш\":",
                LocalDate.of(2000, 5, 11),
                Duration.ofMinutes(-1), List.of(Genre.Comedy, Genre.ActionFilm),
                FilmRatingMPA.R);

        performFilmPostAndExpectStatus(snatch, HttpStatus.BAD_REQUEST);

        snatch.setDuration(Duration.ofMinutes(20));
        performFilmPostAndExpectStatus(snatch, HttpStatus.CREATED);
    }

    private void performFilmPostAndExpectStatus(Film film, HttpStatus status) throws Exception {
        String filmJson = objectMapper.writeValueAsString(film);
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .content(filmJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(status.value()));
    }
}
