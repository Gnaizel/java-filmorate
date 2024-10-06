package gnaizel.inc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "email")
public class User {
    private int id;
    @NotBlank(message = "Поле email не может быть пустым")
    private String email;
    @NotBlank(message = "Поле login не может быть пустым")
    private String login;
    private String name;
    @NonNull
    private LocalDate birthday;

    public User() { // Создал так как lombok не создаст его изза NonNull
    }
}
