package uz.pdp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import uz.pdp.Service.AdminServiceImpl;
import uz.pdp.model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public abstract class Db {

    public static Map<String ,Customer> users = new HashMap<>();
    public static Map<String ,Admin> admin = new HashMap<>();
    public static Scanner scannerStr = new Scanner(System.in);
    public static Scanner scannerInt = new Scanner(System.in);
    public static List <Cloth>clothList = new ArrayList<>(Arrays.asList());
    public static List<Color> colorList = new ArrayList<>();
    public static List<PayType> payTypeList = new ArrayList<>();
    public static List<OrderHistory> orderHistoryList = new ArrayList<>();
    public static GsonBuilder gsonBuilder = new GsonBuilder();
    public static Gson gson = gsonBuilder.setPrettyPrinting().create();
    public static List<StoreItem> storeItemList = new ArrayList<>();

   static public  <T> boolean writer(List<T> list, String path) {
        try (Writer writer = new FileWriter(path)) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            String json = gson.toJson(list, new TypeToken <ArrayList<T>>(){}.getType());
//            System.out.println(json);
            writer.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
static public  <T> boolean writer(Map<String,T> map, String path) {
        try (Writer writer = new FileWriter(path)) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            String json = gson.toJson(map, new TypeToken <HashMap<String, T>>(){}.getType());
//            System.out.println(json);
            writer.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void fileReader() {





        //        List<PayType> payTypeList = new ArrayList<>(Arrays.asList(
//                new PayType("Click",0.5),
//                new PayType("Pay me",0.7)
//        ));



//        List<Color> colorList = new ArrayList<>(Arrays.asList(
//                new Color("Red","145"),
//                new Color("Black","54a"),
//                new Color("White", "87k")
//        ));
//        List<Cloth> clothList = new ArrayList<>(Arrays.asList(
//                new Cloth("T-shirt", new ArrayList<>(Arrays.asList(colorList.get(1))),
//                        Type.FEMALE, Size.M,45,45000,0),
//                new Cloth("Suit", new ArrayList<>(Arrays.asList(colorList.get(2))),
//                        Type.MALE, Size.S,48,65000,0)
//
//        ));
//        admin.put("a",
//                new Admin("Admin Adminov","a","1",0,Role.ADMIN));

//        try (Writer writer = new FileWriter("src/main/java/DbAdmin.json")) {
//
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            Gson gson = gsonBuilder.setPrettyPrinting().create();
//
//            String jsonjf = gson.toJson(admin,new TypeToken<ArrayList<StoreItem>>(){}.getType());
//            System.out.println(jsonjf);
//            writer.write(jsonjf);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/jsons/DB.json"))) {
            users =  gson.fromJson(reader,new TypeToken<Map<String,Customer>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/jsons/ClothDB.json"))) {
            clothList = gson.fromJson(reader,new TypeToken<ArrayList<Cloth>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }



        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/jsons/ColorDb.json"))) {
            colorList =  gson.fromJson(reader,new TypeToken<ArrayList<Color>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/jsons/StoreItem.json"))) {
            storeItemList =  gson.fromJson(reader,new TypeToken<ArrayList<StoreItem>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/jsons/payTypeDb.json"))) {
            payTypeList =  gson.fromJson(reader,new TypeToken<ArrayList<PayType>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/jsons/OrderHistory.json"))) {
            orderHistoryList =  gson.fromJson(reader,new TypeToken<ArrayList<OrderHistory>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/jsons/DbAdmin.json"))) {
            admin = gson.fromJson(reader, new TypeToken<Map<String, Admin>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static  <T> T read(Type type, String path)  {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                 return gson.fromJson(reader,type);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
// try (Writer writer = new FileWriter("src/main/java/DbAdmin.json")) {
//
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            Gson gson = gsonBuilder.setPrettyPrinting().create();
//
//            String jsonjf = gson.toJson(admin,new TypeToken<ArrayList<StoreItem>>(){}.getType());
//            System.out.println(jsonjf);
//            writer.write(jsonjf);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


}
