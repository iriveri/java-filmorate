package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
public class UserController {
    private final HashMap<Integer, User> userMap = new HashMap<>();
    @PostMapping("/user")
    public User addUser(@Valid @RequestBody User user) {
        if(user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        userMap.put(user.getId(),user);
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@Valid @RequestBody User user) {
        if(user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        userMap.put(user.getId(),user);
        return user;
    }
    @GetMapping("/users")
    public HashMap<Integer,User> getAllUsers() {
        return userMap;
    }
}
