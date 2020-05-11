package com.buyanova.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OddsValidatorTest {

    private OddsValidator oddsValidator;

    @Before
    public void setUp() {
        oddsValidator = new OddsValidator();
    }

    @Test
    public void areOddsPositive_NumberGreaterThanZero_True() {
        int oddsNumberGreaterThanZero = 6;
        Assert.assertTrue(oddsValidator.areOddsPositive(oddsNumberGreaterThanZero));
    }

    @Test
    public void areOddsPositive_ZeroNumber_False() {
        int zeroOddsNumber = 0;
        Assert.assertFalse(oddsValidator.areOddsPositive(zeroOddsNumber));
    }

    @Test
    public void areOddsPositive_NegativeNumber_False() {
        int negativeOddsNumber = -3;
        Assert.assertFalse(oddsValidator.areOddsPositive(negativeOddsNumber));
    }
}