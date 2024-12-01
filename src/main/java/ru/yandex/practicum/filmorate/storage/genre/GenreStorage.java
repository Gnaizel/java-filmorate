package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.enums.film.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface GenreStorage {
    Set<Genre> findGenreById(int id);

    List<Genre> findAll();

    Genre mapRowToGenre(ResultSet resultSet, int i) throws SQLException;
}
