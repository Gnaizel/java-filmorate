package gnaizel.inc.dal.mapper;

import gnaizel.inc.enums.film.Genre;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class GenreDbRow implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("name");
        try {
            return Genre.builder().id(id).name(name).build();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
