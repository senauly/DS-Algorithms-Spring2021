import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ShoppingApplication implements Application{
    private static int customerCount = 0;
    private static int traderCount = 0;
    private static int productCount = 0;
    private final String PASSWORD = "123456";
    private HashMap<String,Trader> traders;
    private HashMap<String,Customer> customers;


    public ShoppingApplication(){
        traders = new HashMap<>();
        customers = new HashMap<>();
        register();
    }

    public static int getCustomerCount() {
        return customerCount;
    }

    public static int getTraderCount() {
        return traderCount;
    }

    public static int getProductCount() {
        return productCount;
    }

    public static void increaseProductCount(){
        productCount++;
    }

    public HashMap<String,Trader> getTraders() {
        return this.traders;
    }

    public HashMap<String,Customer> getCustomers() {
        return this.customers;
    }
    /**
     * Search ID according to file. 
     * @param filePath file path
     * @param ID to find
     * @return true if exist, false otherwise
     */
    public static boolean searchID(String filePath, String ID){
        File file = new File(filePath);
        Scanner reader;
        try {
            reader = new Scanner(file);
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                if(FileHelper.trimLine(data,1).equals(ID)){
                    reader.close();
                    return true;
                }
            }
    
            reader.close();
        }
        
        catch (FileNotFoundException e) {
            System.err.println("File does not exist.\n");
        }
     
        return false;
    }

    /**
     * Login into the system.
     * @param userID id
     * @param password password
     * @return true if succesfull, false otherwise
     */
    public boolean login(String userID, String password){
        File file = new File("files/users.txt");
        Scanner reader;
        try{
            reader = new Scanner(file);
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                if(FileHelper.trimLine(data,1).equals(userID)){
                    String pass = FileHelper.trimLine(data,3);
                    if(pass.equals(password)){
                        reader.close();
                        return true;
                    }
                }
            }

            reader.close();
        } 
        
        catch (FileNotFoundException e) {
            System.err.println("File does not exist.\n");
        }
        
        return false;
    }

    /**
     * Register all traders to the system from the file. It is just a starter function.
     */
    public void register(){
        File file = new File("files/e-commerce-samples.txt");

        Scanner reader;
        try {
            reader = new Scanner(file);
            File newFile = new File("files/users.txt");
            FileWriter writer = new FileWriter(newFile);
            HashSet<String> trader = new HashSet<>(1000);
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                String traderName = FileHelper.trimLine(data,7);
                if(trader.add(traderName)){
                    if(traderCount != 0) writer.append("\n");
                    traderCount++;
                    String ID = IDGenerator.generateID(new ShoppingTrader(traderName), traderCount);
                    writer.append(ID + ";" + traderName + ";" + PASSWORD);
                    traders.put(ID, new ShoppingTrader(traderName,PASSWORD,ID));
                }

                productCount++;
            }

            writer.close();
            reader.close();
        } 
        
        catch (IOException e){
            System.err.println("Error occured while reading/writing from/to file.\n");
        }
    }

    /**
     * Register users to the system.
     * @param o object to represent trader or customer.
     * @param password password
     * @param name username
     * @throws NullPointerException if name or password is null
     * @throws UnsupportedOperationException if object is other than trader or customer
     * @return registered user if succesfull, null otherwise.
     */
    public User register(Object o, String password, String name) throws NullPointerException, UnsupportedOperationException{
        if(password == null || name == null) throw new NullPointerException();
        if(password.length() != 6) return null;
        File file = new File("files/users.txt");
        User registered = null;

        Scanner reader;
        try {
            reader = new Scanner(file);
            FileWriter writer = new FileWriter(file, true);
            String id;
            if(o instanceof Customer){
                customerCount++;
                id = IDGenerator.generateID(new ShoppingCustomer(),customerCount);
                customers.put(id, new ShoppingCustomer(name, password, id));
                registered = new ShoppingCustomer(name, password, id);
            }
            else if(o instanceof Trader){
                traderCount++;
                id = IDGenerator.generateID(new ShoppingTrader(),traderCount);
                traders.put(id, new ShoppingTrader(name, password, id));
                registered = new ShoppingTrader(name, password, id);
            }
            else{
                writer.close();
                reader.close();
                throw new UnsupportedOperationException();
            }

            writer.append("\n" + id + ";" + name + ";" + password);
            writer.close();
            reader.close();
        } 
        
        catch (IOException e) {
            System.err.println("Error occured while reading/writing from/to file.\n");
        }

        return registered;
    }
}
