package gnaizel.inc.storage.mpa;

import gnaizel.inc.enums.film.MPA;
import java.util.Collection;
import java.util.List;

public interface MpaStorage {
    MPA findGMpaById(int id);

    List<MPA> findAllMpa();
}