package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.dao.ImageDao;
import com.qucat.quiz.repositories.entities.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/v1/announcement")
public class AnnouncementController {

    @Autowired
    private ImageDao imageDao;

    @PostMapping
    public boolean addAnnouncement(@RequestBody Announcement announcement) {
        return true;
    }

    @PutMapping
    public void updateAnnouncement(@RequestBody Announcement announcement) {
    }

    @GetMapping("{id}")
    public Announcement getAnnouncement(@PathVariable int id) {
        return Announcement.builder()
                .id(id)
                .authorLogin("authorLogin")
                .createdDate(new Date())
                .title("Etiam vel suscipit tellus, sit amet ullamcorper elit")
                .subtitle("Nam a hendrerit odio? Aenean felis augue, hendrerit vel interdum a, dapibus nec velit.")
                .isPublished(true)
                .image(imageDao.get(8))
                .fullText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla ut massa quis dui scelerisque tempor. Morbi non neque volutpat, malesuada quam id, elementum est. Aliquam erat volutpat. Sed non pharetra libero, pretium tempor erat. In hac habitasse platea dictumst. Morbi maximus ac magna et tristique. Donec nec suscipit turpis. Curabitur tortor turpis, lacinia sed congue id, pulvinar nec odio. Aliquam luctus dui nec eros porta, et varius quam eleifend. Sed eget tincidunt mi. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus pellentesque tortor at malesuada auctor.").build();
    }
}
