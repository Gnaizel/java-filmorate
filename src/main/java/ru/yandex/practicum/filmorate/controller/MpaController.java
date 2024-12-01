package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.enums.film.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {
    MpaService mpaService;

    public MpaController(MpaService m) {
        this.mpaService = m;
    }

    @GetMapping
    public List<MPA> getMpaList() {
        return mpaService.getMpaList();
    }

    @GetMapping("/{id}")
    public MPA getMpaById(@PathVariable int id) {
        return mpaService.getMpaById(id);
    }
}
