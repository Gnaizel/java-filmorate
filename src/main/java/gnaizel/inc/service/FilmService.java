package gnaizel.inc.service;

import gnaizel.inc.model.Film;
import gnaizel.inc.storage.film.FilmStorage;
import gnaizel.inc.storage.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public int addLike(long user, int idFilm) {
        filmStorage.getFilm(idFilm)
                .getLike()
                .add(userStorage.findUser(user));
        return filmStorage.getFilm(idFilm).getLike().size();
    }

    public int deleteLike(long user, int idFilm) {
        filmStorage.getFilm(idFilm)
                .getLike()
                .remove(userStorage.findUser(user));
        return filmStorage.getFilm(idFilm).getLike().size();
    }

    public List<Film> getTop10Films(int limit) {
        return filmStorage.getFilms()
                .stream()
                .sorted(Comparator.comparingInt(film -> film.getLike().size()))
                .limit(limit)
                .toList();
    }
}
