package com.buyanova.command;

public enum JSPParameter {

    COMMAND("command"), LOGIN("login"), PASSWORD("password"), EMAIL("email"),
    USER_NAME("userName"), NAME("name"), LANGUAGE("language"), JSP("jsp"), LANG("lang"),
    USER("user"), RACE_ID("raceId"), RACES("races"), HORSES("horses"),
    HORSE_ID("horseId"), RACE("race"), HORSE("horse"), ODDS("odds"), ODDS_ID("oddsId"),
    BET_SUM("betSum"), RACE_DATE("raceDate");

    private String parameter;

    JSPParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
