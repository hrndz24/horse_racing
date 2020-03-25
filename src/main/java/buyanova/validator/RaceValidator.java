package buyanova.validator;

import java.math.BigDecimal;
import java.util.Date;

public class RaceValidator {

    public boolean isPrizeMoneyPositive(BigDecimal prizeMoney) {
        return prizeMoney.doubleValue() > 0;
    }

    public boolean isDateAfterNow(Date date) {
        return date.after(new Date());
    }

    public boolean isDistancePositive(int distance) {
        return distance > 0;
    }
}
