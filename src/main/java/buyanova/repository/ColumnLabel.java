package buyanova.repository;

public enum ColumnLabel {

    USER_ID("user_id"), USER_NAME("user_name"), USER_LOGIN("user_login"),
    USER_PASSWORD("user_password"), USER_EMAIL("user_email"), USER_ROLE_ID("user_role_id"),
    IS_ACTIVE("is_active"), USER_BALANCE("user_balance"),

    HORSE_ID("horse_id"),

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
