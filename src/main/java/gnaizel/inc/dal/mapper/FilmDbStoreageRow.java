package gnaizel.inc.dal.mapper;

import gnaizel.inc.enums.film.Genre;
import gnaizel.inc.model.Film;
import gnaizel.inc.storage.genre.impl.GenreDB;
import gnaizel.inc.storage.mpa.impl.MpaDbStorage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class FilmDbStoreageRow implements RowMapper<Film> {

    MpaDbStorage mpaStorage;
    @Autowired
    JdbcTemplate jdbc;
    @Override
    public Film mapRow(ResultSet resultSet, int numRow) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .mpa(mpaStorage.findGMpaById(resultSet.getInt("mpa_id")))
                .genres(findGenresByFilmId(resultSet.getInt("id")))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .build();
    }

    private Set<Genre> findGenresByFilmId(int filmId) {
        String sqlQuery = "select * from GENRE_FILM as fg " +
                "join genre as g on g.id = fg.genre_id " +
                "where fg.film_id = ? order by genre_id";
        return new HashSet<>(jdbc.query(sqlQuery, this::makeGenre, filmId));
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
    }
}
