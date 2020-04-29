package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.ImageDaoImpl;
import com.qucat.quiz.repositories.entities.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class ImageService {
    @Autowired
    private ImageDaoImpl imageDao;

    @Value("${logo.src}")
    private String logo;

    public Image getImageById(@RequestParam int id) {
        return imageDao.get(id);
    }

    public int addImage(MultipartFile multipartFile) {
        String encodedFile;

        if (multipartFile == null) {
            encodedFile = logo;
        } else {
            byte[] fileBytes;
            try {
                fileBytes = multipartFile.getBytes();
            } catch (IOException e) {
                log.error("Error while get bytes of file", e);
                return -1;
            }

            encodedFile = Base64.getEncoder().encodeToString(fileBytes);
        }

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

    public Image getImage(int imageId) {
        return imageDao.get(imageId);
    }

    public List<Image> getAllImages() {
        return imageDao.getAll();
    }
}
