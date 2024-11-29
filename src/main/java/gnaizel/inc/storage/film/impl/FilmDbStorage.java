package gnaizel.inc.storage.film.impl;

import gnaizel.inc.exception.ValidationException;
import gnaizel.inc.model.Film;
import gnaizel.inc.storage.BaseDbStorage;
import gnaizel.inc.storage.film.FilmStorage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Repository()
@Qualifier("FilmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {
    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);
    private final String GET_ALL_FILMS_QUERY = "SELECT * FROM film;";
    private final String POST_FILM_QUERY = "INSERT INTO film(name, description, mpa_id, release_date, duration) " +
            "VALUES (?, ?, ?, ?, ?, ?) ";
    private final String UPDATE_FILM_QUERY = "UPDATE film SET name = ?," +
            " description = ?," +
            " genre_id = ?," +
            " mpa_id = ? ," +
            " release_date = ?," +
            " duration = ?" +
            " WHERE id = ?;";
    private final String GET_FILM_QUERY = "SELECT * FROM film WHERE id = ?;";

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> rowMapper) {
        super(jdbc, rowMapper);
    }

    @Override
    public Set<Film> getFilms() {
        return new HashSet<>(findAll(GET_ALL_FILMS_QUERY));
    }

    @Override
    public Film postFilm(Film film) {

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание должно быть меньше 200 символов");
        }

        if (film.getReleaseDate()
                .isBefore((
                        LocalDateTime.of(1895, 12, 28, 0, 0)
                                .toLocalDate()))) {
            throw new ValidationException("Дата релиза не может быть ранеьше 1895г 28 дек");
        }
        long filmid = insert(POST_FILM_QUERY,
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                String.valueOf(film.getDuration()));
        updateGenres(film);
        return getFilm((int) filmid);
    }

    @Override
    public Film updateFilm(Film film) {
        update(UPDATE_FILM_QUERY, film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                Date.from(Instant.from(film.getReleaseDate())),
                String.valueOf(film.getDuration()),
                film.getId());
        String sqlQueryForDeleteGenres = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbc.update(sqlQueryForDeleteGenres, film.getId());

        updateGenres(film);

        return getFilm(film.getId());
    }

    private void updateGenres(Film film) {
        if (film.getGenre() != null) {
            String sqlQueryForGenres = "INSERT INTO FILM_GENRE(FILM_ID, GENRE_ID) " +
                    "VALUES (?, ?)";
            jdbc.batchUpdate(
                    sqlQueryForGenres, film.getGenre(), film.getGenre().size(),
                    (ps, genre) -> {
                        ps.setInt(1, film.getId());
                        ps.setInt(2, genre.getId());
                    });
        } else film.setGenre(new HashSet<>());
    }

    @Override
    public Film getFilm(int filmId) {
       return findOne(GET_FILM_QUERY, filmId).get();
    }

}
