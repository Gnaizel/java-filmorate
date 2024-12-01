package ru.yandex.practicum.filmorate.storage.film.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.NotFoundFilmId;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Integer, Film> films = new HashMap<>();

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
    public void addLike(int filmId, long userId) {
        String s;
    }

    @Override
    public void deleteLike(int filmId, long userId) {

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
