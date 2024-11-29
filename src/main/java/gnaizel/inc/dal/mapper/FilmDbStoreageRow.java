package gnaizel.inc.dal.mapper;

import gnaizel.inc.enums.film.MPA;
import gnaizel.inc.model.Film;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class FilmDbStoreageRow implements RowMapper<Film> {
    JdbcTemplate jdbc;

    @Override
    public Film mapRow(ResultSet resultSet, int numRow) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .mpa(MPA.builder()
                        .id(resultSet.getInt("MPA.MPA_ID"))
                        .name(resultSet.getString("MPA.NAME"))
                        .build())
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .build();
    }
}
