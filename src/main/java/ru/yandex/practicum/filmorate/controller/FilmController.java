package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.impl.FilmDbStorage;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    public FilmController(@Qualifier("FilmDbStorage") FilmDbStorage filmStorage, FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable int id) {
        return filmService.getFilmForId(id);
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.getFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.postFilm(film);
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void putLike(@PathVariable int id, @PathVariable long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable long userId) {
        filmService.deleteLike(userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getMostRatedFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.getTop10Films(count);
    }
}
