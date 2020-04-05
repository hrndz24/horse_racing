package com.buyanova.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representation of numerical expression, usually described as a pair
 * of numbers, used in both gambling and statistics.
 * <p>
 * Conventionally, gambling odds are expressed in the form <b>X to Y</b>.
 * Here <b>X</b> is represented by {@code oddsInFavour}
 * and <b>Y</b> is {@code oddsAgainst}.
 * <p>
 * In gambling, odds represent the ratio between the amounts
 * staked by parties to a bet.
 * In simplest terms, <b>5 to 2</b> odds means if you bet a dollar,
 * and you win you get paid five dollars (1 times 5),
 * in other case you lose two dollars (1 times 2).
 *
 * @author Natalie
 * */
public class Odds implements Serializable {

    private int id;
    private int bookmakerId;
    private int horseId;
    private int raceId;
    private int oddsInFavour;
    private int oddsAgainst;

    public Odds() {
    }

    public Odds(int id, int bookmakerId, int horseId, int raceId, int oddsInFavour, int oddsAgainst) {
        this.id = id;
        this.bookmakerId = bookmakerId;
        this.horseId = horseId;
        this.raceId = raceId;
        this.oddsInFavour = oddsInFavour;
        this.oddsAgainst = oddsAgainst;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookmakerId() {
        return bookmakerId;
    }

    public void setBookmakerId(int bookmakerId) {
        this.bookmakerId = bookmakerId;
    }

    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horseId) {
        this.horseId = horseId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getOddsInFavour() {
        return oddsInFavour;
    }

    public void setOddsInFavour(int oddsInFavour) {
        this.oddsInFavour = oddsInFavour;
    }

    public int getOddsAgainst() {
        return oddsAgainst;
    }

    public void setOddsAgainst(int oddsAgainst) {
        this.oddsAgainst = oddsAgainst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Odds odds = (Odds) o;
        return id == odds.id &&
                bookmakerId == odds.bookmakerId &&
                horseId == odds.horseId &&
                raceId == odds.raceId &&
                oddsInFavour == odds.oddsInFavour &&
                oddsAgainst == odds.oddsAgainst;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Odds{" +
                "id=" + id +
                ", bookmakerId=" + bookmakerId +
                ", horseId=" + horseId +
                ", raceId=" + raceId +
                ", winOdds=" + oddsInFavour +
                ", loseOdds=" + oddsAgainst +
                '}';
    }
}
