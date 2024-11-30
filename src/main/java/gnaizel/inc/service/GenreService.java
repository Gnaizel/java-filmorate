package gnaizel.inc.service;

import gnaizel.inc.enums.film.Genre;
import gnaizel.inc.storage.genre.GenreStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
    @Autowired
    private final GenreStorage genreStorage;

    public List<Genre> getGenres() {
        return genreStorage.findAll();
    }

    public Set<Genre> getGenreById(int id) {
        return genreStorage.findGenreById(id);
    }
}