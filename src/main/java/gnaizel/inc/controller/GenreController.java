package gnaizel.inc.controller;

import gnaizel.inc.enums.film.Genre;
import gnaizel.inc.service.GenreService;
import gnaizel.inc.storage.genre.impl.GenreDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    GenreService genreService;

    @GetMapping
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping("/{id}")
    public Set<Genre> getGenreById(@PathVariable int id) {
        return genreService.getGenreById(id);
    }
}
