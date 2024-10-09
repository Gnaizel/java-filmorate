package gnaizel.inc.storage.user;

import gnaizel.inc.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    List<User> findUsersById(Set<Long> ids);

    User findUser(long userId);

    User updateUser(User user);

    User createUser(User user);

    Set<User> getUsers();
}
