package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService(@Qualifier("DbUserStorage") UserStorage storage,
                       @Qualifier("DbFriendStorage") FriendStorage friendStorage) {
        this.userStorage = storage;
        this.friendStorage = friendStorage;
    }

    public void addUser(User newUser) {
        if (newUser.getName() == null) {
            newUser.setName(newUser.getLogin());
        }
        userStorage.addUser(newUser);
    }

    public void updateUser(User existingUser) {
        userStorage.updateUser(existingUser);
    }

    public User getUser(long userId) {
        return userStorage.getUser(userId);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUserList();
    }

    public void deleteUser(long userId) {
        userStorage.deleteUser(userId);
    }

    public void addFriend(long userId, long friendId) {
        friendStorage.addFriend(userId, friendId);
    }

    public void removeFriend(long userId, long friendId) {
        friendStorage.removeFriend(userId, friendId);
    }

    public List<User> getFriends(long userId) {
        List<Long> friendsId = friendStorage.getFriends(userId);

        return friendsId.stream()
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(long firstUserId, long secondUserId) {
        var firstFriendsList = friendStorage.getFriends(firstUserId);
        var secondFriendsList = friendStorage.getFriends(secondUserId);

        List<Long> common = new ArrayList<>(firstFriendsList);
        common.retainAll(secondFriendsList);

        return common.stream()
                .map(this::getUser)
                .collect(Collectors.toList());
    }
}
