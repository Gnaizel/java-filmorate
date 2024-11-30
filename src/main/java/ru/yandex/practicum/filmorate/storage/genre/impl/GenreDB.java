package ru.yandex.practicum.filmorate.storage.genre.impl;

import ru.yandex.practicum.filmorate.enums.film.Genre;
import ru.yandex.practicum.filmorate.exception.NotFaundGenre;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Repository
public class GenreDB extends BaseDbStorage<Genre> implements GenreStorage {
    private final String  FIND_GENRE_BY_ID = "SELECT * FROM genre WHERE id = ?";

    public GenreDB(JdbcTemplate jdbc, RowMapper<Genre> rowMapper) {
        super(jdbc, rowMapper);
    }

    @Override
    public Set<Genre> findGenreById(int id) {
            Genre genre = findOne(FIND_GENRE_BY_ID, id)
                    .orElseThrow(() -> new NotFaundGenre("Жанр с id " + id + " не найден"));
            return Collections.singleton(genre);
    }

    @Override
    public List<Genre> findAll() {
        String sqlQuery = "SELECT ID, NAME FROM GENRE";
        return findAll(sqlQuery);
    }

    @Override
    public Genre mapRowToGenre(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("name");
        try {
            return Genre.builder().id(id).name(name).build();
        } catch (IndexOutOfBoundsException e) {
            log.warn("Invalid Genre ID: {}", id);
            return null;
        }
    }

}
