package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final HashMap<Integer, User> userMap = new HashMap<>();
    @PostMapping("/user")
    public User addUser(@Valid @RequestBody User user) {
        if(user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        userMap.put(user.getId(),user);
        log.info("Добавлен новый пользователь");
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@Valid @RequestBody User user) {
        if(user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        userMap.put(user.getId(),user);
        log.info("Старый пользователь обновлен");
        return user;
    }
    @GetMapping("/users")
    public HashMap<Integer,User> getAllUsers() {
        return userMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.warn("Ошибка валидации: " + errorMessage);
        });
        return errors;
    }
}
