package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Announcement;
import com.qucat.quiz.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/announcements")
public class AnnouncementListController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public Announcement[] getAnnouncements(@RequestParam(value = "count") int currentCount) {
        return announcementService.getAllAnnouncements().toArray(Announcement[]::new);
    }
}

