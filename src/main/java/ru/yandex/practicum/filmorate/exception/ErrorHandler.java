package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Map<String,String> validationError(ValidationException e) {
        return Map.of("Ошибка валидации ", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> noFoundFilm(NotFoundFilmId e) {
        return Map.of("Ошибка валидации ID ", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> noFoundUser(NotFoundUserId e) {
        return Map.of("Ошибка валидации ID ", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> notFoundGenre(NotFaundGenre e) {
        return Map.of("Ошибка валидации ID", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Map<String, String> notFoundMpa(NotFaundMpaID e) {
        return Map.of("Ошибка валидации ID", e.getMessage());
    }
}
