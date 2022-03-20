import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;

public class ShoppingTrader implements Trader{
    private String traderName;
    private String password;
    private String ID;
    private Queue<Order> orders;
    private HashMap<String,Product> products;

    public ShoppingTrader(String traderName, String password, String ID) {
        products = new HashMap<>();
        orders = new ArrayDeque<>();
        this.traderName = traderName;
        this.password = password;
        this.ID = ID;
    }

    protected ShoppingTrader() {
        products = new HashMap<>();
        orders = new ArrayDeque<>();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShoppingTrader(String traderName) {
        this.traderName = traderName;
    }

    public String getTraderName() {
        return this.traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    /**
     * add product to the system
     * @param productName name
     * @param category category
     * @param price price
     * @param discountedPrice discounted price
     * @param description description
     * @throws NullPointerException is name,category or description null
     * @return true if added, false otherwise
     */
    @Override
    public boolean addProduct(String productName, Category category, double price, double discountedPrice, String description) throws NullPointerException{
        if(productName == null || category == null || description == null) throw new NullPointerException();
        File file = new File("files/e-commerce-samples.txt");
        File newFile = new File("files/tempFile.txt");
        
        try {
            Scanner reader = new Scanner(file);
            FileWriter writer = new FileWriter(newFile);
            Boolean addCompleted = false;
            String id = IDGenerator.generateID(new Product(), ShoppingApplication.getProductCount());
            int i = 0;
     
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String name = FileHelper.trimLine(data,2);
                if(name == null) throw new NullPointerException();
    
                if(!addCompleted){
                    if(name.compareTo(productName) <= 0){ 
                        if(i != 0) writer.append("\n");
                        writer.append(data);
                    }
                    
                    if(name.compareTo(productName) > 0){
                        if(i != 0) writer.append("\n");
                        writer.append(FileHelper.createLine(id, productName, category, price, discountedPrice, description, getTraderName()));
                        addCompleted = true;
                        if(i != 0) writer.append("\n");
                        writer.append(data);
                    }
                }
    
                else{
                    if(i != 0) writer.append("\n");
                    writer.append(data);
                }
    
                i++;
            }
    
            ShoppingApplication.increaseProductCount();
            writer.close();
            reader.close();
            file.delete();
            newFile.renameTo(file);
        
        } 
        
        catch (IOException e) {
            System.err.println("Error occured while reading/writing from/to file.\n");
        }

        return true;
    }

    /**
     * remove product from the system
     * @param productID id
     * @return true if removed, false otherwise
     */
    @Override
    public boolean removeProduct(String productID){
        File file = new File("files/e-commerce-samples.txt");
        File newFile = new File("files/tempFile.txt");
        boolean removed = false;
        int i = 0;
        
        try {
            Scanner reader = new Scanner(file);
            FileWriter writer = new FileWriter(newFile);
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                if(i != 0 ) writer.append("\n");
                writer.append(data);
                
                if(FileHelper.trimLine(data,1).equals(productID)){
                    reader.close();
                    removed = true;
                }

                i++;
            }
            
            writer.close();
            reader.close();
            return removed;
        }
        
        catch (IOException e) {
            System.err.println("Error occured while reading/writing from/to file.\n");
        }
     
        return false;
    }
    
    /**
     * Get all products of this trader
     * @return hashMap of the products.
     */
    @Override
    public HashMap<String,Product> getProducts(){
        File file = new File("files/e-commerce-samples.txt");
        if(!file.exists()) return products;
        
        try {
            Scanner reader = new Scanner(file);
            
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                String name = FileHelper.trimLine(data, 7);
                if(name.equals(this.traderName)){
                    String id = FileHelper.trimLine(data, 1);
                    String prname = FileHelper.trimLine(data, 2);
                    Category cg = FileHelper.trimCategory(FileHelper.trimLine(data, 3));
                    Double price = Double.parseDouble(FileHelper.trimLine(data, 4));
                    Double dc = Double.parseDouble(FileHelper.trimLine(data, 5));
                    String description = FileHelper.trimLine(data, 6);

                    products.put(FileHelper.trimLine(data, 1), new Product(id, cg, prname, description, price, dc, name));
                }
            }

            reader.close();

        } 
        
        catch (FileNotFoundException e) {
            System.err.println("File does not exist.\n");
        }

        return products;
    }

    /**
     * Display orders of the trader, by the adding date.
     * @return queue of the orders
     */
    @Override
    public Queue<Order> displayOrders() {
        File file = new File("files/orders.txt");
        if(!file.exists()) return orders;
        
        try {
            Scanner reader = new Scanner(file);
            
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                String name = FileHelper.trimLine(data, 2);

                if(name.equals(this.traderName)){
                    orders.add(new Order(FileHelper.trimLine(data, 1), this.traderName, products.get(FileHelper.trimLine(data, 3))));
                }
            }

        } 
        
        catch (FileNotFoundException e) {
            System.err.println("File does not exist.\n");
        }

        return orders;
    }

    /**
     * Change order status, deny or accept.
     * @param accepted if true accept, deny otherwise
     * @return true if orders is not empty.
     */
    @Override
    public boolean changeOrder(Boolean accepted) {
        if(orders.isEmpty()) return false;
        
        File file = new File("files/orders.txt");
        if(!file.exists()) return false;

        File newFile = new File("files/temp.text");
        int i = 0;
        try {
            Scanner reader = new Scanner(file);
            FileWriter writer = new FileWriter(file);
            
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                if(i != 0) writer.append("\n");
                writer.append(data);
                
                if(FileHelper.trimLine(data, 3).equals(orders.peek().getProduct().getID())){
                    orders.poll();
                    StringBuilder sb = new StringBuilder();
                    sb.append(FileHelper.trimLine(data, 1) + ";" + FileHelper.trimLine(data, 2) + ";" + FileHelper.trimLine(data, 3) + ";");
                    if(accepted){
                        removeProduct(FileHelper.trimLine(data, 3));
                        sb.append(Order.status.ACCEPTED);
                    }
                    else sb.append(Order.status.DENIED);
                    writer.append(sb);
                }

                i++;
            }

            writer.close();
            reader.close();
            file.delete();
            newFile.renameTo(file);
    
        } 
        
        catch (IOException e) {
            System.err.println("Error occured while reading/writing from/to file.\n");
        }

        return true;

    }

    /**
     * Accept the order which is in the head of the queue and also remove from the file.
     * @return true if accepted
     */
    @Override
    public boolean meetOrder(){
        return(changeOrder(true));
    }

    /**
     * Cancel the order which is in the head of the queue.
     * @return true if cancelled
     */
    @Override
    public boolean cancelOrder(){
        return(changeOrder(false));
    }


    @Override
    public String toString() {
        return " traderName='" + getTraderName() + "'" +
            ", password='" + getPassword() + "'" +
            ", ID='" + getID() + "'";
    }

}
