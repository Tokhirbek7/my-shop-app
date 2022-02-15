package uz.pdp.Service;

import uz.pdp.model.*;
import uz.pdp.model.enums.Role;
import uz.pdp.model.enums.Size;
import uz.pdp.model.enums.Type;
import static uz.pdp.Db.*;

import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uz.pdp.Db.*;
import static uz.pdp.Util.*;

public class AdminServiceImpl implements AdminService {
    public static String clothPath = "src/main/resources/jsons/ClothDB.json";
    public static String storeItemPath = "src/main/resources/jsons/StoreItem.json";
    public static String colorPath = "src/main/resources/jsons/ColorDb.json";

    public static CustomerServiceImpl customerService = new CustomerServiceImpl();

    @Override
    public void mainMenu(Admin admin) {
        print(CYAN, ">>>>>>>>>>==ADMIN PANEL==<<<<<<<<<<<");
        print(CYAN, "1-> Clothe menu \n2-> Customer menu\n3-> Color menu\n0-> log out");
        int option = scnInt();
        switch (option) {
            case 1:
                clothMenu();
                break;
            case 2:
                customerMenu();
                break;
            case 3:
                colorMenu();
                break;
            case 0:
                return;
            default:
                System.err.println("please choose again ");
        }
        mainMenu(admin);
    }

    private void colorMenu() {
        print(CYAN,"1-> show colors \n2-> create color \n3-> update color \n4->delete color\nother button-> prev");
        int option =scnInt();
        switch (option) {
            case 1:
                showColors();
                break;
            case 2:
                createColor();

                break;
            case 3:
                updateColor();
                break;
            case 4:
                deleteColor();
                break;
            default:
                return;

        }
        colorMenu();
    }

    private void deleteColor() {
    }

    private void updateColor() {
    }

    private void createColor() {
        print(BLACK,"Input color name: ");
        String name = scnStr();
        if (Objects.nonNull(colorList)) {
            colorList.add(new Color(name));
        }
        else colorList = new ArrayList<>(Arrays.asList(
                new Color(name)
        ));
        writer(colorList, colorPath);
        print(GREEN_BOLD,"Successfully created");
    }

    private void showColors() {
        if (!Objects.nonNull(colorList)) {
            print(RED,"Sizda rang mavjud emas");
            return;
        }
        for (Color color : colorList) {
            print(YELLOW,"Code: "+color.getCode()+"\nName: "+color.getName());
        }
    }

    public static boolean isAd = true;

    @Override
    public void clothMenu() {
        print(CYAN, "1-> Select clothe \n2-> Update clothe \n3-> Create clothe \n4-> Delete clothe \n0-> back");
        int option = scnInt();
        switch (option) {
            case 1:
                selectClothe();
                break;
            case 2:
                updateClothe();
                break;
            case 3:
                createClothe();
                break;
            case 4:
                deleteClothe();
                break;
            case 0:
                return;
            default:
                System.err.println("Error");
        }
        clothMenu();
    }

    private void deleteClothe() {

    }

    private void createClothe() {
        print(BLACK,"Input cloth name: ");
        String name = scnStr();
        for (Type value : Type.values()) {
            System.out.println(value);
        }
        print(BLACK,"Input type: ");
        String typeStr= scnStr();
        Type type1 = Type.valueOf(typeStr.toUpperCase());
        for (Size value : Size.values()) {
            System.out.println(value);
        }
        String sizeStr= scnStr();
        Size size = Size.valueOf(sizeStr.toUpperCase());
        print(BLACK,"Input size number: ");
        int sizeInt= scnInt();
        print(BLACK,"Input price: ");
        double price = scnDouble();
        print(BLACK,"Input discount: ");
        double discount = scnDouble();
        print(BLACK,"Input quantity: ");
        int quantity = scnInt();
        List<Color> colors = selectColor();
        Cloth cloth = new Cloth(name,
                colors,type1,size,sizeInt,price,discount);
        if (Objects.nonNull(clothList)) {
            clothList.add(cloth);
        }
        else {
            clothList = new ArrayList(Arrays.asList(cloth));
        }
        boolean writer = writer(clothList, clothPath);
        if (writer) {
            print(GREEN_BOLD,"Successfully added");
        }
        StoreItem storeItem = new StoreItem(cloth,quantity);
        if (Objects.nonNull(storeItemList)) {
            storeItemList.add(storeItem);
        }
        else storeItemList = new ArrayList<>(Arrays.asList(storeItem));
        writer(storeItemList,storeItemPath);
    }

    private List<Color> selectColor() {
       showColors();
       List<Color> colors = new ArrayList<>();
        String  code="";
       while (!code.equals("0")) {
           print(BLACK, "Input color code(0->prev): ");
           code = scnStr();
           for (Color color : colorList) {
               if (color.getCode().equalsIgnoreCase(code)) {
                   colors.add(color);
                   break;
               }
           }
       }
       return colors;
    }


    public static Set<Color> colors = new HashSet<>();

    private void updateClothe() {

    }

    private void selectClothe() {

    }

    @Override
    public void customerMenu() {
        print(CYAN, "1-> Active Customer\n2-> show order history\n3->show customer\n0-> back");
        int option = scnInt();
        switch (option) {
            case 1:
                activeCustomer();
                break;
            case 2:
                showOrderHistory();
                break;
            case 3:
                showCustomer();
                break;
            case 0:
                return;
            default:
                System.err.println("Error!!! \n  Wrong option");
        }
        customerMenu();
    }

    private void showCustomer() {

    }

    private void showOrderHistory() {
        orderHistoryList.stream().forEach(System.out::println);
        print(BLACK,"1-> download exsel\n2-> back");
        int option =scnInt();
        switch (option) {
            case 1:
                customerService.writeExcel(orderHistoryList);
                break;
            default:
                return;
        }

    }

    private void activeCustomer() {

    }

}
