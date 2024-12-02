package ru.yandex.practicum.filmorate.exception;

public class NotFaundMpaID extends RuntimeException {
    public NotFaundMpaID(String massage) {
        super(massage);
    }
}
