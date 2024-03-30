package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Qualifier("InMemoryFriendStorage")
public class InMemoryFriendStorage implements FriendStorage {
    private final Map<Long, List<Long>> userFriendsMap;

    public InMemoryFriendStorage() {
        this.userFriendsMap = new HashMap<>();
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        userFriendsMap.computeIfAbsent(userId, key -> new ArrayList<>()).add(friendId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        List<Long> friends = userFriendsMap.get(userId);
        if (friends != null) {
            friends.remove(friendId);
        }
    }

    @Override
    public List<Long> getFriends(Long userId) {
        return userFriendsMap.getOrDefault(userId, new ArrayList<>());
    }

}
