package gnaizel.inc.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.LocalDate;


@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "name")
public class Film {
    int id;
    @NonNull()
    String name;
    @NonNull
    String description;
    @NonNull
    LocalDate releaseDate;
    @NonNull
    Duration duration;
}
