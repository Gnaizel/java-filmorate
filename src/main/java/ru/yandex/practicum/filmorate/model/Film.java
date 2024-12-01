package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.enums.film.Genre;
import ru.yandex.practicum.filmorate.enums.film.MPA;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
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
    Set<Genre> genres;
    @NotNull
    MPA mpa;
    @NotNull
    LocalDate releaseDate;
    @NonNull
    int duration;
}
