package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Announcement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnouncementDao extends GenericDao<Announcement> {
    String TABLE_NAME = "announcement";

    List<Announcement> getByAuthorLogin(String login);

    List<Announcement> getAllInfo();

    Announcement getById(int id);

    Page<Announcement> getAllInfoForPage(Pageable pageable);

    Page<Announcement> getPageByAuthorId(int id, Pageable pageable);

    Page<Announcement> getPageByAuthorLogin(String login, Pageable pageable);
}
