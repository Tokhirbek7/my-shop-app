package uz.pdp.model;


import lombok.AllArgsConstructor;
import uz.pdp.model.abs.AbsEntity;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PayType extends AbsEntity {
    private String name;
    private double commissionFee; // in percentage (foizda)



}
