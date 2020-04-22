package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.implementation.TagDaoImpl;
import com.qucat.quiz.repositories.entities.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Service
public class TagService {
    @Autowired
    private TagDaoImpl tagDao;

    public List<Tag> getTagsByQuizId(@RequestParam int quizId) {
        return tagDao.getByQuizId(quizId);
    }

    public int addTag(Tag tag) {
        int tagId = -1;
        String tagName = tag.getName();
        if (tagName != null && !tagName.isEmpty()) {
            tagId = tagDao.getIdByName(tagName);
            if (tagId == -1) {
                return tagDao.save(tag);
            }
        }
        return tagId;
    }
}
