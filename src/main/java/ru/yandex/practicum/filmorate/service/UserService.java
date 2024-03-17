package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    UserStorage storage;

    public void addUser(User newUser) {
        if (newUser.getName() == null) {
            newUser.setName(newUser.getLogin());
        }
        storage.addUser(newUser);
    }

    public void updateUser(User existingUser) {
        storage.updateUser(existingUser);
    }

    public User getUser(long userId) {
        return storage.getUser(userId);
    }

    public void deleteUser(long userId) {
        var user = storage.getUser(userId);

        for(var friend : user.getFriends()){
            friend.removeFriend(user);
        }
        storage.deleteUser(userId);
    }

    public void addFriend(long userId, long friendId) {
        var user = storage.getUser(userId);
        var friend = storage.getUser(friendId);

        user.addFriend(friend);
        //@todo temporary solution
        friend.addFriend(user);
    }
    public void removeFriend(long userId, long friendId) {
        var user = storage.getUser(userId);
        var friend = storage.getUser(friendId);

        user.removeFriend(friend);
        friend.removeFriend(user);
    }
    public List<User> getFriends(long userId) {
        return storage.getUser(userId).getFriends();
    }
    public List<User> getCommonFriends(long firstUserId, long secondUserId) {
        var firstFriendsList = getFriends(firstUserId);
        var secondFriendsList = getFriends(secondUserId);

        List<User> common = new ArrayList<>(firstFriendsList);
        common.retainAll(secondFriendsList);

        return common;
    }
}
