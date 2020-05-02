package com.qucat.quiz.services;

import com.qucat.quiz.repositories.entities.GameAccessor;
import com.qucat.quiz.repositories.entities.Image;
import com.qucat.quiz.repositories.entities.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class GameService {
    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @Value("${url}")
    private String URL;

    public GameAccessor generateGameAccessCredentials(Quiz quiz) {
        String accessCode = String.format("%040d",
                new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)
        ).substring(0, 10);

        Image qrCode = new Image(
                Integer.parseInt(accessCode),
                Base64.getEncoder().encodeToString(qrCodeGenerator.getQRCodeImage(
                        URL + "quiz/" + quiz.getId() + "/game/" + accessCode,
                        200, 200))
        );

        return new GameAccessor(accessCode, qrCode);
    }
}
