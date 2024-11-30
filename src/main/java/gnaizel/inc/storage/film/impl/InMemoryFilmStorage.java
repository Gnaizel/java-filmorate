package gnaizel.inc.storage.film.impl;

import gnaizel.inc.controller.FilmController;
import gnaizel.inc.exception.NotFoundFilmId;
import gnaizel.inc.exception.ValidationException;
import gnaizel.inc.model.Film;
import gnaizel.inc.storage.film.FilmStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    public List<Film> getFilms() {
        log.debug("Вернул список фильмов");
        return films.values().stream().toList();
    }

    public Film getFilm(int idFilm) {
        if (!films.containsKey(idFilm)) throw new NotFoundFilmId("NOT_VALID_FILM_ID");
        return films.get(idFilm);
    }

    public Film postFilm(Film film) {
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

    public Film updateFilm(Film film) {

        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильма с таким id не существует");
        }

        films.replace(film.getId(), film);
        log.debug("Отредактирован фильм {}", film.getName());
        return film;
    }

    @Override
    public void addLike(int film_id, long userId) {

    }

    @Override
    public void deleteLike(int film_id, long userId) {

    }

    @Override
    public List<Film> getPopular(long count) {
        return List.of();
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
