package gnaizel.inc.service;

import gnaizel.inc.model.Film;
import gnaizel.inc.storage.film.FilmStorage;
import gnaizel.inc.storage.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public int addLike(long user, int idFilm) {
        Film film =  filmStorage.getFilm(idFilm);// Валидация есть в методе getFilm()
        film.getLike().add(userStorage.findUser(user));// валидация есть в методе findUser()
        return film.getLike().size();
    }

    public int deleteLike(long user, int idFilm) {
        Film film =  filmStorage.getFilm(idFilm); // Валидация есть в методе getFilm()
        film.getLike().remove(userStorage.findUser(user)); // валидация есть в методе findUser()
        return film.getLike().size();
    }

    public List<Film> getTop10Films(int limit) {
        return filmStorage.getFilms()
                .stream()
                .sorted(Comparator.comparingInt((Film film) -> film.getLike().size()).reversed())
                .limit(limit)
                .toList();
    }
}
