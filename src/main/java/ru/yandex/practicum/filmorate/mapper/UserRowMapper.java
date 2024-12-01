package ru.yandex.practicum.filmorate.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.friend.FriendStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Autowired
    FriendStorage friendStorage;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        user.setFriends(
                friendStorage.getFriendsById(rs.getLong("id"))
                        .stream()
                        .map(User::getId)
                        .collect(Collectors.toSet()));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        return user;
    }
}
