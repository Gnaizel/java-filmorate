package ru.yandex.practicum.filmorate.storage.mpa.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.enums.film.MPA;
import ru.yandex.practicum.filmorate.exception.NotFaundMpaID;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MpaDbStorage extends BaseDbStorage implements MpaStorage {
    public final String findAllMpa = "SELECT * FROM MPA ";

    public MpaDbStorage(JdbcTemplate jdbc, RowMapper<MPA> rowMapper) {
        super(jdbc, rowMapper);
    }

    @Override
    public MPA findGMpaById(int id) {
        String findMpaById = "SELECT * FROM MPA WHERE id = ? ";
        Optional<MPA> optionalMpa = findOne(findMpaById, id);
        return optionalMpa.orElseThrow(() -> new NotFaundMpaID("MPA с id " + id + " не найден"));
    }

    @Override
    public List<MPA> findAllMpa() {
        return findAll(findAllMpa);
    }
}
