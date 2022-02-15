package uz.pdp.Service;

import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import static uz.pdp.Util.*;
import uz.pdp.model.*;
import uz.pdp.model.abs.AbsClothQuantity;
import uz.pdp.model.enums.Role;
import uz.pdp.model.enums.Type;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import static uz.pdp.Service.AuthImpl.*;
import java.util.stream.Stream;

import static uz.pdp.Db.*;

public class CustomerServiceImpl implements CustomerService {

    static List<OrderItem> myCart  = new ArrayList<>();
    public static String orderHistoryPath = "src/main/resources/jsons/OrderHistory.json";
    @Override
    public void mainMenu(Customer customer) {
        if(!myCart.isEmpty()) customer.setMyCart(myCart);
        while (true) {
            print(CYAN, "1-> select cloth \n2-> show cloth \n3-> check out \n4-> order item history \n5-> My cart" +
                    "\n0->exit");
            int option = scnInt();
            switch (option) {
                case 1:
                    selectClothes(customer);
                    break;
                case 2:
                    showClothes(clothList);
                    break;
                case 3:
                    checkedCloth(customer);
                    break;
                case 4:
                    showHistory(customer);
                    break;
                case 5:
                    myChart(customer);
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Wrong option");
            }
        }
    }

    @Override
    public boolean showClothes(List<Cloth> clothList) {
        print(BLACK,"1-> price\n2-> Type \n3-> has discount\n4-> All clothes\n0-> back");
        int option = scnInt();
        switch (option) {
            case 1:
                print(BLACK,"max price: ");
                double maxPrice = scnDouble();
                print(BLACK,"min price: ");
                double minPrice = scnDouble();
                clothList.stream()
                        .filter(cloth -> cloth.getPrice()<maxPrice)
                        .filter(cloth -> cloth.getPrice()>minPrice)
                        .peek(cloth -> System.out.print(
                                "code: "+cloth.getProductCode()+
                                "\tName: "+cloth.getName()+
                                "\tType: "+cloth.getType()+
                                        "\tPrice: "+
                                        (cloth.getDiscount()>0.0?
                                                "\033[0;9;30m"+cloth.getPrice()+RESET+"->"+
                                                        (cloth.getPrice()-cloth.getPrice()*cloth.getDiscount()/100)+"" :
                                                cloth.getPrice() )+
                                "\tColor: +"
                        )).forEach(cloth -> System.out.println(cloth.getColors()));
                break;
            case 2:
                Arrays.stream(Type.values()).forEach(System.out::println);
                print(BLACK,"Input chosen type: ");
                String type = scnStr();
                Type type1 = Type.valueOf(type.toUpperCase());
                clothList.stream()
                        .filter(cloth -> cloth.getType().equals(type1))
                        .peek(cloth -> System.out.print(
                                "code: "+cloth.getProductCode()+
                                        "\tName: "+cloth.getName()+
                                        "\tType: "+cloth.getType()+
                                        "\tPrice: "+
                                        (cloth.getDiscount()>0.0?
                                                "\033[0;9;30m"+cloth.getPrice()+RESET+"->"+
                                                        (cloth.getPrice()-cloth.getPrice()*cloth.getDiscount()/100)+"" :
                                                cloth.getPrice() )+
                                        "\tColor: +"
                        )).forEach(cloth -> System.out.println(cloth.getColors()));

                break;
            case 3:
                clothList.stream().filter(cloth -> cloth.getDiscount()>0.0)
                        .peek(cloth -> System.out.print(
                                "code: "+cloth.getProductCode()+
                                        "\tName: "+cloth.getName()+
                                        "\tType: "+cloth.getType()+
                                        "\tPrice: "+
                                        (cloth.getDiscount()>0.0?
                                                "\033[0;9;30m"+cloth.getPrice()+RESET+"->"+
                                                        (cloth.getPrice()-cloth.getPrice()*cloth.getDiscount()/100)+"" :
                                                cloth.getPrice() )+
                                        "\tColor: +"
                        )).forEach(cloth -> System.out.println(cloth.getColors()));
                break;
            case 4:
                clothList.stream()
                        .peek(cloth -> System.out.print(
                                "code: "+cloth.getProductCode()+
                                        "\tName: "+cloth.getName()+
                                        "\tType: "+cloth.getType()+
                                        "\tPrice: "+
                                        (cloth.getDiscount()>0.0?
                                                "\033[0;9;30m"+cloth.getPrice()+RESET+"->"+
                                                        (cloth.getPrice()-cloth.getPrice()*cloth.getDiscount()/100)+"" :
                                                cloth.getPrice() )+
                                        "\tColor: +"
                        )).forEach(cloth -> System.out.println(cloth.getColors()));
                break;
            case 0:
                return false;
        }
        return true;
    }

    @Override
    public void myChart(Customer customer) {
        if (Objects.nonNull(myCart)) {
            myCart.stream().
                    peek(orderItem -> System.out.println("Cloth name: "+orderItem.getCloth().getName()
                            +"\tQuantity: "+orderItem.getQuantity())
                            ).forEach(orderItem -> System.out.println("\tCode: "+orderItem.getCloth().getProductCode()));
        }
    }

    @Override
    public void checkedCloth(Customer customer) {

        if(customer==null) {
            System.err.println("Please login or register");
            return;
        }
        if (!Objects.nonNull(myCart)) {
            System.err.println("please choose cloth first!!!");
            return;
        }
        customer.setMyCart(myCart);
        myCart=new ArrayList<>();
        for (int i = 0; i < payTypeList.size(); i++) {
            System.out.println((i + 1) + " >>  " + payTypeList.get(i));
        }
        int option = scnInt();
        double sum = 0;
        for (OrderItem orderItem : customer.getMyCart()) {
            long count = storeItemList.stream().filter(item -> item.getCloth().equals(orderItem.getCloth()))
                    .filter(item -> item.getQuantity() >= orderItem.getQuantity()).count();
            if (count==0) continue;
            sum+=(orderItem.getCloth().getPrice()-(orderItem.getCloth().getPrice()*orderItem.getCloth().getDiscount()/100))
                    *orderItem.getQuantity();
            myCart.add(orderItem);
        }
        customer.setMyCart(myCart);
        OrderHistory orderHistory = new OrderHistory(
                customer,payTypeList.get(option-1),customer.getMyCart(),
                sum,sum*payTypeList.get(option-1).getCommissionFee()/100
        );
        print(GREEN,"Thanks for purchase\n \tcome again");
        print(BLUE,"Your check : ");

        File file = new File("src/main/resources/jsons/check.pdf");
        try (PdfWriter writer = new PdfWriter(file.getPath())) {
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.addNewPage();
            Document doc = new Document(pdfDoc);
            Paragraph paragraph = new Paragraph("SalomSalomBarcha barchaga shumtakalar nomidan");
            float[] columnsWidth = {200f,120f,120f,120f};
            Table table  = new Table(columnsWidth);
            table.addCell(new Cell().add("Name"));
            table.addCell(new Cell().add("Summa"));
            table.addCell(new Cell().add("Commission summa"));
            table.addCell(new Cell().add("Date"));
            LocalDateTime localDateTime = orderHistory.getUpdatedAt();
            table.addCell(new Cell().add(orderHistory.getCustomer().getFullName()));
            table.addCell(new Cell().add(orderHistory.getPrice()+""));
            table.addCell(new Cell().add(orderHistory.getCommissionFeeSum()+""));
            table.addCell(new Cell().add(localDateTime+""));
            float [] columnsWidth1 = {210f,100f,100f};
            Table table1 = new Table(columnsWidth1);
            table1.addCell(new Cell().add("Cloth Name"));
            table1.addCell(new Cell().add("Cloth quantity"));
            table1.addCell(new Cell().add("cloth cost"));
            /////////////////////////////// new
            for (int i = 0; i < orderHistory.getItems().size(); i++) {
                table1.addCell(new Cell().add(orderHistory.getItems().get(i).getCloth().getName()));
                table1.addCell(new Cell().add(orderHistory.getItems().get(i).getQuantity()+""));
                table1.addCell(new Cell().add(orderHistory.getItems().get(i).getCloth().getPrice()+""));
            }

            doc.add(table);
            doc.add(table1);
            doc.add(paragraph);
            System.out.println(file.getAbsolutePath());
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Objects.nonNull(orderHistoryList)) {
            orderHistoryList.add(orderHistory);
        }
        else {
            orderHistoryList = new ArrayList<>(Arrays.asList(orderHistory));
        }
        writer(orderHistoryList,orderHistoryPath);
        System.out.println("Jami summa: "+sum*(1+payTypeList.get(option-1).getCommissionFee()/100));
        customer.setMyCart(new ArrayList<>()) ;
        customer.setBalance(customer.getBalance()-sum*(1+payTypeList.get(option-1).getCommissionFee()/100));
        writer(users,usPath);
        writer(admin,"src/main/resources/jsons/DbAdmin.json");
        double finalSum = sum;
        admin.forEach((s, admin1) -> admin1.setBalance(admin1.getBalance()+finalSum));

    }



    private void showHistory(Customer customer) {
        for (OrderHistory orderHistory : orderHistoryList) {
            print(GREEN,""+orderHistory);
        }
        print(CYAN,"0-> back 1-> download Excel file");
        int option = scnInt();
        switch (option) {
            case 1:
                writeExcel(orderHistoryList);
                break;
            case 0:
                return;
            default:
                System.err.println("Wrong option!!!");
        }

    }

    public static void writeExcel(List<OrderHistory> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        File file = new File("src/main/resources/HistoryOrder.xlsx");
        try (OutputStream out = new FileOutputStream(file.getPath())) {

            XSSFSheet sheet = workbook.createSheet("History");
            sheet.setDefaultColumnWidth(20);
            sheet.setDefaultRowHeightInPoints(20);
            XSSFRow row = sheet.createRow(0);

            row.createCell(0).setCellValue("№");
            row.createCell(1).setCellValue("Customer Name");
            row.createCell(2).setCellValue("Cloth");
            row.createCell(3).setCellValue("quantity");
            row.createCell(4).setCellValue("Cost");
            row.createCell(5).setCellValue("Summa");
            row.createCell(6).setCellValue("Commission fee sum");
            row.createCell(7).setCellValue("Date");


            LocalDateTime localDateTime ;
            for (int i=0; i<list.size(); i++) {
                OrderHistory orderHistory = list.get(i);

                XSSFRow rowN = sheet.createRow(i+1);
                rowN.createCell(0).setCellValue(i+1);
                rowN.createCell(1).setCellValue(orderHistory.getCustomer().getFullName());
                for (OrderItem item : orderHistory.getItems()) {
                    rowN.createCell(2).setCellValue(item.getCloth().getName()+"\n");
                    rowN.createCell(3).setCellValue(item.getQuantity()+"\n");
                    rowN.createCell(4).setCellValue(item.getCloth().getPrice());
                }
                rowN.createCell(5).setCellValue(orderHistory.getPrice());
                rowN.createCell(6).setCellValue(orderHistory.getCommissionFeeSum());
                localDateTime = orderHistory.getUpdatedAt();
                rowN.createCell(7).setCellValue(
                        "Month: "+localDateTime.getMonth()+"\t      day : "
                                +localDateTime.getDayOfMonth()+"\t     Year: "+
                                localDateTime.getYear());




            }
            int sum = list.size()+1;
            XSSFRow row1 = sheet.createRow(sum);
            row1.createCell(0).setCellValue("sum : ");
            String column_letter = CellReference.convertNumToColString(sum);
            workbook.write(out);
    } catch (IOException e) {
        e.printStackTrace();
    }

        System.out.println(file.getAbsolutePath());
    }

    @Override
    public void selectClothes(Customer customer) {
        showClothes(clothList);
        print(BLACK,"Input by product code: ");
        String code = scnStr();
        print(BLACK,"input quantity: ");
        int quantity = scnInt();
        Cloth clothSelect;
        for (Cloth cloth : clothList) {
            if (cloth.getProductCode().equalsIgnoreCase(code)) {
                clothSelect = cloth;
                if (Objects.nonNull(myCart)) {
                    myCart.add(new OrderItem(clothSelect,quantity));
                }
                else myCart = new ArrayList<>(Arrays.asList(
                        new OrderItem(clothSelect,quantity)
                ));
                print(GREEN_BOLD,"The cloth successfully added");
                break;
            }
        }

    }
}
