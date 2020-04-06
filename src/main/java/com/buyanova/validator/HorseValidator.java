package com.buyanova.validator;

public class HorseValidator {

    public boolean isAgePositive(int age) {
        return age > 0;
    }

    public boolean isRacesNumberPositive(int racesNumber) {
        return racesNumber >= 0;
    }
}
