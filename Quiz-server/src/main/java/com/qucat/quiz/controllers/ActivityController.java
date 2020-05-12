package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Activity;
import com.qucat.quiz.repositories.entities.Image;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/activities")
public class ActivityController {

    @GetMapping("filter")
    public List<Activity> getAnnouncements(@RequestParam(value = "count") String[] categoryName) {
        System.out.println("category name");
        for (String s : categoryName) {
            System.out.println(s);
        }
        List<Activity> activities = new ArrayList<>();
        Date date = new Date();
        String src = "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";
        Image image = new Image(1, src);
        Activity firstActivity = new Activity(1, "login1", "achievement",
                null, null, false, null, date, 1, image);
        Activity secondActivity = new Activity(2, "login1", null,
                "quizName", "quizCategoryName", false, null, date, 1, image);
        Activity thirdActivity = new Activity(3, "login1", null,
                "quizName", "category name", true, null, date, 1, image);
        Activity fourthActivity = new Activity(4, "login1", null,
                null, null, false, "friendLogin", date, 1, image);
        activities.add(firstActivity);
        activities.add(secondActivity);
        activities.add(thirdActivity);
        activities.add(fourthActivity);
        activities.add(fourthActivity);
        activities.add(firstActivity);
        System.out.println("added filter");
        return activities;
    }


    /*@GetMapping("{id}")
    public List<Activity> getActivities(@PathVariable int id) {
        System.out.println("id " + id);
        List<Activity> activities = new ArrayList<>();
        Date date = new Date();
        String src = "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";
        Image image = new Image(1, src);
        Activity firstActivity = new Activity(1, "login1", "achievement",
                null, null, false, null, date, 1, image);
        Activity secondActivity = new Activity(2, "login1", null,
                "quizName", "quizCategoryName", false, null, date, 1, image);
        Activity thirdActivity = new Activity(3, "login1", null,
                "quizName", "category name", true, null, date, 1, image);
        Activity fourthActivity = new Activity(4, "login1", null,
                null, null, false, "friendLogin", date, 1, image);
        activities.add(firstActivity);
        activities.add(secondActivity);
        activities.add(thirdActivity);
        activities.add(fourthActivity);
        activities.add(fourthActivity);
        activities.add(firstActivity);
        activities.add(secondActivity);
        activities.add(thirdActivity);
        activities.add(fourthActivity);
        activities.add(fourthActivity);
        System.out.println("added");
        return activities;
    }*/
}
