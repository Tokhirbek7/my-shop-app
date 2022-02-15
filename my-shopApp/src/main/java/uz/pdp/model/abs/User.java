package uz.pdp.model.abs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.model.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public  class User extends AbsEntity {
    private String fullName;
    private String username;
    private String password;
    private double balance;
    private Role role;


    public User() {
    }

    public User(UUID id, LocalDateTime updatedAt, String fullName, String username, String password, double balance, Role role) {
        super(id, updatedAt);
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public User(String fullName, String username, String password, double balance, Role role) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }
}
