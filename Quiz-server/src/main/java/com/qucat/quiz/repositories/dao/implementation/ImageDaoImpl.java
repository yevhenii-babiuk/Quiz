package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.ImageDao;
import com.qucat.quiz.repositories.dao.mappers.ImageMapper;
import com.qucat.quiz.repositories.entities.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Repository
public class ImageDaoImpl extends GenericDaoImpl<Image> implements ImageDao {
    @Value("#{${sql.image}}")
    private Map<String, String> imageQueries;

    protected ImageDaoImpl() {
        super(new ImageMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return imageQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, Image image) throws SQLException {
        preparedStatement.setString(1, image.getSrc());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return imageQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Image image) {
        return new Object[]{image.getSrc(), image.getId()};
    }

    @Override
    public int getIdBySrc(String src) {
        Number id;
        try {
            id = jdbcTemplate.queryForObject(imageQueries.get("getIdBySrc"),
                    new Object[]{src}, Integer.class);
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            return -1;
        }
        return id != null ? id.intValue() : -1;
    }
}
