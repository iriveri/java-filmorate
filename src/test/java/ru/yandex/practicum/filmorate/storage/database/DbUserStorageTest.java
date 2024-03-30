package ru.yandex.practicum.filmorate.storage.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class DbUserStorageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void addUser() {
        DbUserStorage userStorage = new DbUserStorage(jdbcTemplate);
        User user = new User(null, "test@example.com", "test123", "Test User", LocalDate.of(1990, 1, 1));

        userStorage.addUser(user);

        User retrievedUser = userStorage.getUser(user.getId());
        assertThat(retrievedUser).isEqualTo(user);
    }

    @Test
    void updateUser() {
        DbUserStorage userStorage = new DbUserStorage(jdbcTemplate);
        User user = new User(null, "test@example.com", "test123", "Test User", LocalDate.of(1990, 1, 1));

        userStorage.addUser(user);
        user.setLogin("updated@example.com");
        userStorage.updateUser(user);

        User retrievedUser = userStorage.getUser(user.getId());
        assertThat(retrievedUser).isEqualTo(user);
    }

    @Test
    void getUser() {
        DbUserStorage userStorage = new DbUserStorage(jdbcTemplate);
        User user = new User(null, "test@example.com", "test123", "Test User", LocalDate.of(1990, 1, 1));

        userStorage.addUser(user);

        User retrievedUser = userStorage.getUser(user.getId());
        assertThat(retrievedUser).isEqualTo(user);
    }

    @Test
    void deleteUser() {
        DbUserStorage userStorage = new DbUserStorage(jdbcTemplate);
        User user = new User(999L, "test@example.com", "test123", "Test User", LocalDate.of(1990, 1, 1));

        userStorage.addUser(user);
        userStorage.deleteUser(999L);

        Throwable thrown = org.assertj.core.api.Assertions.catchThrowable(() -> userStorage.getUser(1L));
        assertThat(thrown).isInstanceOf(NotFoundException.class);
    }

    @Test
    void toList() {
        DbUserStorage userStorage = new DbUserStorage(jdbcTemplate);
        User user1 = new User(null, "test1@example.com", "test123", "Test User 1", LocalDate.of(1990, 1, 1));
        User user2 = new User(null, "test2@example.com", "test456", "Test User 2", LocalDate.of(1991, 2, 2));

        userStorage.addUser(user1);
        userStorage.addUser(user2);

        List<User> userList = userStorage.toList();
        assertThat(userList).containsExactly(user1, user2);
    }
}
