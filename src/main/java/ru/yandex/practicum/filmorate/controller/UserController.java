package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        service.addUser(user);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        if (user.getId() != null && userMap.containsKey(user.getId())) {
//            return ResponseEntity.badRequest().body(Map.of("error", "Фильм с таким ID уже существует"));
//        }
//
//        if (user.getId() == null) {
//            user.setId(generateUniqueId());
//        }
//
//        if (user.getName() == null) {
//            user.setName(user.getLogin());
//        }
//
//        try {
//            userMap.put(user.getId(), user);
//            log.info("Пользователь успешно добавлен");
//            return ResponseEntity.ok(user);
//        } catch (Exception e) {
//            log.error("Ошибка при добавлении пользователя", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user) {
        service.updateUser(user);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        if (!userMap.containsKey(user.getId())) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
//        }
//
//        if (user.getName() == null || user.getName().isBlank()) {
//            user.setName(user.getLogin());
//        }
//
//        try {
//            userMap.put(user.getId(), user);
//            log.info("Пользователь успешно обновлён");
//            return ResponseEntity.ok(user);
//        } catch (Exception e) {
//            log.error("Ошибка при обновлении пользователя", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
    }

    @GetMapping("/users")
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>();
    }

}
