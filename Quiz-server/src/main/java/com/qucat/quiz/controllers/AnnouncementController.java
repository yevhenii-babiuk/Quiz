package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Announcement;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/v1/announcement")
public class AnnouncementController {

    @PostMapping
    public boolean addAnnouncement(@RequestBody Announcement quiz) {
        return true;
    }

    @PutMapping
    public void updateAnnouncement(@RequestBody Announcement quiz) {
    }

    @GetMapping("{id}")
    public Announcement getAnnouncement(@PathVariable int id) {
        return Announcement.builder().id(id).authorLogin("login").createdDate(new Date()).title("title").subtitle("subtitle").isPublished(true).fullText("full text").build();
    }
}
