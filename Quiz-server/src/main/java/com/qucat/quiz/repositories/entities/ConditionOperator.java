package com.qucat.quiz.repositories.entities;


public enum ConditionOperator {

    GREATER(">"),
    EQUALS("="),
    LESS("<");

    private String operator;

    ConditionOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

}
