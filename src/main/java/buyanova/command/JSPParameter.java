package buyanova.command;

public enum JSPParameter {

    COMMAND("command"), LOGIN("login"), PASSWORD("password"), EMAIL("email"),
    USER_NAME("userName"), NAME("name");

    private String parameter;

    JSPParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
