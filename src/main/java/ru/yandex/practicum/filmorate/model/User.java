package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "email")
public class User {
    private long id;
    @NotBlank(message = "Поле email не может быть пустым")
    private String email;
    @NotBlank(message = "Поле login не может быть пустым")
    private String login;
    private String name;
    @NonNull
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public User() { // Создал так как lombok не создаст его изза NonNull
    }
}
