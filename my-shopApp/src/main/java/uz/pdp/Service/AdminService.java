package uz.pdp.Service;

import uz.pdp.model.Admin;
import uz.pdp.model.abs.User;

public interface AdminService {
    void mainMenu(Admin admin);

    void clothMenu();
    void customerMenu();

}
