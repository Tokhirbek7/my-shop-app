package uz.pdp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.pdp.model.abs.AbsClothQuantity;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class OrderItem extends AbsClothQuantity {
    public OrderItem(Cloth cloth, int quantity) {
        super(cloth, quantity);
    }
}
