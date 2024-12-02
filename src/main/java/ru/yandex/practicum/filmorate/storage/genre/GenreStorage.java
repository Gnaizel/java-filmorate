package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.enums.film.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface GenreStorage {
    Genre findGenreById(int id);

    List<Genre> findAll();

    Genre mapRowToGenre(ResultSet resultSet, int i) throws SQLException;
}
