package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.AnnouncementDao;
import com.qucat.quiz.repositories.dao.mappers.AnnouncementExtractor;
import com.qucat.quiz.repositories.dao.mappers.AnnouncementMapper;
import com.qucat.quiz.repositories.entities.Announcement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@PropertySource("classpath:announcement.properties")
public class AnnouncementDaoImpl extends GenericDaoImpl<Announcement> implements AnnouncementDao {
    @Value("#{${sql.announcement}}")
    private Map<String, String> announcementQueries;

    protected AnnouncementDaoImpl() {
        super(new AnnouncementMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return announcementQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, Announcement announcement) throws SQLException {
        preparedStatement.setString(1, announcement.getAuthorLogin());
        preparedStatement.setBoolean(2, announcement.isPublished());
        preparedStatement.setString(3, announcement.getTitle());
        preparedStatement.setString(4, announcement.getSubtitle());
        preparedStatement.setString(5, announcement.getFullText());
        preparedStatement.setInt(6, announcement.getImageId());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return announcementQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Announcement announcement) {
        return new Object[]{announcement.getAuthorLogin(), announcement.isPublished(), announcement.getTitle(),
                announcement.getSubtitle(), announcement.getFullText(), announcement.getImageId(), announcement.getId()};
    }

    @Override
    public List<Announcement> getByAuthorLogin(String login) {
        return jdbcTemplate.query(
                announcementQueries.get("getAllInfo").replace(";", " WHERE author_login = ?;"),
                new Object[]{login}, new AnnouncementExtractor()
        );
    }

    @Override
    public List<Announcement> getAllInfo() {
        return jdbcTemplate.query(announcementQueries.get("getAllInfo"), new AnnouncementExtractor());
    }

    @Override
    public Announcement getById(int id) {
        List<Announcement> result = jdbcTemplate.query(
                announcementQueries.get("getAllInfo").replace(";", " WHERE a.id = ?;"),
                new Object[]{id}, new AnnouncementExtractor()
        );
        return result.size() == 0 ? null : result.get(0);
    }
}
