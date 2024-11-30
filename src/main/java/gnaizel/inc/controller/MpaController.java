package gnaizel.inc.controller;

import gnaizel.inc.enums.film.MPA;
import gnaizel.inc.storage.mpa.impl.MpaDbStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {
    MpaDbStorage mpaDbStorage;

    @GetMapping
    public List<MPA> getMpaList() {
        return mpaDbStorage.findAllMpa();
    }

    @GetMapping("/{id}")
    public MPA getMpaById(int id) {
        log.info(String.valueOf(id));
        return mpaDbStorage.findGMpaById(id);
    }
}
