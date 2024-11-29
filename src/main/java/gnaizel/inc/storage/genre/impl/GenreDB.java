package gnaizel.inc.storage.genre.impl;

import gnaizel.inc.enums.film.Genre;
import gnaizel.inc.storage.genre.GenreStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GenreDB implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Genre> findGenreById(int id) {
        String sqlQuery = "SELECT ID, NAME FROM GENRE WHERE ID = ?";

        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (genreRows.next()) {
            Genre genre = Genre.builder()
                    .id(genreRows.getInt("ID"))
                    .name(genreRows.getString("NAME"))
                    .build();
            log.info("Найден жанр {} с названием {} ", genreRows.getInt("ID"),
                    genreRows.getString("NAME"));
            return Optional.of(genre);
        } else {
            log.info("Жанр с id {} не найден", id);
            return Optional.empty();
        }
    }

    @Override
    public Collection<Genre> findAll() {
        String sqlQuery = "SELECT ID, NAME FROM GENRE";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public Genre mapRowToGenre(ResultSet resultSet, int i) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("ID"))
                .name(resultSet.getString("NAME"))
                .build();
    }
}
