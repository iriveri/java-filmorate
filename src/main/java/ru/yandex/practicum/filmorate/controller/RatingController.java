package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class RatingController {

    private final MpaService mpaService;

    @Autowired
    public RatingController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllMpas() {
        List<MpaRating> mpas = mpaService.getAllMpas();
        return ResponseEntity.ok().body(mpas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMpaById(@PathVariable Long id) {
        MpaRating mpa = mpaService.getMpaById(id);
        return ResponseEntity.ok().body(mpa);
    }
}