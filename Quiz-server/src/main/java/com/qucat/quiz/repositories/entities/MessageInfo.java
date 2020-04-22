package com.qucat.quiz.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.Arrays.asList;

public enum MessageInfo {
    registration(
            new MessageInfoItem(Lang.UA, "mail/registration-ua.html", "Підтвердження реєстрації на QuCat"),
            new MessageInfoItem(Lang.EN, "mail/registration-en.html", "Confirm registration on QuCat")
    ),
    passwordRecover(
            new MessageInfoItem(Lang.UA, "mail/passwordRecovery-ua.html", "Скидання паролю на QuCat"),
            new MessageInfoItem(Lang.EN, "mail/passwordRecovery-en.html", "Confirm reset password on QuCat")
    );


    private List<MessageInfoItem> items;

    MessageInfo(MessageInfoItem... items) {
        this.items = asList(items);
    }

    public MessageInfoItem findByLang(Lang lang) {
        return items
                .stream()
                .filter(item -> item.getLang().equals(lang))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Failed to find template by lang: " + lang.name()));
    }

    @Data
    @AllArgsConstructor
    public static class MessageInfoItem {
        private Lang lang;
        private final String filename;
        private final String subject;
    }
}
