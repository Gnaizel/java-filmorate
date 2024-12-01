package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film getFilm(int idFilm);

    List<Film> getFilms();

    Film postFilm(Film film);

    Film updateFilm(Film film);

    void addLike(int filmId, long userId);

    void deleteLike(int filmId, long userId);

    List<Film> getPopular(long count);
}
