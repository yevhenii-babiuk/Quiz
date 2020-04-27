package com.qucat.quiz.repositories.dao;

import com.qucat.quiz.repositories.entities.Announcement;

import java.util.List;

public interface AnnouncementDao extends GenericDao<Announcement> {
    String TABLE_NAME = "announcement";

    List<Announcement> getByAuthorLogin(String login);

    List<Announcement> getAllInfo();

    Announcement getById(int id);
}
