import java.io.IOException;
import java.util.ArrayList;

public abstract class Driver{
    public static final String PASSWORD = "123456";
    public static void customerFunctions(ShoppingCustomer customer){
        System.out.println("Search Product:\n");
        System.out.println("Searching 'women shoe' products");
        ArrayList<Product> products;
        try{
            products = customer.searchProduct("Women Shoe");
            for (Product product : products) {
                System.out.println(product.getName());
            }
            System.out.println("\nSearching null products");
            ArrayList<Product> products2 = customer.searchProduct(null);
            for (Product product : products2) {
                System.out.println(product.getName() + "\t" + product.getDescription());
            }
        }

        catch(NullPointerException e){
            System.err.println("Can't search null product.");
        }

        System.out.println("Sort 'women shoe' products by name increasing");
        products = customer.sortByName(true);
        for (Product product : products) {
            System.out.println(product.toString());
        }
        System.out.println();
        System.out.println("Sort 'women shoe' products by name decreasing");
        products = customer.sortByName(false);
        for (Product product : products) {
            System.out.println(product.toString());
        }

        System.out.println("Sort 'women shoe' products by price increasing");
        products = customer.sortByPrice(true);
        for (Product product : products) {
            System.out.println(product.toString());
        }
        System.out.println();
        System.out.println("Sort 'women shoe' products by price decreasing");
        products = customer.sortByPrice(false);
        for (Product product : products) {
            System.out.println(product.toString());
        }

        System.out.println("Sort 'women shoe' products by discount increasing");
        products = customer.sortByDiscount(true);
        for (Product product : products) {
            System.out.println(product.toString());
        }
        System.out.println();
        System.out.println("Sort 'women shoe' products by discount decreasing");
        products = customer.sortByDiscount(false);
        for (Product product : products) {
            System.out.println(product.toString());
        }

        System.out.println();
        System.out.println("Display Products of 'Zyxel'");
        products = customer.displayProduct("Zyxel");
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    public static void main(String[] args) {
        ShoppingApplication app = new ShoppingApplication();
        ShoppingCustomer customer = (ShoppingCustomer) app.register(new ShoppingCustomer(),PASSWORD, "Sena");
        customerFunctions(customer);
    }
}
