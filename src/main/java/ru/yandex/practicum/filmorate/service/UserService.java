package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    UserStorage storage;

    void addUser(User newUser){
        storage.addUser(newUser);
    }
    void updateUser(User existingUser){
        storage.updateUser(existingUser);
    }
    User getUser(int id)
    {
        return storage.getUser(id);
    }
    void deleteUser(int id){
        storage.deleteUser(id);
    }
    void addFriend(int id){

    }
    public List<User> commonFriends(){
        return List.of();
    }
}
