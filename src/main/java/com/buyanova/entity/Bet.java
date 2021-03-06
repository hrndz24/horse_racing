package com.buyanova.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * The money client stakes based on bookmaker's odds.
 *
 * @author Natalie
 * @see com.buyanova.entity.Odds
 */
public class Bet implements Serializable {

    private int id;
    private int userId;
    private BigDecimal sum;
    private int oddsId;

    public Bet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public int getOddsId() {
        return oddsId;
    }

    public void setOddsId(int oddsId) {
        this.oddsId = oddsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return id == bet.id &&
                userId == bet.userId &&
                oddsId == bet.oddsId &&
                Objects.equals(sum, bet.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", userId=" + userId +
                ", sum=" + sum +
                ", oddsId=" + oddsId +
                '}';
    }
}
