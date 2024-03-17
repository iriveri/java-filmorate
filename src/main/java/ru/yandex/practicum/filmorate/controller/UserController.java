package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        service.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user) {
        service.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        var users = service.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        User user = service.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/users/{userId}/friends/{friendId}")
    public ResponseEntity<Object> addFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        service.addFriend(userId, friendId);
        User user = service.getUser(friendId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public ResponseEntity<Object> removeFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        service.removeFriend(userId, friendId);
        User user = service.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users/{id}/friends")
    public ResponseEntity<Object> getFriends(@PathVariable("id") Long id) {
        List<User> friends = service.getFriends(id);
        return ResponseEntity.status(HttpStatus.OK).body(friends);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public ResponseEntity<Object> getCommonFriends(@PathVariable("id") Long id, @PathVariable("otherId") Long otherId) {
        List<User> commonFriends = service.getCommonFriends(id, otherId);
        return ResponseEntity.status(HttpStatus.OK).body(commonFriends);
    }
}