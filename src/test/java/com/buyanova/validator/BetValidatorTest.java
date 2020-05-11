package com.buyanova.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class BetValidatorTest {

    private BetValidator betValidator;

    @Before
    public void setUp() {
        betValidator = new BetValidator();
    }

    @Test
    public void isSumPositive_SumGreaterThanZero_True() {
        BigDecimal sumGreaterThanZero = BigDecimal.valueOf(113.45);
        Assert.assertTrue(betValidator.isSumPositive(sumGreaterThanZero));
    }

    @Test
    public void isSumPositive_ZeroSum_False() {
        BigDecimal zeroSum = BigDecimal.ZERO;
        Assert.assertFalse(betValidator.isSumPositive(zeroSum));
    }

    @Test
    public void isSumPositive_NegativeSum_False() {
        BigDecimal negativeSum = BigDecimal.valueOf(-435.34);
        Assert.assertFalse(betValidator.isSumPositive(negativeSum));
    }
}