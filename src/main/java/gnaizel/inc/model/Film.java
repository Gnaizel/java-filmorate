package gnaizel.inc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "name")
public class Film {
    int id;
    @NotBlank(message = "Поле названия не может быть пустым")
    String name;
    @NotBlank(message = "Поле описания не может быть пустым")
    String description;
    @NotNull
    LocalDate releaseDate;
    @NonNull
    Duration duration;
    Set<User> like = new HashSet<>();
}
