package com.qucat.quiz.services;

import com.qucat.quiz.repositories.dao.AnnouncementDao;
import com.qucat.quiz.repositories.entities.Announcement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementDao announcementDao;

    public boolean createAnnouncement(Announcement announcement) {
        int announcementId = announcementDao.save(announcement);
        if (announcementId == -1) {
            log.info("createAnnouncement: Announcement wasn't saved");
            return false;
        }
        return true;
    }

    public void updateAnnouncement(Announcement announcement) {
        if (announcement == null) {
            log.info("updateAnnouncement: Announcement is null");
            return;
        }
        announcementDao.update(announcement);
    }

    public void deleteAnnouncement(Announcement announcement) {
        announcementDao.delete(announcement);
    }

    public Announcement getAnnouncementById(int announcementId) {
        return announcementDao.getById(announcementId);
    }

    public List<Announcement> getAnnouncementsByAuthorLogin(String authorLogin) {
        return announcementDao.getByAuthorLogin(authorLogin);
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementDao.getAllInfo();
    }
}
