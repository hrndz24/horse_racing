package com.buyanova.command;

public enum JSPParameter {

    COMMAND("command"), LOGIN("login"), PASSWORD("password"), EMAIL("email"),
    USER_NAME("userName"), NAME("name"), OLD_PASSWORD("oldPassword"), NEW_PASSWORD("newPassword"),
    LANGUAGE("language"), JSP("jsp"), LANG("lang"),
    USER("user"), RACE_ID("raceId"), RACES("races"),
    HORSES("horses"), HORSE_ID("horseId"), RACE("race"), HORSE("horse"),
    PERFORMING_HORSES("performingHorses"),
    ODDS("odds"), ODDS_ID("oddsId"),
    BET_SUM("betSum"), RACE_DATE("raceDate"), ODDS_IN_FAVOUR("oddsInFavour"),
    ODDS_AGAINST("oddsAgainst"), RACE_LOCATION("raceLocation"), RACE_PRIZE_MONEY("racePrizeMoney"),
    RACE_DISTANCE("raceDistance"), RACES_WITHOUT_RESULTS("racesWithoutResults"),
    RACES_WITHOUT_ODDS("racesWithoutOdds"), PAST_RACES("pastRaces"),
    PAGE_NUMBER("pageNumber"), CURRENT_PAGE("currentPage"), PAGE_QUANTITY("pageQuantity"),
    PAST_RACE("pastRace"), USERS("users"), USER_ID("userId"),
    HORSES_TYPE("horsesType"), SEARCH("search"),
    BETS("bets"), BET_ID("betId"), BET("bet"), SUM("sum"),
    HORSE_NAME("horseName"), HORSE_BREED("horseBreed"), HORSE_AGE("horseAge"),
    HORSE_WON_RACES("horseWonRaces"), HORSE_LOST_RACES("horseLostRaces"),
    PREVIOUS_REQUEST_HASH("previousRequestHash"),
    ERROR_MESSAGE("errorMessage");

    private String parameter;

    JSPParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
