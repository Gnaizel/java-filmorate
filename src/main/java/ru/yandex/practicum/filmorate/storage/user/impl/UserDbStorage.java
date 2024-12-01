package ru.yandex.practicum.filmorate.storage.user.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundUserId;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.BaseDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier("UserDbStorage")
@Primary
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {

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
        String findUserById = "SELECT * FROM users WHERE id = ? ";
        Optional<User> user = findOne(findUserById, userId);
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
        String updateUserQuery = "UPDATE users SET email = ?," +
                " login = ?," +
                " name = ?," +
                " birthday = ?" +
                " WHERE id = ?;";
        update(updateUserQuery,
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

        String createUserQuery = "INSERT INTO users(email, login, name, birthday) " +
                "VALUES (?, ?, ?, ?);";
        long userid = insert(createUserQuery, user.getEmail(),
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()));
        return findUser(userid);
    }

    @Override
    public Set<User> getUsers() {
        String findAllUserQuery = "SELECT * FROM users;";
        return findAll(findAllUserQuery).stream()
                .sorted(Comparator.comparing(User::getId)).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
