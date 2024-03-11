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

    public void addUser(User newUser){
        storage.addUser(newUser);
    }
    public void updateUser(User existingUser){
        storage.updateUser(existingUser);
    }
    public User getUser(int id)
    {
        return storage.getUser(id);
    }
    public void deleteUser(int id){
        storage.deleteUser(id);
    }
    public void addFriend(int id){

    }
    public List<User> commonFriends(){
        return List.of();
    }
}
