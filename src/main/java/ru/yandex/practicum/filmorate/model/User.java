package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


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
    private LocalDate birthday;
    private Set<User> friends;

    public User(Long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        friends = new HashSet<>();
    }

    public void addFriend(User friend) {
        if (this.equals(friend)) {
            throw new IllegalArgumentException("Нельзя добавить самого себя в список друзей");
        }
        if (friends.contains(friend)) {
            throw new IllegalArgumentException("Пользователь уже есть в списке друзей");
        }
        friends.add(friend);
    }

    public void removeFriend(User friend) {
        if (!friends.contains(friend)) {
            throw new NotFoundExeption("Данный пользователь не в друзьях");
        }
        friends.remove(friend);
    }

    public List<User> getFriends() {
        return new ArrayList<>(friends);
    }
}
