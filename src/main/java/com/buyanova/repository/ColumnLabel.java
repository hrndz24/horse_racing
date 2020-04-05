package com.buyanova.repository;

/**
 * A {@code ColumnLabel} enum is used to encapsulate the {@code value}
 * that represents a column name of a table from the database of the project.
 *
 * @author Natalie
 */
public enum ColumnLabel {

    USER_ID("user_id"), USER_NAME("user_name"), USER_LOGIN("user_login"),
    USER_PASSWORD("user_password"), USER_EMAIL("user_email"), USER_ROLE_ID("user_role_id"),
    USER_IS_ACTIVE("user_is_active"), USER_BALANCE("user_balance"),

    HORSE_ID("horse_id"), JOCKEY_ID("jockey_id"), HORSE_NAME("horse_name"),
    HORSE_BREED("horse_breed"), HORSE_AGE("horse_age"), IS_PERFORMING("is_performing"),
    RACES_WON_NUMBER("races_won_number"), RACES_LOST_NUMBER("races_lost_number"),

    BET_ID("bet_id"), BET_SUM("bet_sum"),

    ODDS_ID("odds_id"), BOOKMAKER_ID("bookmaker_id"), ODDS_IN_FAVOUR("odds_in_favour"),
    ODDS_AGAINST("odds_against"),

    RACE_ID("race_id"), RACE_PRIZE_MONEY("race_prize_money"), RACE_DISTANCE("race_distance"),
    RACE_DATE("race_date"), HORSE_WINNER_ID("horse_winner_id");

    private String value;

    ColumnLabel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
