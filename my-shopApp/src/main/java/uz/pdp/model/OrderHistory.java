package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.pdp.model.abs.AbsEntity;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderHistory extends AbsEntity {
    private Customer customer;
    private PayType payType;
    private List<OrderItem> items = new ArrayList<>();
    private double price;
    private double commissionFeeSum;


}
