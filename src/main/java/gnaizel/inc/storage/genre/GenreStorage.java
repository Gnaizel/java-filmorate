package gnaizel.inc.storage.genre;

import gnaizel.inc.enums.film.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface GenreStorage {
    Set<Genre> findGenreById(int id);

    Collection<Genre> findAll();

    Genre mapRowToGenre(ResultSet resultSet, int i) throws SQLException;
}
