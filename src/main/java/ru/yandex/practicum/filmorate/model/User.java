package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import ru.yandex.practicum.filmorate.validation.DateMin;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;


@Data
public class User {
    private Long id;
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\S+$")
    private String login;

    private String name;
    @Past
    @DateMin(value = "1900-01-01")
    private LocalDate birthday;
    private Map<Long, FriendStatus> friends;

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        friends = new HashMap<>();
    }

    public void addFriend(User friend) {
        if (this.equals(friend)) {
            throw new IllegalArgumentException("Нельзя добавить самого себя в список друзей");
        }
        friends.put(friend.getId(), FriendStatus.ACCEPTED);
    }


    public void removeFriend(User friend) {
        friends.remove(friend.getId());
    }

    public List<Long> getFriends() {
        return friends.entrySet().stream()
                .filter(entry -> entry.getValue() == FriendStatus.ACCEPTED)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
