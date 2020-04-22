package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.ImageDaoImpl;
import com.qucat.quiz.repositories.entities.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class ImageService {
    @Autowired
    private ImageDaoImpl imageDao;

    public Image getImageById(@RequestParam int id) {
        return imageDao.getById(id);
    }

    public int addImage(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return -1;
        }

        byte[] fileBytes;
        try {
            fileBytes = multipartFile.getBytes();
        } catch (IOException e) {
            log.error("Error while get bytes of file", e);
            return -1;
        }

        String encodedFile = Base64.getEncoder().encodeToString(fileBytes);

        int imageId = imageDao.getIdBySrc(encodedFile);
        if (imageId != -1) {
            return imageId;
        }

        return imageDao.save(
                Image.builder()
                        .src(encodedFile)
                        .build()
        );
    }
}
