package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.user.friend.FriendStorage;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public User inviteFriend(long userId, long friendId) {
        friendStorage.addFriend(userId, friendId);
        return userStorage.findUser(friendId);
    }

    public User deleteFriend(long userId, long friendId) {
        if (userStorage.findUser(friendId) == null) {
            throw new ValidationException("Другалёк потерялся");
        } else {
            friendStorage.removeFriend(userId, friendId);
            return userStorage.findUser(friendId);
        }
    }

    public List<User> listFriends(long userId) {
        Set<Long> idsFriends = userStorage.findUser(userId).getFriends();
        return userStorage.findUsersById(idsFriends);
    }

    public List<User> getMutualFriends(long userId, long friendId) {
        return listFriends(userId).stream()
                .filter(listFriends(friendId)::contains)
                .toList();
    }

}

