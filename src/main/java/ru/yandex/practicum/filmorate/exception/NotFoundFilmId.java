package ru.yandex.practicum.filmorate.exception;

public class NotFoundFilmId extends RuntimeException {
    public NotFoundFilmId(String message) {
        super(message);
    }
}
