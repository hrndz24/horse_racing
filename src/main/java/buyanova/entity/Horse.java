package buyanova.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Main participant of the races. The core element of the project.
 *
 * @author Natalie
 * */
public class Horse implements Serializable {

    private int id;
    private int jockeyId;
    private String name;
    private String breed;
    private int age;
    private boolean isPerforming;
    private int racesWonNumber;
    private int racesLostNumber;

    public Horse(){}

    public Horse(int id, int jockeyId, String name, String breed, int age, boolean isPerforming, int racesWonNumber, int racesLostNumber) {
        this.id = id;
        this.jockeyId = jockeyId;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.isPerforming = isPerforming;
        this.racesWonNumber = racesWonNumber;
        this.racesLostNumber = racesLostNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJockeyId() {
        return jockeyId;
    }

    public void setJockeyId(int jockeyId) {
        this.jockeyId = jockeyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isPerforming() {
        return isPerforming;
    }

    public void setPerforming(boolean performing) {
        isPerforming = performing;
    }

    public int getRacesWonNumber() {
        return racesWonNumber;
    }

    public void setRacesWonNumber(int racesWonNumber) {
        this.racesWonNumber = racesWonNumber;
    }

    public int getRacesLostNumber() {
        return racesLostNumber;
    }

    public void setRacesLostNumber(int racesLostNumber) {
        this.racesLostNumber = racesLostNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return id == horse.id &&
                jockeyId == horse.jockeyId &&
                age == horse.age &&
                isPerforming == horse.isPerforming &&
                racesWonNumber == horse.racesWonNumber &&
                racesLostNumber == horse.racesLostNumber &&
                name.equals(horse.name) &&
                breed.equals(horse.breed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Horse{" +
                "id=" + id +
                ", jockeyId=" + jockeyId +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", isPerforming=" + isPerforming +
                ", racesWonNumber=" + racesWonNumber +
                ", racesLostNumber=" + racesLostNumber +
                '}';
    }
}
