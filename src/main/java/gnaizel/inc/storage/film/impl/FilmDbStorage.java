package gnaizel.inc.storage.film.impl;

import gnaizel.inc.enums.film.Genre;
import gnaizel.inc.exception.ValidationException;
import gnaizel.inc.model.Film;
import gnaizel.inc.storage.BaseDbStorage;
import gnaizel.inc.storage.film.FilmStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Repository()
@Qualifier("FilmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {
    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);
    private final String GET_ALL_FILMS_QUERY = "SELECT * FROM film;";
    private final String POST_FILM_QUERY = "INSERT INTO film(name, description, mpa_id, release_date, duration) " +
            "VALUES (?, ?, ?, ?, ?) ";
    private final String UPDATE_FILM_QUERY = "UPDATE film SET name = ?," +
            " description = ?," +
            " mpa_id = ? ," +
            " release_date = ?," +
            " duration = ?" +
            " WHERE id = ?;";
    private final String GET_FILM_QUERY = "SELECT * FROM film WHERE id = ?;";

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> rowMapper) {
        super(jdbc, rowMapper);
    }

    @Override
    public void addLike(int filmId, long userId) {
        String sqlQuery = "INSERT INTO \"like\" (FILM_ID, USER_ID) " +
                "VALUES (?, ?)";

        jdbc.update(sqlQuery,
                filmId,
                userId);
    }

    @Override
    public void deleteLike(int film_id, long userId) {
        String sqlQuery = "DELETE FROM \"like\" WHERE USER_ID = ?";
        jdbc.update(sqlQuery, userId);
    }

    @Override
    public List<Film> getPopular(long count) {
        List<Film> films;

        String sqlQueryWithEmpty = "SELECT \n" +
                "    f.id, \n" +
                "    f.name, \n" +
                "    f.description, \n" +
                "    f.release_date, \n" +
                "    f.duration, \n" +
                "    m.id AS mpa_id,\n" +
                "    g.id AS genre_id,\n" +
                "    (SELECT COUNT(*) FROM \"like\" WHERE film_id = f.id) AS likes_count\n" +
                "FROM \n" +
                "    film f\n" +
                "LEFT JOIN \n" +
                "    mpa m ON f.mpa_id = m.id\n" +
                "LEFT JOIN \n" +
                "    genre_film fg ON f.id = fg.film_id\n" +
                "LEFT JOIN\n" +
                "    genre g ON fg.genre_id = g.id\n" +
                "ORDER BY \n" +
                "    likes_count DESC\n" +
                "LIMIT ?;\n";

        films = findAll(sqlQueryWithEmpty, count);

        return films;
    }

    @Override
    public List<Film> getFilms() {
        return findAll(GET_ALL_FILMS_QUERY);
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
        int filmid = (int) insert(POST_FILM_QUERY,
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                String.valueOf(film.getDuration()));
        film.setId(filmid);
        insertFilmGenre(film);
        return getFilm((int) filmid);
    }

    @Override
    public Film updateFilm(Film film) {
        update(UPDATE_FILM_QUERY, film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                Date.from(film.getReleaseDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                String.valueOf(film.getDuration()),
                film.getId());
        String sqlQueryForDeleteGenres = "DELETE FROM GENRE_FILM WHERE FILM_ID = ?";
        jdbc.update(sqlQueryForDeleteGenres, film.getId());
        insertFilmGenre(film);
        return getFilm(film.getId());
    }

    private void insertFilmGenre(Film film) {
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            jdbc.update("DELETE FROM GENRE_FILM WHERE FILM_ID = ?", film.getId());

            List<Genre> genres = new ArrayList<>(film.getGenres());
            jdbc.batchUpdate("INSERT INTO GENRE_FILM(FILM_ID, GENRE_ID) VALUES (?, ?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, film.getId());
                    ps.setInt(2, genres.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return genres.size();
                }
            });
        }
    }


    @Override
    public Film getFilm(int filmId) {
       return findOne(GET_FILM_QUERY, filmId).get();
    }

}
