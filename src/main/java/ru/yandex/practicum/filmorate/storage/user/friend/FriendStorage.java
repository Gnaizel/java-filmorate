package ru.yandex.practicum.filmorate.storage.user.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface FriendStorage {

    long addFriend(long userId, long friendId);

    long removeFriend(long userId, long friendId);

    Set<User> getFriendsById(long id);
}
