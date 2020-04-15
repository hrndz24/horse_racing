package com.buyanova.command;

public enum JSPPath {
    HOME_PAGE("/index.jsp"), USER_PAGE("/jsp/userPage.jsp"), SIGN_UP("/jsp/signUp.jsp"),
    RACES("/jsp/races.jsp"), RACE("/jsp/race.jsp"), MAKE_BET("/jsp/makeBet.jsp"),
    PLACE_ODDS("/jsp/placeOddsForRace.jsp"),
    ERROR_PAGE("/jsp/errorPage.jsp");

    private String path;

    JSPPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
