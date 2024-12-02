package ru.yandex.practicum.filmorate.storage.film.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.enums.film.Genre;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository()
@Qualifier("FilmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {

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
    public void deleteLike(int filmId, long userId) {
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
        String getFilmQuery = "SELECT * FROM film;";
        return findAll(getFilmQuery);
    }

    @Override
    public Film postFilm(Film film) {

        if (film.getMpa().getId() > 8) {
            throw new ValidationException("Не корректное поле рейтинга");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание должно быть меньше 200 символов");
        }

        if (film.getDuration() < 0) {
            throw new ValidationException("Не кореетное поле Продолжительности фильма: " + film.getDuration());
        }

        if (film.getReleaseDate()
                .isBefore((
                        LocalDateTime.of(1895, 12, 28, 0, 0)
                                .toLocalDate()))) {
            throw new ValidationException("Дата релиза не может быть ранеьше 1895г 28 дек");
        }
        String postFilmQuery = "INSERT INTO film(name, description, mpa_id, release_date, duration) " +
                "VALUES (?, ?, ?, ?, ?) ";
        int filmid = (int) insert(postFilmQuery,
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                String.valueOf(film.getDuration()));
        film.setId(filmid);
        insertFilmGenre(film);
        return getFilm(filmid);
    }

    @Override
    public Film updateFilm(Film film) {
        String updateFilmQuery = "UPDATE film SET name = ?," +
                " description = ?," +
                " mpa_id = ? ," +
                " release_date = ?," +
                " duration = ?" +
                " WHERE id = ?;";
        update(updateFilmQuery, film.getName(),
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

            Set<Integer> genreIds = film.getGenres().stream()
                    .map(Genre::getId)
                    .filter(id -> id <= 20)
                    .collect(Collectors.toSet());

            if (film.getGenres().stream().anyMatch(genre -> genre.getId() > 20)) {
                throw new ValidationException("ID жанра не может быть больше 20");
            }

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
        String getGetFilmQuery = "SELECT * FROM film WHERE id = ?;";
        return findOne(getGetFilmQuery, filmId).get();
    }

}
