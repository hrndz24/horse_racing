package com.buyanova.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Sport event typically involving two or more horses ridden by jockeys
 * over a set {@code distance}, competing for {@code prizeMoney}.
 *
 * @author Natalie
 */
public class Race implements Serializable {

    private int id;
    private BigDecimal prizeMoney;
    private int horseWinnerId;
    private Date date;
    private int distance;
    private String location;

    public Race() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(BigDecimal prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public int getHorseWinnerId() {
        return horseWinnerId;
    }

    public void setHorseWinnerId(int horseWinnerId) {
        this.horseWinnerId = horseWinnerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return id == race.id &&
                horseWinnerId == race.horseWinnerId &&
                distance == race.distance &&
                prizeMoney.equals(race.prizeMoney) &&
                date.equals(race.date) &&
                location.equals(race.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", prizeMoney=" + prizeMoney +
                ", horseWinnerId=" + horseWinnerId +
                ", date=" + date +
                ", distance=" + distance +
                ", location='" + location + '\'' +
                '}';
    }
}
