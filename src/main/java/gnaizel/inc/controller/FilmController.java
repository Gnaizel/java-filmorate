package gnaizel.inc.controller;

import gnaizel.inc.exception.ValidationException;
import gnaizel.inc.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping
    public Set<Film> getFilms() {
        log.debug("Вернул список фильмов");
        return new HashSet<>(films.values());
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) {
        if (films.containsValue(film)) {
            throw new ValidationException("фильм с таким названием уже есть");
        }
        if (film.getName() == null
                || film.getDescription() == null
                || film.getDuration() == null
                || film.getReleaseDate() == null) {
            throw new ValidationException("Поля Name, описание, продолжительность и дата релиза обязательны");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание должно быть меньше 200 символов");
        }

        if (film.getReleaseDate()
                .isBefore((
                        LocalDateTime.of(1895, 12, 28, 0, 0)
                                .toLocalDate()))) {
            throw new ValidationException("Дата релиза не может быть ранеьше 1895г 28 дек");
        }

        film.setId(nextIdCreate());
        films.put(film.getId(), film);
        log.info("Запостили фильм{}", film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {

        if (!films.containsKey(film.getId())) {
            throw new ValidationException("пользователя с таким id не существует");
        }

        Film existingFilm = films.get(film.getId());

        if (film.getName() != null) {
            existingFilm.setName(film.getName());
        }
        if (film.getDescription() != null) {
            existingFilm.setDescription(film.getDescription());
        }
        if (film.getReleaseDate() != null) {
            existingFilm.setReleaseDate(film.getReleaseDate());
        }
        if (film.getDuration() != null) {
            existingFilm.setDuration(film.getDuration());
        }

        films.replace(existingFilm.getId(), existingFilm);
        log.debug("Отредактирован фильм {}", film.getName());
        return existingFilm;
    }

    private int nextIdCreate() {
        int nextId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);

        return ++nextId;
    }
}
