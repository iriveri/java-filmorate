package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface FriendStorage {
    void addFriend(Long userId, Long friendId);

    void removeFriend(Long userId, Long friendId);

    List<Long> getFriends(Long userId);
}
