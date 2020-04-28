package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Announcement;
import com.qucat.quiz.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/announcements")
public class AnnouncementListController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public List<Announcement> getAnnouncements(@RequestParam(value = "count") int currentCount) {
              return announcementService.getPageForAllAnnouncements(Optional.of(currentCount), Optional.of(20)).toList();
    }
}

