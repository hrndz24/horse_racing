package com.buyanova.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HorseValidatorTest {

    private HorseValidator horseValidator;

    @Before
    public void setUp() {
        horseValidator = new HorseValidator();
    }

    @Test
    public void isAgePositive_AgeGreaterThanZero_True() {
        int ageGreaterThanZero = 8;
        Assert.assertTrue(horseValidator.isAgePositive(ageGreaterThanZero));
    }

    @Test
    public void isAgePositive_ZeroAge_False() {
        int zeroAge = 0;
        Assert.assertFalse(horseValidator.isAgePositive(zeroAge));
    }

    @Test
    public void isAgePositive_NegativeAge_False() {
        int negativeAge = -6;
        Assert.assertFalse(horseValidator.isAgePositive(negativeAge));
    }

    @Test
    public void isRacesNumberPositive_GreaterThanZero_True() {
        int racesNumberGreaterThanZero = 26;
        Assert.assertTrue(horseValidator.isRacesNumberPositive(racesNumberGreaterThanZero));
    }

    @Test
    public void isRacesNumberPositive_ZeroRaces_True() {
        int zeroRaces = 0;
        Assert.assertTrue(horseValidator.isRacesNumberPositive(zeroRaces));
    }

    @Test
    public void isRacesNumberPositive_NegativeNumber_False() {
        int negativeRacesNumber = -56;
        Assert.assertFalse(horseValidator.isRacesNumberPositive(negativeRacesNumber));
    }
}