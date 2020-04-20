package com.qucat.quiz.repositories.dao.implementation;

import com.qucat.quiz.repositories.dao.CategoryDao;
import com.qucat.quiz.repositories.dao.mappers.CategoryMapper;
import com.qucat.quiz.repositories.entities.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Repository
public class CategoryDaoImpl extends GenericDaoImpl<Category> implements CategoryDao {
    @Value("#{${sql.category}}")
    private Map<String, String> categoryQueries;

    protected CategoryDaoImpl() {
        super(new CategoryMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return categoryQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setString(1, category.getName());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return categoryQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Category category) {
        return new Object[]{category.getName(), category.getId()};
    }

}