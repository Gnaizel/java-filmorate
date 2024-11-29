package gnaizel.inc.storage.user.impl;

import gnaizel.inc.exception.NotFoundUserId;
import gnaizel.inc.exception.ValidationException;
import gnaizel.inc.model.User;
import gnaizel.inc.storage.BaseDbStorage;
import gnaizel.inc.storage.user.UserStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
@Qualifier("UserDbStorage")
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {
    private final String FIND_USER_QUERY = "SELECT * FROM users WHERE id = ?;";
    private final String UPDATE_USER_QUERY = "UPDATE users SET email = ?," +
            " login = ?," +
            " name = ?," +
            " birthday = ?" +
            " WHERE id = ?;";
    private final String CREATE_USER_QUERY = "INSERT INTO users(email, login, name, birthday) " +
            "VALUES (?, ?, ?, ?);";
    private final String  FIND_ALL_USERS_QUERY = "SELECT * FROM users;";

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> rowMapper) {
        super(jdbc, rowMapper);
    }

    @Override
    public List<User> findUsersById(Set<Long> ids) {
        List<User> userList = new ArrayList<>();
        for (long id : ids) {
            userList.add(findUser(id));
        }
        return userList;
    }

    @Override
    public User findUser(long userId) {
        Optional<User> user = findOne(FIND_USER_QUERY, userId);
        if (user.isEmpty()) {
            throw new NotFoundUserId("USER_ID_NOT_FOUND");
        }
        return user.get();
    }

    @Override
    public User updateUser(User user) {
        
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Некоректная почта");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы или быьт пустым");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Некоректаня дата рождения");
        }
        update(UPDATE_USER_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId());
        return findUser(user.getId());
    }

    @Override
    public User createUser(User user) {

        if (user.getLogin() == null || user.getEmail() == null) {
            throw new ValidationException("Поля Login и Email обязательны");
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Некоректная почта");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы или быьт пустым");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Некоректаня дата рождения");
        }

        long userid = insert(CREATE_USER_QUERY, user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()));
        return findUser(userid);
    }

    @Override
    public Set<User> getUsers() {
        return new HashSet<User>(findAll(FIND_ALL_USERS_QUERY));
    }
}
