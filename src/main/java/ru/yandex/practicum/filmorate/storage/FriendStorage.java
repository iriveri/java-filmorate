package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface FriendStorage {
    void addFriend(Long userId, Long friendId);


    void removeFriend(Long userId, Long friendId);

    List<Long> getFriends(Long userId);
}
