package gnaizel.inc.storage.mpa;

import gnaizel.inc.enums.film.MPA;
import java.util.Collection;

public interface MpaStorage {
    MPA findGMpaById(int id);

    Collection<MPA> findAllMpa();
}