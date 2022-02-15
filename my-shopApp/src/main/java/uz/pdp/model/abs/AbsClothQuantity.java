package uz.pdp.model.abs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.model.Cloth;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbsClothQuantity extends AbsEntity {
    private Cloth cloth;
    private int quantity;


}
