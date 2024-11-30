package gnaizel.inc.controller;

import gnaizel.inc.enums.film.MPA;
import gnaizel.inc.storage.mpa.impl.MpaDbStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/mpa")
public class MpaController {
    MpaDbStorage mpaDbStorage;

    @GetMapping
    public List<MPA> getMpaList() {
        return mpaDbStorage.findAllMpa();
    }

    @GetMapping("/{id}")
    public MPA getMpaById(int id) {
        return mpaDbStorage.findGMpaById(id);
    }
}
