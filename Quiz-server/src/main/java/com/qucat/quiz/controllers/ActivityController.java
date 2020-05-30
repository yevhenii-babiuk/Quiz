package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.FriendActivity;
import com.qucat.quiz.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/activities")
public class ActivityController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public List<FriendActivity> getActivities(@PathVariable int id,
                                              @RequestParam(value = "pageNumber") int pageNumber) {
        return userService.getAllFriendsActivityPage(id, Optional.of(pageNumber), Optional.of(20)).toList();
    }

    @GetMapping("/filter/{id}")
    public List<FriendActivity> getFilteredActivities(@PathVariable int id,
                                                          @RequestParam(value = "categoryFilter") boolean[] categoryFilter,
                                                          @RequestParam(value = "pageNumber") int pageNumber) {
        return userService.getFilteredFriendsActivityPage(id, categoryFilter[0],
                categoryFilter[1], categoryFilter[2], categoryFilter[3], Optional.of(pageNumber), Optional.of(20)).toList();
    }

    /*@GetMapping("{id}/pages")
    public List<FriendActivity> getActivitiesPage(@PathVariable int id,
                                                  @RequestParam(value = "pageNumber") int pageNumber) {
        return userService.getAllFriendsActivityPage(id, Optional.of(pageNumber), Optional.of(20)).toList();
    }

    @GetMapping("/filter/pages")
    public List<FriendActivity> getFilteredActivitiesPage(@RequestParam(value = "id") int userId,
                                                          @RequestParam(value = "categoryFilter") boolean[] categoryFilter,
                                                          @RequestParam(value = "pageNumber") int pageNumber) {
        return userService.getFilteredFriendsActivityPage(userId, categoryFilter[0],
                categoryFilter[1], categoryFilter[2], categoryFilter[3], Optional.of(pageNumber), Optional.of(20)).toList();
    }*/
}
