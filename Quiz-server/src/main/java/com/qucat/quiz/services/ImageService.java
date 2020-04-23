package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.ImageDaoImpl;
import com.qucat.quiz.repositories.entities.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Service
public class ImageService {
    @Autowired
    private ImageDaoImpl imageDao;

    public Image getImageById(@RequestParam int id) {
        return imageDao.get(id);
    }
}
