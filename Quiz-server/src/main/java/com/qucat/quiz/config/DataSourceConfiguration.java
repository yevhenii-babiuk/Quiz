package com.qucat.quiz.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

/*    @Autowired
    Environment environment;*/

    @Primary
    @Bean
    @ConfigurationProperties("postgres.spring.datasource")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource postgresDataSource(@Qualifier("postgresDataSourceProperties") DataSourceProperties dataSourceProperties) {

        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
/*        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty("postgres.spring.datasource.driver-class-name"));
        dataSource.setJdbcUrl(environment.getProperty("postgres.spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("postgres.spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("postgres.spring.datasource.password"));

        return dataSource;*/
    }

    @Primary
    @Bean
    public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }


    @Bean
    @ConfigurationProperties("h2.spring.datasource")
    public DataSourceProperties h2DataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    public DataSource h2DataSource(@Qualifier("h2DataSourceProperties") DataSourceProperties dataSourceProperties) {

        return dataSourceProperties
                .initializeDataSourceBuilder()
                .build();
/*        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;*/
        //return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate h2JdbcTemplate(@Qualifier("h2DataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
