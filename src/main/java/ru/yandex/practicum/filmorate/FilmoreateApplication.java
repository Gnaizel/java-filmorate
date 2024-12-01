package ru.yandex.practicum.filmorate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmoreateApplication {
    private static final Logger log = LoggerFactory.getLogger(FilmoreateApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FilmoreateApplication.class, args);
        log.info("Программа запущена");
    }
}