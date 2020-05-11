package com.buyanova.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserValidatorTest {

    private UserValidator userValidator;

    @Before
    public void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    public void isValidEmail_CorrectEmail_True() {
        String correctEmail = "natalie@mail.ru";
        Assert.assertTrue(userValidator.isValidEmail(correctEmail));
    }

    @Test
    public void isValidEmail_EmailWithoutAtSign_False() {
        String emailWithoutAtSign = "natalie.mail.ru";
        Assert.assertFalse(userValidator.isValidEmail(emailWithoutAtSign));
    }

    @Test
    public void isValidEmail_EmailWithSpecialCharacters_False() {
        String emailWithoutAtSign = "nata%`lie@mail.ru";
        Assert.assertFalse(userValidator.isValidEmail(emailWithoutAtSign));
    }

    @Test
    public void isValidLogin_LoginWithSpecialCharacters_False() {
        String loginWithSpecialCharacters = "natal%ie34";
        Assert.assertFalse(userValidator.isValidLogin(loginWithSpecialCharacters));
    }

    @Test
    public void isValidLogin_CorrectLogin_True() {
        String correctLogin = "natalie24";
        Assert.assertTrue(userValidator.isValidLogin(correctLogin));
    }

    @Test
    public void isValidPassword_CorrectPassword_True() {
        String correctPassword = "Veryvalidpass2";
        Assert.assertTrue(userValidator.isValidPassword(correctPassword));
    }

    @Test
    public void isValidPassword_PasswordWithoutDigit_False() {
        String passwordWithoutDigit = "Notvalidpass";
        Assert.assertFalse(userValidator.isValidPassword(passwordWithoutDigit));
    }

    @Test
    public void isValidPassword_PasswordWithoutUpperCaseLetter_False() {
        String passwordWithoutUpperCase = "notvalidpass";
        Assert.assertFalse(userValidator.isValidPassword(passwordWithoutUpperCase));
    }

    @Test
    public void isValidPassword_PasswordLessThanEightCharacters_False() {
        String passwordLessThanEightCharacters = "seventy";
        Assert.assertFalse(userValidator.isValidPassword(passwordLessThanEightCharacters));
    }

    @Test
    public void isValidName_CorrectName_True() {
        String correctName = "natalie";
        Assert.assertTrue(userValidator.isValidName(correctName));
    }

    @Test
    public void isValidName_NameWithSpecialCharacters_False() {
        String nameWithSpecialCharacters = "natal@ie";
        Assert.assertFalse(userValidator.isValidName(nameWithSpecialCharacters));
    }
}