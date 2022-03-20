import java.io.*;
public interface FurnitureCustomer{
    Boolean register(String password) throws IOException;
    int searchProduct(String name, String color, String model);
    void seeProducts();
    Branch whereIsTheProduct(String name, String color, String model) throws IOException;
    void shopOnline(String adress, String phone, Product product);
    void viewPreviousOrders();
}