package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.DuplicateException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
@Qualifier("InMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {

    protected final HashMap<Long, User> userMap;
    protected Long counter;

    public InMemoryUserStorage() {
        userMap = new HashMap<>();
        counter = 0L;
    }

    @Override
    public void addUser(User user) {
        if (user.getId() != null && userMap.containsKey(user.getId())) {
            log.warn("Попытка добавить уже существующего пользователя");
            throw new DuplicateException("Пользователь с таким ID уже существует");
        }
        if (user.getId() == null) {
            user.setId(generateUniqueId());
        }
        try {
            userMap.put(user.getId(), user);
            log.info("Пользователь успешно добавлен");
        } catch (Exception e) {
            log.error("Ошибка при добавлении пользователя", e);
            throw new RuntimeException("Ошибка при добавлении пользователя");
        }
    }

    @Override
    public void updateUser(User user) {
        if (!userMap.containsKey(user.getId())) {
            log.warn("Попытка обновить несуществующего пользователя");
            throw new NotFoundException("Пользователь с таким ID не найден");
        }

        try {
            userMap.put(user.getId(), user);
            log.info("Пользователь успешно обновлён");
        } catch (Exception e) {
            log.error("Ошибка при обновлении пользователя", e);
            throw new RuntimeException("Ошибка при обновлении пользователя");
        }
    }

    @Override
    public User getUser(long id) {
        User user = userMap.get(id);
        if (user == null) {
            throw new NotFoundException("Пользователя с таким ID " + id + " не существует");
        }
        return user;
    }

    @Override
    public void deleteUser(long id) {
        if (userMap.get(id) == null) {
            throw new NotFoundException("Пользователя с таким ID не существует");
        }
        userMap.remove(id);
    }

    public List<User> getAllUserList() {
        return new ArrayList<>(userMap.values());
    }

    private long generateUniqueId() {
        counter++;
        while (userMap.containsKey(counter)) {
            counter++;
        }
        return counter;
    }
}

