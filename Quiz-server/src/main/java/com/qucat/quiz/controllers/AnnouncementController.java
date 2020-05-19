package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Announcement;
import com.qucat.quiz.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/announcement")
    public boolean createAnnouncement(@RequestBody Announcement announcement) {
        return announcementService.createAnnouncement(announcement);
    }

    @PutMapping("/announcement")
    public void updateAnnouncement(@RequestBody Announcement announcement) {
        announcementService.updateAnnouncement(announcement);
    }

    @GetMapping("/announcement/{id}")
    public Announcement getAnnouncement(@PathVariable int id) {
        return announcementService.getAnnouncementById(id);
    }

    @DeleteMapping("/announcement/{id}")
    public void deleteAnnouncement(@PathVariable int id) {
        announcementService.deleteAnnouncement(id);
    }

    @GetMapping("/announcements")
    public List<Announcement> getAnnouncements(@RequestParam(value = "pageNumber") int pageNumber,
                                               @RequestParam(value = "isPublished") boolean isPublished) {
        return announcementService.getPageForAllAnnouncements(isPublished, Optional.of(pageNumber), Optional.of(20)).toList();
    }
}
