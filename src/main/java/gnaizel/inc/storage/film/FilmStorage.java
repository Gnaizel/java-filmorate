package gnaizel.inc.storage.film;

import gnaizel.inc.model.Film;

import java.util.List;

public interface FilmStorage {
    Film getFilm(int idFilm);

    List<Film> getFilms();

    Film postFilm(Film film);

    Film updateFilm(Film film);

    void addLike(int film_id, long userId);

    void deleteLike(int film_id, long userId);

    List<Film> getPopular(long count);
}
