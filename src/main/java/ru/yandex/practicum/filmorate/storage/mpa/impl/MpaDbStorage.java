package ru.yandex.practicum.filmorate.storage.mpa.impl;

import ru.yandex.practicum.filmorate.enums.film.MPA;
import ru.yandex.practicum.filmorate.exception.NotFaundMpaID;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MpaDbStorage extends BaseDbStorage implements MpaStorage {
    private final String FIND_MPA_BY_ID = "SELECT * FROM MPA WHERE id = ? ";
    public final String FIND_ALL_MPA = "SELECT * FROM MPA ";

    public MpaDbStorage(JdbcTemplate jdbc, RowMapper<MPA> rowMapper) {
        super(jdbc, rowMapper);
    }

    @Override
    public MPA findGMpaById(int id) {
        Optional<MPA> optionalMpa = findOne(FIND_MPA_BY_ID, id);
        return optionalMpa.orElseThrow(() -> new NotFaundMpaID("MPA с id " + id + " не найден"));
    }

    @Override
    public List<MPA> findAllMpa() {
        return findAll(FIND_ALL_MPA);
    }
}
