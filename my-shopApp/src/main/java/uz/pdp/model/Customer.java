package uz.pdp.model;

import lombok.*;
import uz.pdp.model.abs.User;
import uz.pdp.model.enums.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Customer extends User {
    private boolean isActive = true;
    private List<OrderItem> myCart = new ArrayList<>();



    public Customer(String fullName, String username,
                    String password, double balance
    ) {
        super(fullName, username, password, balance, Role.CUSTOMER);
    }

    public boolean isActive() {
        return isActive;
    }

    public void addMyCart(Cloth cloth, int quantity) {
        if (Objects.nonNull(myCart)) {
            myCart.add(new OrderItem(cloth, quantity));
        }
        else myCart = new ArrayList<>(Arrays.asList(
                new OrderItem(cloth,quantity)
        ));
    }
}
