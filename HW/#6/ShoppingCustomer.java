import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Scanner;

public class ShoppingCustomer implements Customer{
    private String username;
    private String password;
    private String ID;
    private final String productFilePath = "files/e-commerce-samples.txt";
    private ArrayList<Product> searchedProducts;
    private Queue<Order> orders;


    public ShoppingCustomer(){
        searchedProducts = new ArrayList<>();
        orders = new ArrayDeque<>();
    }

    public ShoppingCustomer(String username, String password, String ID){
        this.username = username;
        this.password = password;
        this.ID = ID;
        orders = new ArrayDeque<>();
        searchedProducts = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<Product> getSearchedProducts() {
        return this.searchedProducts;
    }

    public Queue<Order> getOrders() {
        return this.orders;
    }

    /**
     * Search products by name
     * @param name to search
     * @return ArrayList of products
     * @throws NullPointerException if name is null.
     */
    @Override
    public ArrayList<Product> searchProduct(String name) throws NullPointerException{
        if(name == null) throw new NullPointerException();
        File file = new File(productFilePath);
        
        try {
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                String productName = FileHelper.trimLine(data,2);
                String description = FileHelper.trimLine(data,6);
                if(description.contains(name) || productName.contains(name)){
                    String productID = FileHelper.trimLine(data,1);
                    Category cg = FileHelper.trimCategory(FileHelper.trimLine(data,3));
                    double price = Double.parseDouble(FileHelper.trimLine(data, 4));
                    double discount = Double.parseDouble(FileHelper.trimLine(data, 5));
                    String trader = FileHelper.trimLine(data,7);
                    Product newProduct = new Product(productID, cg, productName, description, price, discount, trader);
                    searchedProducts.add(newProduct);
                }
            }
            
            reader.close();

        }
        
        catch (FileNotFoundException e) {
            System.err.println("File does not exist.\n");
        }
        
        return searchedProducts;
    }

    /**
     * Request a product if product in that name exist.
     * @param name to request
     * @return true if exist, false otherwise
     */
    @Override
    public boolean queryProduct(String name) {
        if(name == null) throw new NullPointerException();
        File file = new File("files/orders.txt");
        
        for (Product product : searchedProducts) {
            if(product.getName().equals(name)){
                try {
                    boolean isFirst = false;
                    FileWriter writer;
                    if(file.exists()) writer = new FileWriter(file, true);
                    else{
                        writer = new FileWriter(file);
                        isFirst = true;
                    }
                    
                    if(!isFirst) writer.append("\n");
                    writer.append(this.ID + ";" + product.getTraderName() + ";" + product.getID() + Order.status.WAITING);
                    orders.add(new Order(this.ID, product.getTraderName(), product));
                    writer.close();
                    return true;
                }       
        
                catch (IOException e) {
                    System.err.println("Error occured while reading/writing from/to file.\n");
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * Sort searched products by price.
     * @param increasing if list is going to be in increasing order.
     * @return sorted arraylist.
     */
    @Override
    public ArrayList<Product> sortByPrice(Boolean increasing) {
        Comparator<Product> comp = new Comparator<Product>(){
            @Override
            public int compare(Product o1, Product o2){
                if(o1.getPrice() < o2.getPrice()) return -1;
                else if(o1.getPrice() > o2.getPrice()) return 1;
                else return 0;
            }
        };

        
        Sort.quickSort(searchedProducts, comp);
        if(!increasing) Collections.reverse(searchedProducts);
        return searchedProducts;
    }

    /**
     * Sort searched products by discount rate.
     * @param increasing if list is going to be in increasing order.
     * @return sorted arraylist.
     */
    @Override
    public ArrayList<Product> sortByDiscount(Boolean increasing) {
        Comparator<Product> comp = new Comparator<Product>(){
            @Override
            public int compare(Product o1, Product o2){
                if(calculateDiscount(o1.getDiscount(), o1.getPrice()) < calculateDiscount(o2.getDiscount(),o2.getPrice())) return -1;
                else if(calculateDiscount(o1.getDiscount(), o1.getPrice()) > calculateDiscount(o2.getDiscount(),o2.getPrice())) return 1;
                else return 0;
            }
        };

        Sort.mergeSort(searchedProducts, comp);
        if(!increasing) Collections.reverse(searchedProducts);
        return searchedProducts;
    }
    
    /**
     * Calculate discount rate.
     * @param discountPrice discounted price.
     * @param price original price.
     * @return rate 
     */
    @Override
    public double calculateDiscount(double discountPrice, double price){
        return(((price - discountPrice) / price) * 100);
    }

    /**
     * Sort searched products by name.
     * @param increasing if list is going to be in increasing order.
     * @return sorted arraylist.
     */
    @Override
    public ArrayList<Product> sortByName(Boolean increasing) {
        Comparator<Product> comp = new Comparator<Product>(){
            @Override
            public int compare(Product o1, Product o2){
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        };

        Sort.insertionSort(searchedProducts, comp);
        if(!increasing) Collections.reverse(searchedProducts);
        return searchedProducts;
    }

    /**
     * Filter products within the range.
     * @param upper upper range
     * @param lower lower range
     * @return filtered arraylist
     */
    @Override
    public ArrayList<Product> filterByPrice(double upper, double lower){
        ArrayList<Product> filtered = new ArrayList<>();
        for (Product product : searchedProducts) {
            if(product.getPrice() <= upper && product.getPrice() >= lower){
                filtered.add(product);
            }
        }  

        return filtered;
    }

    /**
     * Display products of a trader.
     * @param traderName the trader to display products to.
     * @return trader's products' list
     * @throws NullPointerException if traderName is null.
     */
    @Override
    public ArrayList<Product> displayProduct(String traderName) throws NullPointerException{
        ArrayList<Product> filtered = new ArrayList<>();
        if(traderName == null) throw new NullPointerException();
        File file = new File("files/e-commerce-samples.txt");
        if(!file.exists()) return filtered;
        
        try {
            Scanner reader = new Scanner(file);
            
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                String name = FileHelper.trimLine(data, 7);
                if(name.equals(traderName)){
                    String id = FileHelper.trimLine(data, 1);
                    String prname = FileHelper.trimLine(data, 2);
                    Category cg = FileHelper.trimCategory(FileHelper.trimLine(data, 3));
                    Double price = Double.parseDouble(FileHelper.trimLine(data, 4));
                    Double dc = Double.parseDouble(FileHelper.trimLine(data, 5));
                    String description = FileHelper.trimLine(data, 6);

                    filtered.add(new Product(id, cg, prname, description, price, dc, name));
                }
            }

            reader.close();
        } 
        
        catch (FileNotFoundException e) {
            System.err.println("File does not exist.\n");
        }

        return filtered;
    }
}
