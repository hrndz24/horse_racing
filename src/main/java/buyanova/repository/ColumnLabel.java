package buyanova.repository;

public enum ColumnLabel {

    USER_ID("user_id"), USER_NAME("user_name"), USER_LOGIN("user_login"),
    USER_PASSWORD("user_password"), USER_EMAIL("user_email"), USER_ROLE_ID("user_role_id"),
    IS_ACTIVE("is_active"), USER_BALANCE("user_balance"),

    HORSE_ID("horse_id"), JOCKEY_ID("jockey_id"), HORSE_NAME("horse_name"),
    HORSE_BREED("horse_breed"), HORSE_AGE("horse_age"), IS_PERFORMING("is_performing"),
    RACES_WON_NUMBER("races_won_number"), RACES_LOST_NUMBER("races_lost_number"),

    BET_ID("bet_id"),

    RACE_ID("race_id");
    private String value;

    ColumnLabel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
