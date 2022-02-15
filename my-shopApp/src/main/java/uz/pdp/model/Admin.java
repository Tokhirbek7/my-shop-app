package uz.pdp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.model.abs.User;
import uz.pdp.model.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class Admin extends User {

    public Admin(String fullName, String username, String password, double balance) {
        super(fullName, username, password, balance, Role.ADMIN);
    }

    public Admin() {
    }

    public Admin(UUID id, LocalDateTime updatedAt, String fullName, String username, String password, double balance, Role role) {
        super(id, updatedAt, fullName, username, password, balance, role);
    }

    public Admin(String fullName, String username, String password, double balance, Role role) {
        super(fullName, username, password, balance, role);
    }


}
