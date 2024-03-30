package ru.yandex.practicum.filmorate.storage.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Repository
@Qualifier("DbFriendStorage")
public class DbFriendStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbFriendStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        String sql = "INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        String sql = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<Long> getFriends(Long userId) {
        String sql = "SELECT friend_id FROM user_friends WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, userId);
    }
}
