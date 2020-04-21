package com.qucat.quiz.repositories.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {
    private int id;
    private String src;
}
