package ru.yandex.practicum.filmorate.exception;

public class SqlNotFaund extends RuntimeException{
    public SqlNotFaund(String massage) {
        super(massage);
    }
}
