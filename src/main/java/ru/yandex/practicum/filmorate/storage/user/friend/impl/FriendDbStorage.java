package ru.yandex.practicum.filmorate.storage.user.friend.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.friend.FriendStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class FriendDbStorage implements FriendStorage {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public long addFriend(long userId, long friendId) {
        String sql = "INSERT INTO friend(USER_ID, FRIEND_ID) VALUES (?, ?)";
        jdbc.update(sql, userId, friendId);
        return friendId;
    }

    @Override
    public Set<User> getFriendsById(long id) {
        String sql = "SELECT u.* FROM friend f JOIN USERS U on U.ID = f.FRIEND_ID WHERE f.USER_ID = ?";
        return new HashSet<>(jdbc.query(sql, (resultSet, rowNum) -> makeUser(resultSet), id));
    }

    @Override
    public long removeFriend(long id, long friendId) {
        String sql = "DELETE FROM friend WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbc.update(sql, id, friendId);
        return friendId;
    }

    private User makeUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setBirthday(resultSet.getDate("BIRTHDAY").toLocalDate());
        return user;
    }
}
