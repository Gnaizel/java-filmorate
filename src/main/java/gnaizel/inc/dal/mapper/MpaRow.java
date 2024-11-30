package gnaizel.inc.dal.mapper;

import gnaizel.inc.enums.film.MPA;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class MpaRow implements RowMapper<MPA> {
    @Override
    public MPA mapRow(ResultSet sr, int i) throws SQLException {
        return MPA.builder()
                .id(sr.getInt("id"))
                .name(sr.getString("name")).build();
    }
}
