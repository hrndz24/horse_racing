package buyanova.entity;

public enum UserRole {

    ADMINISTRATOR(1),
    CLIENT(2),
    JOCKEY(3),
    BOOKMAKER(4),
    GUEST(5);

    private int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserRole getById(int id) {
        for (UserRole role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        return GUEST;
    }
}
