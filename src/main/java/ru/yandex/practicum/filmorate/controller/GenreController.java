package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.enums.film.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
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
