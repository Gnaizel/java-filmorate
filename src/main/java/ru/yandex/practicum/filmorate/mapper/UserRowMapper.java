package ru.yandex.practicum.filmorate.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        user.setFriends(getFriendsIdById(rs.getLong("id")));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        return user;
    }

    public Set<Long> getFriendsIdById(long id) {
        String sql = "SELECT u.id FROM friend f JOIN users u ON u.id = f.friend_id WHERE f.user_id = ?";
        return new HashSet<>(jdbc.queryForList(sql, Long.class, id));
    }
}
