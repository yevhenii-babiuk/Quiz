package com.qucat.quiz.repositories.entities;

public enum Lang {
    UA("Українська", "ua", MessageInfo.registrationUA, MessageInfo.passwordRecoverUA),
    EN("English", "en", MessageInfo.registrationEN, MessageInfo.passwordRecoverEN);

    private final String name;
    private final String code;
    private final MessageInfo registration;
    private final MessageInfo passwordRecovery;

    Lang(String name, String code, MessageInfo registration, MessageInfo passwordRecovery) {
        this.name = name;
        this.code = code;
        this.registration = registration;
        this.passwordRecovery = passwordRecovery;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public MessageInfo getRegistration() {
        return registration;
    }

    public MessageInfo getPasswordRecovery() {
        return passwordRecovery;
    }
}


