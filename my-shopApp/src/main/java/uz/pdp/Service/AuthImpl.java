package uz.pdp.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uz.pdp.model.Admin;
import uz.pdp.model.Customer;
import uz.pdp.model.OrderItem;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import static uz.pdp.Db.*;
import static uz.pdp.Util.*;

public class AuthImpl implements Auth {

    static AdminServiceImpl adminService = new AdminServiceImpl();
    static CustomerServiceImpl customerService = new CustomerServiceImpl();
    static Admin adminLog=null;
    static Customer customerLog =null;
    public static OrderItem myCart = null;

    public static String usPath = "src/main/resources/jsons/DB.json";
    @Override
    public void login() {
        boolean isLog =false;
        print(BLACK,"Input user name: ");
        String userName = scnStr();
        print(BLACK,"Input password: ");
        String password = scnStr();
        if(users!=null&&
                users.containsKey(userName)&&
                users.get(userName).getPassword().equals(password))
            customerLog = users.get(userName);
        else if(admin.size()!=0 &&
                admin.containsKey(userName) && admin.get(userName).getPassword().equals(password))
            adminLog = admin.get(userName);
        if (adminLog!=null) adminService.mainMenu((adminLog) );
        else if(customerLog !=null)  customerService.mainMenu(customerLog);
         else    System.err.println("password or username is incorrect\n Please input again");
    }
    @Override
    public void register() {
        File file = new File("src/main/resources/jsons/DB.json");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        System.out.println("fullName: ");
        String fullName = scnStr();
        System.out.println("username: ");
        String username = scnStr();
        System.out.println("Password: ");
        String password = scnStr();
        if (users==null) {
            users = new HashMap<>();
            users.put(username,new Customer(fullName,username,password,10));
        }
        else users.put(username,new Customer(fullName,username,password,10));
        writer(users,usPath);
    }
}
