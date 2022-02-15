package uz.pdp.Service;

import uz.pdp.model.Cloth;
import uz.pdp.model.Customer;
import uz.pdp.model.abs.User;
import uz.pdp.Util.*;

import java.util.List;

public interface CustomerService {
    boolean showClothes(List<Cloth> clothList) ;

    void myChart(Customer customer);

    void checkedCloth(Customer customer);

    void mainMenu(Customer customer);

    void selectClothes(Customer customer);

}
