package ru.yandex.practicum.filmorate.exception;

public class NotFriends extends RuntimeException {
    public NotFriends(String massage) {
        super(massage);
    }
}
