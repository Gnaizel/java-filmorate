package gnaizel.inc.dal.mapper;

import gnaizel.inc.model.Film;
import gnaizel.inc.storage.genre.impl.GenreDB;
import gnaizel.inc.storage.mpa.impl.MpaDbStorage;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class FilmDbStoreageRow implements RowMapper<Film> {

    MpaDbStorage mpaStorage;
    GenreDB genreDB;
    @Override
    public Film mapRow(ResultSet resultSet, int numRow) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .mpa(mpaStorage.findGMpaById(resultSet.getInt("mpa_id")))
                .genre(genreDB.findGenreById(resultSet.getInt("genre_id")))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .build();
    }
}
