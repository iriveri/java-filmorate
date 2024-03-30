package ru.yandex.practicum.filmorate.storage.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.exception.DuplicateException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@Qualifier("DbUserStorage")
public class DbUserStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(User user) {
        Long id = user.getId();
        if (id != null && isUserExistsById(id)) {
            log.warn("Пользователь с таким ID {} уже существует", id);
            throw new DuplicateException("Пользователя с таким ID " + id + " уже существует");
        }

        String sqlQuery = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getLogin());
                stmt.setString(3, user.getName());
                stmt.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
                return stmt;
            }, keyHolder);
            long newId = keyHolder.getKey().longValue();
            user.setId(newId);
            log.info("Пользователь {} успешно добавлен", newId);
        } catch (DataAccessException e) {
            log.error("Ошибка при добавлении пользователя", e);
            throw new RuntimeException("Ошибка при добавлении пользователя");
        }
    }

    @Override
    public void updateUser(User user) {
        Long id = user.getId();
        if (!isUserExistsById(id)) {
            log.warn("Пользователь с таким ID {} не найден", id);
            throw new NotFoundException("Пользователь с таким ID " + id + " не найден");
        }
        String sqlQuery = "UPDATE users SET email=?, login=?, name=?, birthday=? WHERE id = ? ";
        try {
            jdbcTemplate.update(sqlQuery,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId());
            log.info("Пользователь {} успешно обновлён", id);
        } catch (DataAccessException e) {
            log.error("Ошибка при добавлении пользователя", e);
            throw new RuntimeException("Ошибка при добавлении пользователя");
        }
    }

    @Override
    public User getUser(long id) {
        String sqlQuery = "SELECT * FROM users WHERE id=? LIMIT 1";
        try {
            User user = jdbcTemplate.queryForObject(sqlQuery, new UserMapper(), id);
            log.info("Пользователь {} успешно извлечён", id);
            return user;
        } catch (DataAccessException e) {
            log.warn("Пользователь с таким ID {} не найден", id);
            throw new NotFoundException("Пользователь с таким ID " + id + " не найден");
        }
    }

    @Override
    public void deleteUser(long id) {
        try {
            jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
            log.info("Пользователь {} успешно удалён", id);
        } catch (DataAccessException e) {
            log.warn("Пользователь с таким ID {} не найден", id);
            throw new NotFoundException("Пользователь с таким ID " + id + " не найден");
        }
    }

    @Override
    public List<User> toList() {
        try {
            List<User> users = jdbcTemplate.query("SELECT * FROM users", new UserMapper());
            log.info("Список пользователей успешно извлечён");
            return users;
        } catch (DataAccessException e) {
            log.error("Ошибка при извлечении списка пользователей", e);
            throw new RuntimeException("Ошибка при извлечении списка пользователей");
        }
    }

    private boolean isUserExistsById(long id) {
        String sqlQuery = "SELECT COUNT(*) FROM users WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        return count > 0;
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    rs.getDate("birthday").toLocalDate());
        }
    }
}