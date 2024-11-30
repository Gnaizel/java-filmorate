package ru.yandex.practicum.filmorate.exception;

public class NotFoundUserId extends RuntimeException{
    public NotFoundUserId(String message) {
        super(message);
    }
}
