package ru.yandex.practicum.filmorate.storage.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.DuplicateException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Slf4j
@Repository
@Qualifier("DbFriendStorage")
public class DbFriendStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbFriendStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {

        if (userDoesntExistsById(userId) || userDoesntExistsById(friendId)) {
            log.warn("Один из пользоватетелей не найден");
            throw new NotFoundException("Один из пользоватетелей не найден");
        }

        if (isAlreadyFriends(userId, friendId)) {
            log.warn("Пользователи {} и {} уже друзья", userId, friendId);
            throw new DuplicateException("Пользователи " + userId + " и " + userId + " уже друзья");
        }

        String sql = "INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, userId, friendId);
            log.info("{} добавлен в друзья", friendId);
        } catch (DataAccessException e) {
            log.warn("Ошибка при добавлении в друзья", e);
            throw new NotFoundException("Ошибка при добавлении в друзья");
        }
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        if (userDoesntExistsById(userId) || userDoesntExistsById(friendId)) {
            log.warn("Один из пользоватетелей не найден");
            throw new NotFoundException("Один из пользоватетелей не найден");
        }

        String sql = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";

        try {
            jdbcTemplate.update(sql, userId, friendId);
            log.info("{} удалён из друзей", friendId);
        } catch (DataAccessException e) {
            log.warn("Ошибка при удалении из друзей", e);
            throw new NotFoundException("Ошибка при удалении из друзей");
        }
    }

    @Override
    public List<Long> getFriends(Long userId) {
        if (userDoesntExistsById(userId)) {
            log.warn("Один из пользоватетелей не найден");
            throw new NotFoundException("Один из пользоватетелей не найден");
        }
        String sql = "SELECT friend_id FROM user_friends WHERE user_id = ?";

        try {
            List<Long> friends = jdbcTemplate.queryForList(sql, Long.class, userId);
            log.info("Список друзей пользователя {} извлечён", userId);
            return friends;
        } catch (DataAccessException e) {
            log.warn("Ошибка при извлечении списка друзей", e);
            throw new NotFoundException("Ошибка при извлечении списка друзей");
        }
    }

    private boolean userDoesntExistsById(long id) {
        String sqlQuery = "SELECT COUNT(*) FROM users WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return count == 0;
    }

    private boolean isAlreadyFriends(Long userId, Long friendId) {
        String sqlQuery = "SELECT COUNT(*) FROM user_friends WHERE user_id = ? AND friend_id = ?";
        int count = jdbcTemplate.queryForObject(sqlQuery, Integer.class, userId, friendId);
        return count > 0;
    }
}
