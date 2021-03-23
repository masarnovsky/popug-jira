package com.masarnovsky.popugjira.auth.model;

public enum Role {
    ADMIN("ADMIN"), EMPLOYEE("EMPLOYEE"), MANAGER("MANAGER"), ACCOUNTANT("ACCOUNTANT");

    private final String name;

    Role(String role) {
        this.name = role;
    }

    public String getName() {
        return this.name;
    }
}
