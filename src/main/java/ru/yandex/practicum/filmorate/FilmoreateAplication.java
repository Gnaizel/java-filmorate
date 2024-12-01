package ru.yandex.practicum.filmorate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmoreateAplication {
    private static final Logger log = LoggerFactory.getLogger(FilmoreateAplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FilmoreateAplication.class, args);
        log.info("Программа запущена");
    }
}