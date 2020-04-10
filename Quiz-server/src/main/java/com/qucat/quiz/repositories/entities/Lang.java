package com.qucat.quiz.repositories.entities;

public enum Lang {
    UK("Українська","ua"),
    EN("English", "en");

    private String name;
    private String code;

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