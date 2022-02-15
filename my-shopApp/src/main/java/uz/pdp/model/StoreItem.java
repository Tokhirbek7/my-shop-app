package uz.pdp.model;

import uz.pdp.model.abs.AbsClothQuantity;

public class StoreItem extends AbsClothQuantity {

    public StoreItem() {
    }

    public StoreItem(Cloth cloth, int quantity) {
        super(cloth, quantity);
    }
}
