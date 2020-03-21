package buyanova.entity;

import java.io.Serializable;
import java.util.Objects;

public class Odds implements Serializable {

    private int id;
    private int bookmakerId;
    private int horseId;
    private int raceId;
    private int winOdds;
    private int loseOdds;

    public Odds() {
    }

    public Odds(int id, int bookmakerId, int horseId, int raceId, int winOdds, int loseOdds) {
        this.id = id;
        this.bookmakerId = bookmakerId;
        this.horseId = horseId;
        this.raceId = raceId;
        this.winOdds = winOdds;
        this.loseOdds = loseOdds;
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

    public int getWinOdds() {
        return winOdds;
    }

    public void setWinOdds(int winOdds) {
        this.winOdds = winOdds;
    }

    public int getLoseOdds() {
        return loseOdds;
    }

    public void setLoseOdds(int loseOdds) {
        this.loseOdds = loseOdds;
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
                winOdds == odds.winOdds &&
                loseOdds == odds.loseOdds;
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
                ", winOdds=" + winOdds +
                ", loseOdds=" + loseOdds +
                '}';
    }
}
