package gnaizel.inc.controller;

import gnaizel.inc.model.Film;
import gnaizel.inc.service.FilmService;
import gnaizel.inc.storage.film.impl.FilmDbStorage;
import gnaizel.inc.storage.film.FilmStorage;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    public FilmController(@Qualifier("FilmDbStorage")FilmDbStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public Film getFilmForId(@PathVariable int id) {
        return filmStorage.getFilm(id);
    }

    @GetMapping
    public Set<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) {
        log.info(film.toString());
        return filmStorage.postFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmStorage.updateFilm(film);
    }

    @PostMapping("/{id}/like/{userId}")
    public int likeFilm(@PathVariable int id, @PathVariable long userId) {
        return filmService.addLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable long userId) {
        filmService.deleteLike(userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getTop10(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.getTop10Films(count);
    }
}
