package gnaizel.inc.storage.user.impl;

import gnaizel.inc.exception.NotFoundUserId;
import gnaizel.inc.exception.ValidationException;
import gnaizel.inc.model.User;
import gnaizel.inc.storage.user.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);
    private final Map<Long, User> users = new HashMap<>();

    public Set<User> getUsers() {
        log.debug("Вернул юзеров");
        return new HashSet<>(users.values());
    }

    public List<User> findUsersById(Set<Long> ids) {
        return ids.stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public User findUser(long userId) {
        if (!users.containsKey(userId)) throw new NotFoundUserId("NOT_FOUND_USER_ID: " + userId);
        return users.get(userId);
    }

    public User updateUser(User user) { // может потомучо с ноут бука запускал но в постмане проходит только когда запускаю отдельно тесты с фильмами
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("пользователя с таким id не существует");
        }

        User existingUser = users.get(user.getId());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getLogin() != null) existingUser.setLogin(user.getLogin());
        if (user.getName() != null) existingUser.setName(user.getName());

        if (!Objects.equals(user.getLogin(), existingUser.getLogin())
                || !Objects.equals(user.getEmail(), existingUser.getEmail())) {
            if (users.containsValue(user)) throw new ValidationException("Login или Email заняты другим пользователем");
        }
        if (existingUser.getEmail().isBlank() || !existingUser.getEmail().contains("@")) {
            throw new ValidationException("Некоректная почта");
        }
        if (existingUser.getLogin().isBlank() || existingUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы или быьт пустым");
        }
        if (existingUser.getName().isBlank()) {
            existingUser.setName(existingUser.getLogin());
        }
        if (existingUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Некоректаня дата рождения");
        }

        log.debug("Юзера отредактировали \n{} \n{}", user, existingUser);
        users.replace(existingUser.getId(), existingUser);
        return existingUser;
    }

    public User createUser(User user) {

        if (user.getLogin() == null || user.getEmail() == null) {
            throw new ValidationException("Поля Login и Email обязательны");
        }
        if (users.containsValue(user)) {
            throw new ValidationException("Login или Email заняты другим пользователем");
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
        user.setId(nextIdCreate());

        users.put(user.getId(), user);
        log.info("Создан новый юзер");
        return user;
    }

    private long nextIdCreate() {
        long nextId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);

        return ++nextId;
    }
}
