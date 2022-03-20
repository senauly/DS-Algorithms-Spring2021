import java.io.*;
/**
 * @author Sena Ulukaya 
 * This class created for the automation system.
 */

public class Company implements FurnitureCompany{
    private String companyName;
    private Administrator admin;
    private MyArray<Branch> branches;
    private MyArray<Product> storage;
    private File customerList;
    private static final String FILE_NAME_CUSTOMER = "customerList.txt";
    private File stockList;
    private static final String FILE_NAME_STOCK = "stockList.txt";
    
    public Company(String companyName, String adminName, String adminSurname){
        setCompanyName(companyName);
        admin = new Administrator(adminName, adminSurname,this);
        branches = new MyArray<>(5);
        storage = new MyArray<>(500);
        customerList = new File(FILE_NAME_CUSTOMER);
        stockList = new File(FILE_NAME_STOCK);
        try{
            if(customerList.createNewFile()) System.out.println("File created: " + customerList.getName());
            if(stockList.createNewFile()) System.out.println("File created: " + stockList.getName());
            addRegisteredCustomers();
            addOldStock();
        }

        catch(IOException exception){
            exception.getMessage();
        }
    }

    public void setCompanyName(String compName){
        this.companyName = compName;
    }

    public String getName(){
        return companyName;
    }

    public Branch getBranch(int branchNum){
        return findBranch(branchNum);
    }

    public MyArray<Branch> getAllBranches(){
        return branches;
    }

    public Administrator admin(){
        return admin;
    }

    public MyArray<Product> getStorage(){
        return storage;
    }

/** Add old customers from file to system.
 * @throws IOException if some error occur while dealing with the file.
*/
    public void addRegisteredCustomers() throws IOException{
        int count = 0;
        String line = null;
        FileReader file = new FileReader(FILE_NAME_CUSTOMER);
        BufferedReader read = new BufferedReader(file);
        int branchNo = -1;
        String color = null;
        String model = null;
        String type = null;
        Product newProduct = null;

        while((line = read.readLine()) != null){
            count ++;
            if(count == 1){
                branchNo = Integer.parseInt(line);
                admin().addBranch(branchNo);
            }
            
            if(count == 2) type = line;
            if(count == 3) model = line;
            if(count == 4) color = line;
            if(count == 5){
                count = 0;
                if(type != null && model != null && color != null && branchNo != -1 && newProduct != null){
                    newProduct = new Product(type, color, model);
                    getBranch(branchNo).getAllProducts().add(newProduct);
                }

                else{
                    read.close();
                    throw new IOException("Error occured while reading the file.\n");
                }
            }

        }

        read.close();
    }
/** Add products from file to system.
 * @throws IOException if some error occur while dealing with the file.
*/
    public void addOldStock() throws IOException{
        int count = 0;
        String line = null;
        FileReader file = new FileReader(FILE_NAME_CUSTOMER);
        BufferedReader read = new BufferedReader(file);
        int branchNo = -1;
        String name = null;
        String surname = null;
        String email = null;
        int customerNo = -1;
        Customer newCustomer = null;

        while((line = read.readLine()) != null){
            count ++;
            if(count == 1){
                branchNo = Integer.parseInt(line);
                admin().addBranch(branchNo);
            }
            if(count == 2) name = line;
            if(count == 3) surname = line;
            if(count == 4) email = line;
            if(count == 5) customerNo = Integer.parseInt(line);
            if(count == 6){
                count = 0;
                if(name != null && surname != null && email != null && customerNo != -1 && newCustomer != null){
                    newCustomer = new Customer(name, surname, email,getBranch(branchNo));
                    getBranch(branchNo).getAllCustomers().add(newCustomer);
                }

                else{
                    read.close();
                    throw new IOException("Error occured while reading the file.\n");
                }
            }

        }

        read.close();
    }

/** Find branch by branch number.
 * @param branchNo of branch
 * @return branch if it is found, else null.
*/
    public Branch findBranch(int branchNo){
        Branch temp = null;
        int i;
        for (i = 0; i < branches.getSize() ; i++) {
            if(getAllBranches().at(i).getBranchNum() == branchNo){
                temp = getAllBranches().at(i);
                break;
            }
        }
        return temp;
    }

    public File getCustomerList(){
        return customerList;
    }

/** Check if customer registered before.
 * @param customer to check
 * @return true if registered before
 * @throws IOException if some error occur while dealing with the file.
 * 
*/
    public Boolean isRegistered(Customer customer) throws IOException{
        int count = 0;
        String line = null;
        FileReader file = new FileReader(FILE_NAME_CUSTOMER);
        BufferedReader read = new BufferedReader(file);

        while((line = read.readLine()) != null){
            count ++;
            if(count == 6) count = 0;
            if(count == 3 && customer.getEmail().equals(line)){
                read.close();
                return true;
            }
        }

        read.close();

        return false;
    }

/** Find customer by email.
 * @param email od customer
 * @return customer if found, else null.
 * 
*/
    public Customer findCustomer(String email){
        Customer customer = null;
        for (int i = 0; i < getAllBranches().getSize(); i++){
            if((customer = getAllBranches().at(i).findCustomer(email)) != null){
                return customer;
            }
        }

        return null;
    }

/** Find employee by email.
 * @param email of customer
 * @return employee if found, else null.
 * 
*/
    public Employee findEmployee(String email){
        Employee employee = null;
        for (int i = 0; i < getAllBranches().getSize(); i++){
            if((employee = getAllBranches().at(i).findEmployee(email)) != null){
                return employee;
            }
        }
        
        return null;
    }

    @Override
    public String toString(){
        return("Company Name: " + companyName + "\n" + "Admin:\n" + admin().toString() + "\n");
    }
}
