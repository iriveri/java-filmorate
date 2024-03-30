package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier("InMemoryLikeStorage")
public class InMemoryLikeStorage implements LikeStorage {
    private final Map<Long, List<Long>> filmLikesMap;

    public InMemoryLikeStorage() {
        this.filmLikesMap = new HashMap<>();
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        filmLikesMap.computeIfAbsent(filmId, key -> new ArrayList<>()).add(userId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        List<Long> likes = filmLikesMap.get(filmId);
        if (likes != null) {
            likes.remove(userId);
        }
    }

    @Override
    public List<Long> getLikes(Long filmId) {
        return filmLikesMap.getOrDefault(filmId, new ArrayList<>());
    }

    @Override
    public Long getLikesAmount(Long filmId) {
        List<Long> likes = filmLikesMap.get(filmId);
        return (likes != null) ? (long) likes.size() : 0L;
    }

    @Override
    public List<Long> getPopularFilmsId(int size){
        return filmLikesMap.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().size()))
                .limit(size)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}