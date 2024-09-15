package gnaizel.inc.controller;

import gnaizel.inc.exception.ValidationException;
import gnaizel.inc.model.Film;
import jakarta.validation.Valid;
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
    public Film postFilm(@Valid @RequestBody Film film) {
        if (films.containsValue(film)) {
            throw new ValidationException("фильм с таким названием уже есть");
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

        Film build = film.toBuilder()
                .id(nextIdCreate())
                .build();
        films.put(build.getId(), build);
        log.info("Запостили фильм{}", build.getName());
        return build;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {

        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильма с таким id не существует");
        }

        films.replace(film.getId(), film);
        log.debug("Отредактирован фильм {}", film.getName());
        return film;
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
