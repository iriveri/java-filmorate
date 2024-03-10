package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashMap;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    protected final HashMap<Integer, User> userMap;
    protected Integer counter;

    public InMemoryUserStorage() {
        userMap = new HashMap<>();
        counter = 0;
    }

    @Override
    public void addUser(User user) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("Передано пустое поле");
        }
        if (user.getId() != null && userMap.containsKey(user.getId())) {
            throw new RuntimeException("Фильм с таким ID уже существует");
        }
        if (user.getId() == null) {
            user.setId(generateUniqueId());
        }

        try {
            userMap.put(user.getId(), user);
            log.info("Фильм успешно добавлен");
        } catch (Exception e) {
            log.error("Ошибка при добавлении пользователя", e);
            throw new RuntimeException("Фильм с таким ID уже существует");
        }
    }
    @Override
    public void updateUser(User user) {
        if (!userMap.containsKey(user.getId())) {
            throw new RuntimeException("Фильм с таким ID уже существует");
        }

        try {
            userMap.put(user.getId(), user);
            log.info("Пользователь успешно обновлён");
        } catch (Exception e) {
            log.error("Ошибка при обновлении пользователя", e);
            throw new RuntimeException("Фильм с таким ID уже существует");
        }
    }

    @Override
    public User getUser(int id) {
        User user = userMap.get(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public void deleteUser(int id) {
        userMap.remove(id);
    }

    private int generateUniqueId() {
        counter++;
        while (userMap.containsKey(counter)) {
            counter++;
        }
        return counter;
    }
}

