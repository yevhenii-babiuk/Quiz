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
        /*Announcement a = Announcement.builder()
                .title("Interesting title")
                .subtitle("Very interesting subtitle")
                .authorLogin("greatAuthor")
                .createdDate(new Date())
                .id(1)
                .build();*/

        return announcementService.getAllAnnouncements().toArray(Announcement[]::new); //announcementService.showPage(Optional.of(currentCount), Optional.of(20)).toList().toArray(Announcement[]::new);
    }
}

