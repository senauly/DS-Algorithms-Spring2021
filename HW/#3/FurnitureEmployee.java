import java.io.*;
public interface FurnitureEmployee {
    Boolean inquireStock(Product product);
    void informManager(Product product);
    Product addProduct(String type, String color, String model) throws IOException;
    Boolean removeProduct(String type, String color, String model);
    void makeSale(String name, String color, String model, Customer customer) throws IOException;
    void accesCustomerInfo(int customerNum);
    Customer registerCustomer(String name, String surname, String email, String password) throws IOException;
    void updatePreviousOrders(Product product, Customer customer);

}
