package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.enums.Role;
import com.qucat.quiz.repositories.entities.User;
import com.qucat.quiz.repositories.entities.enums.UserAccountStatus;
import com.qucat.quiz.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    @PutMapping("/photo")
    public void updateUserPhoto(@RequestBody User editedUser) {
        userService.updateUserImage(editedUser);
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(value = "pageNumber") int pageNumber,
                               @RequestParam(value = "allUsers") boolean allUsers,
                               @RequestParam(value = "filter", defaultValue = "") String filter) {
        return allUsers ? userService.searchUsersByLogin(filter, Optional.of(pageNumber), Optional.of(10)).toList() :
                userService.searchUsersByLogin(filter, Role.USER, Optional.of(pageNumber), Optional.of(10)).toList();
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/checkFriend/{friendId}")
    public boolean checkFriend(@PathVariable int id, @PathVariable int friendId) {
        return userService.checkUsersFriendship(id, friendId);
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

    @PutMapping("/image/{id}")
    public void saveImageForProfile(@PathVariable int id,
                                    @RequestBody MultipartFile file) {
        System.out.println("loaded " + id);
        //userService.updateUserImage(id, file);
    }

    @PutMapping("/{id}/status/change")
    public void changeUserStatus(@PathVariable int id, @RequestBody String newStatus) {
        userService.updateUserStatus(id, UserAccountStatus.valueOf(newStatus));
    }

    @PostMapping("/create")
    public boolean createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }
}
