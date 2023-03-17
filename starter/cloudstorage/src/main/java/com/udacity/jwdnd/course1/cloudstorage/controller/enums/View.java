package com.udacity.jwdnd.course1.cloudstorage.controller.enums;

public enum View {
    RESULT("operation_result"),
    HOME("home"),
    LOGIN("login"),
    SIGNUP("signup"),

    UPLOAD("upload");

    private final String text;

    View(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

