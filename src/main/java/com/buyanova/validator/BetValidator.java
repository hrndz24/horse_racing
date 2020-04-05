package com.buyanova.validator;

import java.math.BigDecimal;

public class BetValidator {

    public boolean isSumPositive(BigDecimal sum) {
        return sum.doubleValue() > 0;
    }
}
