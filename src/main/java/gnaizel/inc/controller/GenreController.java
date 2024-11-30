package gnaizel.inc.controller;

import gnaizel.inc.enums.film.Genre;
import gnaizel.inc.storage.genre.impl.GenreDB;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/genre")
public class GenreController {
    GenreDB genreDB;

    @GetMapping
    public List<Genre> getGenres() {
        return genreDB.findAll();
    }

    @GetMapping("/{id}")
    public Set<Genre> getGenreById(int id) {
        return genreDB.findGenreById(id);
    }
}
