package gnaizel.inc.storage.film;

import gnaizel.inc.model.Film;

import java.util.Set;

public interface FilmStorage {
    Film getFilm(int idFilm);

    Set<Film> getFilms();

    Film postFilm(Film film);

    Film updateFilm(Film film);
}
