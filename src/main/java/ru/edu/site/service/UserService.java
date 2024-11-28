package ru.edu.site.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.edu.site.entity.User;
import ru.edu.site.exception.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserByid(Long userId) {
        return users.stream().filter(user -> user.getId() == userId).findFirst();
    }

    public boolean saveUser(User user) {
        users.stream().filter(u -> u.equals(user)).findAny().ifPresent(u -> {throw new UserAlreadyExistsException(  "User already exists");});
        return users.add(user);
    }

    public void updateUser(User user) {
        Optional<User> userForUpdate = getUserByid(user.getId());
        userForUpdate.ifPresent(
                u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                }
        );
    }

    public void deleteUser(int userId) {
        users.removeIf(user -> user.getId() == userId);
    }

    @PostConstruct
    public void init() {
        users.add(new User(1L, "User1", 20));
        users.add(new User(2L, "User2", 33));
        users.add(new User(3L, "User3", 54));
        users.add(new User(4L, "User4", 18));
    }
}
