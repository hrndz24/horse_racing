package com.buyanova.command;

public enum JSPParameter {

    COMMAND("command"), LOGIN("login"), PASSWORD("password"), EMAIL("email"),
    USER_NAME("userName"), NAME("name"), LANGUAGE("language"), JSP("jsp"), LANG("lang"),
    USER("user"), RACE_ID("raceId"), RACES("races"),
    HORSES("horses"), HORSE_ID("horseId"), RACE("race"), HORSE("horse"),
    PERFORMING_HORSES("performingHorses"),
    ODDS("odds"), ODDS_ID("oddsId"),
    BET_SUM("betSum"), RACE_DATE("raceDate"), ODDS_IN_FAVOUR("oddsInFavour"),
    ODDS_AGAINST("oddsAgainst"), RACE_LOCATION("raceLocation"), RACE_PRIZE_MONEY("racePrizeMoney"),
    RACE_DISTANCE("raceDistance"), RACES_WITHOUT_RESULTS("racesWithoutResults"),
    BETS("bets"),
    ERROR_MESSAGE("errorMessage");

    private String parameter;

    JSPParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
