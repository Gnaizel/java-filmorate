package ru.yandex.practicum.filmorate.mapper;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.enums.film.MPA;

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
