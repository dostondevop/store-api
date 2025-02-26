package com.doston.model.enumaration;

public enum UserRole {
    ADMIN("Admin"),
    USER("User");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
