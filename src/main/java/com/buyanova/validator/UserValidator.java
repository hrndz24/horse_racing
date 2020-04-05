package com.buyanova.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String LOGIN_REGEX = "^[a-z0-9_.@-]{3,16}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    private static final String NAME_REGEX = "^[a-zA-Z0-9_.-]{3,16}$";

    public boolean isValidEmail(String email) {
        return doesStringMatchRegex(email, EMAIL_REGEX);
    }

    public boolean isValidLogin(String login) {
        return doesStringMatchRegex(login, LOGIN_REGEX);
    }

    public boolean isValidPassword(String password) {
        return doesStringMatchRegex(password, PASSWORD_REGEX);
    }

    public boolean isValidName(String name) {
        return doesStringMatchRegex(name, NAME_REGEX);
    }

    private boolean doesStringMatchRegex(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
