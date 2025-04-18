package ru.yandex.practicum.filmorate.exception;

public class NotFaundGenre extends RuntimeException {
    public NotFaundGenre(String massage) {
        super(massage);
    }
}
