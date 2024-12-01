package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.enums.film.MPA;

import java.util.List;

public interface MpaStorage {
    MPA findGMpaById(int id);

    List<MPA> findAllMpa();
}