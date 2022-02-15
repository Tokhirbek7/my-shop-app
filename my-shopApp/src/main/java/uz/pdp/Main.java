package uz.pdp;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.Service.*;
import uz.pdp.bot.Bot;
import uz.pdp.model.Customer;

import static uz.pdp.Util.*;
public class Main {
    static AdminService adminService = new AdminServiceImpl();
    static CustomerService customerService = new CustomerServiceImpl();
    static AuthImpl auth = new AuthImpl();
    public static void main(String[] args) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);

            api.registerBot(new Bot());

            print("started...");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Db.fileReader();
        runApp();
    }
    private static void runApp() {
        print(CYAN, ">>>>>>>>>>>>>>MAIN MENU<<<<<<<<<<<<<<<<");
        while (true) {
            print(CYAN, "1-> logIn \n2-> register \n3-> select clothes \n4-> My cart \n5-> check out \n0-> Exit");

            int option = scnInt();
            Customer customer = null;
            switch (option) {
                case 1:
                    auth.login();
                    break;
                case 2:
                    auth.register();
                    break;
                case 3:
                    customerService.selectClothes(customer);
                    break;
                case 4:
                    customerService.myChart(customer);
                    break;
                case 5:
                    customerService.checkedCloth(customer);
                    break;
                case 0:
                    return;
                default:
                    System.err.println("wrong option");

            }
        }
    }

}