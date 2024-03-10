package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    void addUser(User newUser);
    void updateUser(User existingUser);
    User getUser(int id);
    void deleteUser(int id);
}
