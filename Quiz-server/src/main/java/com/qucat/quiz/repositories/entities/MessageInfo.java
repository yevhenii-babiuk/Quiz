package com.qucat.quiz.repositories.entities;

public enum MessageInfo {
    registrationUA("registration-ua.html", "Підтвердження реєстрації на "),
    registrationEN("registration-en.html", "Confirm registration on "),
    passwordRecoverUA("passwordRecovery-ua.html", "Скидання паролю на "),
    passwordRecoverEN("passwordRecovery-en.html", "Confirm reset password on ");

    private final String filename;
    private final String subject;

    MessageInfo(String filename, String subject) {
        this.filename = filename;
        this.subject = subject;
    }

    public String getFilename() {
        return filename;
    }

    public String getSubject() {
        return subject;
    }
}
