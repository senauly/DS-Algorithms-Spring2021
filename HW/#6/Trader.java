import java.io.IOException;
import java.util.HashMap;
import java.util.Queue;

public interface Trader extends User{
    boolean addProduct(String productName, Category category, double price, double discountedPrice, String description) throws NullPointerException;
    boolean removeProduct(String productID);
    Queue<Order> displayOrders();
    boolean cancelOrder();
    boolean meetOrder();
    HashMap<String,Product> getProducts();
    boolean changeOrder(Boolean accepted);
}
