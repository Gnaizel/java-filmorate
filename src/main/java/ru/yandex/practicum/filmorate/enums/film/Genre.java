package ru.yandex.practicum.filmorate.enums.film;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Builder
public class Genre implements Serializable {
    @Getter
    private int id;
    private String name;
}