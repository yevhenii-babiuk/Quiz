package com.qucat.quiz.repositories.entities;

public enum Lang {
    UA("Українська", "ua"),
    EN("English", "en");

    private final String name;
    private final String code;

    Lang(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}


