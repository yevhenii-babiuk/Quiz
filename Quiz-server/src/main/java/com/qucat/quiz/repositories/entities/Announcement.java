package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Announcement {
    private int id;
    private String authorLogin;
    private boolean isPublished;
    private Date createdDate;
    private String title;
    private String subtitle;
    private String fullText;
    private int imageId;
    private Image image;
}
