package buyanova.entity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class User {

    private int id;
    private String name;
    private String login;
    private char[] password;
    private String email;
    private int userRoleId;
    private boolean isActive;
    private BigDecimal balance;

    public User(){}

    public User(int id, String name, String login, char[] password, String email, int userRoleId, boolean isActive, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.userRoleId = userRoleId;
        this.isActive = isActive;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                userRoleId == user.userRoleId &&
                isActive == user.isActive &&
                name.equals(user.name) &&
                login.equals(user.login) &&
                Arrays.equals(password, user.password) &&
                email.equals(user.email) &&
                balance.equals(user.balance);
    }

    @Override
    public int hashCode() { // TODO: 05.03.2020 maybe return plain id
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password=" + Arrays.toString(password) +
                ", email='" + email + '\'' +
                ", userRoleId=" + userRoleId +
                ", isActive=" + isActive +
                ", balance=" + balance +
                '}';
    }
}
