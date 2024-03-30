package ru.yandex.practicum.filmorate.storage.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class DbFriendStorageTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO users (id,email, login, name, birthday) " + "VALUES (?, ?, ?, ?, ?)", 1L, "user1@example.com", "user1", "User 1", Date.valueOf(LocalDate.of(1990, 1, 1)));

        jdbcTemplate.update("INSERT INTO users (id,email, login, name, birthday) VALUES (?, ?, ?, ?, ?)", 2L, "user2@example.com", "user2", "User 2", Date.valueOf(LocalDate.of(1990, 1, 1)));

        jdbcTemplate.update("INSERT INTO users (id,email, login, name, birthday) VALUES (?, ?, ?, ?, ?)", 3L, "user3@example.com", "user3", "User 3", Date.valueOf(LocalDate.of(1990, 1, 1)));

    }

    @Test
    void addFriend() {
        DbFriendStorage friendStorage = new DbFriendStorage(jdbcTemplate);
        long userId = 1L;
        long friendId = 2L;

        friendStorage.addFriend(userId, friendId);

        List<Long> friends = friendStorage.getFriends(userId);
        assertThat(friends).contains(friendId);
    }

    @Test
    void removeFriend() {
        DbFriendStorage friendStorage = new DbFriendStorage(jdbcTemplate);
        long userId = 1L;
        long friendId = 2L;

        friendStorage.addFriend(userId, friendId);
        friendStorage.removeFriend(userId, friendId);

        List<Long> friends = friendStorage.getFriends(userId);
        assertThat(friends).doesNotContain(friendId);
    }

    @Test
    void getFriends() {
        DbFriendStorage friendStorage = new DbFriendStorage(jdbcTemplate);
        long userId = 1L;
        long friendId1 = 2L;
        long friendId2 = 3L;

        friendStorage.addFriend(userId, friendId1);
        friendStorage.addFriend(userId, friendId2);

        List<Long> friends = friendStorage.getFriends(userId);
        assertThat(friends).containsExactlyInAnyOrder(friendId1, friendId2);
    }
}
