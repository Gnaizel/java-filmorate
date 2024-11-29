package gnaizel.inc.service;

import gnaizel.inc.enums.film.Genre;
import gnaizel.inc.exception.NotFaundGenre;
import gnaizel.inc.storage.genre.GenreStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public Collection<Genre> getAllGenres() {
        return genreStorage.findAll();
    }

    public Genre getGenreById(int id) {
        Genre genre = genreStorage.findGenreById(id)
                .orElseThrow(() -> new NotFaundGenre("Жанр не найден!"));
        return genre;
    }
}