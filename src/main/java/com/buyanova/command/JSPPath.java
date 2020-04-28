package com.buyanova.command;

public enum JSPPath {
    HOME_PAGE("/index.jsp"),
    USER_PAGE("/jsp/userPage.jsp"), SIGN_UP("/jsp/signUp.jsp"), EDIT_USER("/jsp/editUserInfo.jsp"),
    RACES("/jsp/races.jsp"), RACE("/jsp/race.jsp"), MAKE_BET("/jsp/makeBet.jsp"),
    PLACE_ODDS("/jsp/placeOddsForRace.jsp"),
    ADD_RACE("/jsp/addRace.jsp"), RACES_WITHOUT_RESULTS("/jsp/racesWithoutResults.jsp"),
    EDIT_RACE("/jsp/editRace.jsp"), RACES_WITHOUT_ODDS("/jsp/racesWithoutOdds.jsp"),
    SET_RACE_RESULTS("/jsp/setRaceResults.jsp"),
    USER_BETS("/jsp/userBets.jsp"), BET("/jsp/bet.jsp"),
    HORSES("/jsp/horses.jsp"), EDIT_HORSE("/jsp/editHorse.jsp"),
    ERROR_PAGE("/jsp/errorPage.jsp");

    private String path;

    JSPPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
