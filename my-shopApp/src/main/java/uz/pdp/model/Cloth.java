package uz.pdp.model;

import uz.pdp.model.abs.AbsEntity;
import uz.pdp.model.enums.Size;
import uz.pdp.model.enums.Type;

import java.util.List;

import lombok.*;


@EqualsAndHashCode(callSuper = true)

@NoArgsConstructor
@Data
@ToString
public class Cloth extends AbsEntity {
    private String name;
    private List<Color> colors;
    private final String productCode=String.valueOf(
                (int) ((Math.random() * (9999 - 1000)) + 1000)) +
            (char) ((Math.random() * (91 - 65)) + 65);
    private Type type;
    private Size size;
    private int sizeInt;
    private double price;
    private double discount; // in percentage (foizda)


    public Cloth(String name, List<Color> colors, Type type,
                 Size size, int sizeInt, double price, double discount) {
        this.name = name;
        this.colors = colors;
        this.type = type;
        this.size = size;
        this.sizeInt = sizeInt;
        this.price = price;
        this.discount = discount;
    }
    public Cloth(String name, List<Color> colors, Type type, int sizeInt, double price, double discount) {
        this.name = name;
        this.colors = colors;
        this.type = type;
        this.sizeInt = sizeInt;
        this.price = price;
        this.discount = discount;

    }public Cloth(String name, List<Color> colors, Type type,
                 Size size, double price, double discount) {
        this.name = name;
        this.colors = colors;
        this.type = type;
        this.size = size;
        this.price = price;
        this.discount = discount;

    }



}
