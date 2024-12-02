package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.SqlNotFaund;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User findUser(long id) {
        return userStorage.findUser(id);
    }

    public Set<User> getUsers() {
        return userStorage.getUsers();
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public Set<User> getCommonFriends(long userId, long friendId) {
        return userStorage.getCommonFriends(userId, friendId);
    }

    public User inviteFriend(long userId, long friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public User deleteFriend(long userId, long friendId) {
        if (userStorage.findUser(friendId) == null) {
            throw new SqlNotFaund("Другалёк потерялся");
        } else {
            userStorage.removeFriend(userId, friendId);
            return userStorage.findUser(friendId);
        }
    }

    public List<User> listFriends(long userId) {
        Set<Long> idsFriends = userStorage.findUser(userId).getFriends();
        return userStorage.findUsersById(idsFriends);
    }

}

