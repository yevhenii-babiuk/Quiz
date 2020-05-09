package com.qucat.quiz.repositories.entities;


public enum ConditionOperator {

    greater(">"),
    equals("="),
    less("<");

    private String operator;

    ConditionOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

}
