package com.qucat.quiz.config;

import com.qucat.quiz.services.QucatSessionAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Session;
import java.io.IOException;
import java.util.Properties;


@Slf4j
@Configuration
public class MailConfiguration {

    @Bean
    public Session emailSession(QucatSessionAuthenticator sessionAuthenticator) {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("mail/application-mail-config.properties"));
        } catch (IOException e) {
            log.error("load properties",e);
        }
        return Session.getInstance(properties, sessionAuthenticator);
    }
}
