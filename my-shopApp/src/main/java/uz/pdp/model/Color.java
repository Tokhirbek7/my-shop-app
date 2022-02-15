package uz.pdp.model;

import lombok.*;
import uz.pdp.model.abs.AbsEntity;
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@ToString
public class Color extends AbsEntity {
    private String name;
    private final String code=String.valueOf(
            (int) ((Math.random() * (9999 - 1000)) + 1000)) +
            (char) (int) ((Math.random() * (91 - 65)) + 65);;

    public Color(String name) {
        this.name = name;
        }
}
