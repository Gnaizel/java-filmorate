package gnaizel.inc.model;

import gnaizel.inc.enums.film.Genre;
import gnaizel.inc.enums.film.MPA;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "name")
public class Film {
    int id;
    @NotBlank(message = "Поле названия не может быть пустым ")
    String name;
    @NotBlank(message = "Поле описания не может быть пустым ")
    String description;
    Set<Genre> genre;
    MPA mpa;
    @NotNull
    LocalDate releaseDate;
    @NonNull
    int duration;
    Set<User> like;
}
