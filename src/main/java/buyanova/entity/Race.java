package buyanova.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Race implements Serializable {

    private int id;
    private BigDecimal prizeMoney;
    private int horseWinnerId;
    private Date date;
    private int distance;

    public Race() {
    }

    public Race(int id, BigDecimal prizeMoney, int horseWinnerId, Date date, int distance) {
        this.id = id;
        this.prizeMoney = prizeMoney;
        this.horseWinnerId = horseWinnerId;
        this.date = date;
        this.distance = distance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return id == race.id &&
                horseWinnerId == race.horseWinnerId &&
                distance == race.distance &&
                prizeMoney.equals(race.prizeMoney) &&
                date.equals(race.date);
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
                '}';
    }
}
