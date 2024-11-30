package gnaizel.inc.service;

import gnaizel.inc.model.Film;
import gnaizel.inc.storage.film.FilmStorage;
import gnaizel.inc.storage.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(@Qualifier("FilmDbStorage")FilmStorage filmStorage,
                       @Qualifier("UserDbStorage")UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int idFilm, long userId) {
        filmStorage.addLike(idFilm, userId);
    }

    public void deleteLike(long userId, int idFilm) {
        filmStorage.deleteLike(idFilm,  userId);
    }

    public List<Film> getTop10Films(int limit) {
        return filmStorage.getPopular(limit);
    }
}
