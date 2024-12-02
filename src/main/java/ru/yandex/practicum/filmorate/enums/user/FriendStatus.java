package ru.yandex.practicum.filmorate.enums.user;

import lombok.Getter;

@Getter
public enum FriendStatus {
    CONFIRMED(1), PENDING(2);

    private final int id;

    FriendStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
