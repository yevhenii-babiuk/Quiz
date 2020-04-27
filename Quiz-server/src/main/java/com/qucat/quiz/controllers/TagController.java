package com.qucat.quiz.controllers;

import com.qucat.quiz.repositories.entities.Tag;
import com.qucat.quiz.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public Tag[] getTags() {
        return tagService.getAllTags().toArray(Tag[]::new);
    }

}
