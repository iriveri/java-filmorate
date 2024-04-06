package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface LikeStorage {
    void addLike(Long filmId, Long userId);

    void removeLike(Long filmId, Long userId);

    List<Long> getLikes(Long filmId);

    Long getLikesAmount(Long filmId);

    List<Long> getPopularFilmsId(int size);
}
