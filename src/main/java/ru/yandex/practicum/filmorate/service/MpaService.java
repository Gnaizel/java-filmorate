package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.enums.film.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<MPA> getMpaList() {
        return mpaStorage.findAllMpa();
    }

    public MPA getMpaById(int id) {
        return mpaStorage.findGMpaById(id);
    }
}
