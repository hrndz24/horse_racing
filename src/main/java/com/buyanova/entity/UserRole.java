package com.buyanova.entity;

/**
 * Types of people who can visit the website
 * differentiated by provided services.
 *
 * @author Natalie
 * */
public enum UserRole {

    ADMINISTRATOR(1),
    CLIENT(2),
    BOOKMAKER(3),
    GUEST(4);

    private int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserRole getById(int id) {
        for (UserRole role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        return GUEST;
    }
}
