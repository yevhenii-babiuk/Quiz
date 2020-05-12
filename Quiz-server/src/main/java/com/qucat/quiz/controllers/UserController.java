package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserDataById(id);
    }

    @PutMapping
    public void updateUser(@RequestBody User editedUser) {
        userService.updateUserProfile(editedUser);
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(value = "pageNumber") int pageNumber,
                               @RequestParam(value = "allUsers") boolean allUsers) {

        return userService.getAllUsersPage(Optional.of(pageNumber), Optional.of(10)).toList();//allUsers? userService.getAllUsersPage(Optional.of(pageNumber),Optional.of(10)): userService.;
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        return userService.getUserFriends(id);
    }

    @PostMapping("/{id}/addFriend")
    public boolean addFriend(@PathVariable int id,
                             @RequestBody int friendId) {
        return userService.addUserFriend(id, friendId);
    }

    @PostMapping("/{id}/removeFriend")
    public boolean removeFriend(@PathVariable int id,
                                @RequestBody int friendId) {
        return userService.deleteUserFriend(id, friendId);
    }
}
