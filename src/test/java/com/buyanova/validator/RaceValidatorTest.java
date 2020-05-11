package com.buyanova.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class RaceValidatorTest {

    private RaceValidator raceValidator;

    @Before
    public void setUp() {
        raceValidator = new RaceValidator();
    }

    @Test
    public void isPrizeMoneyPositive_MoneyGreaterThanZero_True() {
        BigDecimal moneyGreaterThanZero = BigDecimal.valueOf(10000.00);
        Assert.assertTrue(raceValidator.isPrizeMoneyPositive(moneyGreaterThanZero));
    }

    @Test
    public void isPrizeMoneyPositive_ZeroMoneySum_False() {
        BigDecimal zeroMoneySum = BigDecimal.ZERO;
        Assert.assertFalse(raceValidator.isPrizeMoneyPositive(zeroMoneySum));
    }

    @Test
    public void isPrizeMoneyPositive_NegativeMoneySum_False() {
        BigDecimal negativeMoneySum = BigDecimal.valueOf(-1245.87);
        Assert.assertFalse(raceValidator.isPrizeMoneyPositive(negativeMoneySum));
    }

    @Test
    public void isDateAfterNow_FutureDate_True() {
        Date currentDate = new Date();
        Date futureDate = new Date(currentDate.getTime() + 100000);
        Assert.assertTrue(raceValidator.isDateAfterNow(futureDate));
    }

    @Test
    public void isDateAfterNow_CurrentDate_False() {
        Date currentDate = new Date();
        Assert.assertFalse(raceValidator.isDateAfterNow(currentDate));
    }

    @Test
    public void isDateAfterNow_PastDate_False() {
        Date currentDate = new Date();
        Date pastDate = new Date(currentDate.getTime() - 100000);
        Assert.assertFalse(raceValidator.isDateAfterNow(pastDate));
    }

    @Test
    public void isDistancePositive_DistanceGreaterThanZero_True() {
        int distanceGreaterThanZero = 1000;
        Assert.assertTrue(raceValidator.isDistancePositive(distanceGreaterThanZero));
    }

    @Test
    public void isDistancePositive_ZeroDistance_False() {
        int zeroDistance = 0;
        Assert.assertFalse(raceValidator.isDistancePositive(zeroDistance));
    }

    @Test
    public void isDistancePositive_NegativeDistance_False() {
        int negativeDistance = -1200;
        Assert.assertFalse(raceValidator.isDistancePositive(negativeDistance));
    }
}