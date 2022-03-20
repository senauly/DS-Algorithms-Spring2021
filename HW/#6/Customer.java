import java.util.ArrayList;

public interface Customer extends User{
    public ArrayList<Product> searchProduct(String name) throws NullPointerException;
    boolean queryProduct(String name);
    ArrayList<Product> sortByPrice(Boolean increasing);
    ArrayList<Product> sortByDiscount(Boolean increasing);
    ArrayList<Product> sortByName(Boolean increasing);
    ArrayList<Product> filterByPrice(double upper, double lower);
    ArrayList<Product> displayProduct(String traderName) throws NullPointerException;
    double calculateDiscount(double price, double discountPrice);

}
