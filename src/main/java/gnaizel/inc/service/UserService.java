package gnaizel.inc.service;

import gnaizel.inc.model.User;
import gnaizel.inc.storage.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage")UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void inviteFriend(long userId, long friendId) {
        userStorage.findUser(userId) // валидация есть в findUser() я проверял всё раббоает в постмане
                .getFriends().add(friendId);
        userStorage.findUser(friendId) // и тут
                .getFriends().add(userId);
    }

    public void deleteFriend(long userId, long friendId) {
        userStorage.findUser(userId)
                .getFriends().remove(friendId);
        userStorage.findUser(friendId)
                .getFriends().remove(userId);
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

