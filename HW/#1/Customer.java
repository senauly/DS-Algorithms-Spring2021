import java.io.*;
import java.util.NoSuchElementException;
public class Customer extends Person implements FurnitureCustomer{
    private String adress;
    private String phoneNumber;
    private int customerNo;
    private Branch branch;
    private MyArray<Order> orders;

    public Customer(String name, String surname, String email, Branch branch){
        super(name,surname,email);
        this.branch = branch;
        orders = new MyArray<>();
    }

    public void setCustomerNo(int num){
        customerNo = num;
    }

    public void setCustomerNo(){
        this.customerNo = generateCustomerNo();
    }

    public MyArray<Order> getPreviousOrders(){
        return orders;
    }
/** Generate customer number according to company, branch and customer order number.
 * @return customer number
*/
    public int generateCustomerNo(){
        String company = "01";
        String branchNo;
        String rank;
        if(getBranch().getBranchNum() > 9 && getBranch().getAllCustomers().getSize()+1 > 9){
            branchNo = String.valueOf(getBranch().getBranchNum());
            rank = String.valueOf(getBranch().getAllCustomers().getSize()+1);
        }

        else{
            branchNo = "0" + String.valueOf(getBranch().getBranchNum());
            rank = "0" + String.valueOf(getBranch().getAllCustomers().getSize()+1);

        }
        
        int custNo = Integer.parseInt(company + branchNo + rank);
        
        if(!(getBranch().customerDoesExist(custNo))){
            custNo = changeCustumerNo(custNo);
            if(custNo == -1){
                getBranch().getAllCustomers().setCapacity(getBranch().getAllCustomers().getCapacity()*2);
            }
        }

        return custNo;
    }

/** Update customer no if it is exist before.
 * @param custNo to change
 * @return new customer no
*/
    public int changeCustumerNo(int custNo){
        for (int i = 1; i < getBranch().getAllCustomers().getCapacity(); i++){
            custNo += i;
            if(!(getBranch().customerDoesExist(custNo))) return custNo;
        }

        return -1;
    }

    public Branch getBranch(){
        return branch;
    }

    public int getCustomerNo() {
        return customerNo;
    }
/** Register to the system.
 * @param password of customer
 * @return true if succesfully registered.
 * @throws IllegalArgumentException if already registered.
*/
    public Boolean register(String password) throws IOException{
        if(!(getBranch().getCompany().isRegistered(this))){
            setCustomerNo();
            setPassword(password);
            getBranch().getAllCustomers().add(this);
            FileWriter writer = new FileWriter("customerList.txt", true);
            writer.write(this.getBranch().getBranchNum() + "\n" + this.getName() + "\n" + this.getSurname() + "\n"  + 
                            this.getEmail() + "\n" + this.getCustomerNo() + "\n\n");
            writer.close();
            return true;
        }
        
        else throw new IllegalArgumentException("Customer is already registered.\n");
    }
/** Search product with given parameters.
 * @param name product name
 * @param color product color
 * @param model product model
 * @return branchNum of where product is.
*/
    public int searchProduct(String name, String color, String model){
        for (int i = 0; i < getBranch().getCompany().getAllBranches().getSize(); i++) {
            if(getBranch().getCompany().getAllBranches().at(i).findProduct(name, color, model)) return getBranch().getCompany().getAllBranches().at(i).getBranchNum();
        }

        return -1;
    }
/** See all products of the company.
*/
    public void seeProducts(){
        getBranch().refreshStock();
        for (int j = 0; j < getBranch().getCompany().getAllBranches().getSize(); j++){
            for (int i = 0; i < getBranch().getCompany().getAllBranches().at(j).getStock().getSize(); i++){
                System.out.println("Product Name: " + getBranch().getCompany().getAllBranches().at(j).getStock().at(i).getName());
                System.out.println("Product Color: " + getBranch().getCompany().getAllBranches().at(j).getStock().at(i).getColor());
                System.out.println("Product Model: " + getBranch().getCompany().getAllBranches().at(j).getStock().at(i).getModel());
                System.out.println("Branch No: " + getBranch().getCompany().getAllBranches().at(j).getBranchNum());
                System.out.println("Stock: " + getBranch().getCompany().getAllBranches().at(j).howManyProduct(getBranch().getStock().at(i)));
                System.out.println();
            }
        }
    }
/** Find where the specific product is.
 * @param type product name
 * @param color product color
 * @param model product model
 * @return branch where product exist.
 * @throws NoSuchElementException when there's no such product.
 * @throws IOException when error occurs while reading the file.
*/
    public Branch whereIsTheProduct(String type, String color, String model) throws IOException, NoSuchElementException{
        FileReader file = new FileReader("stockList.txt");
        BufferedReader read = new BufferedReader(file);
        String line = null;
        int count = 0;
        int found = 0;
        int branchNo = -1;
        
        while((line = read.readLine()) != null){
            count ++;
            if(count == 1){
                branchNo = Integer.parseInt(line);
            }
            
            if(count == 2 && type.equals(line)) found++;
            if(count == 3 && model.equals(line)) found++;
            if(count == 4 && color.equals(line)) found++;
            if(count == 5) found = count = 0;
            if(found == 3 && branchNo != -1){
                read.close();
                return(getBranch().getCompany().findBranch(branchNo));
            }

            if(found == 3 && branchNo == -1){
                read.close();
                throw new IOException("Error occured while reading the file.\n");
            }
        }
        
        read.close();
        throw new NoSuchElementException("Product is out of stock.");
    }
/** Override login function for customer to login the system.
 * @param email of customer
 * @param password of customer
 * @return true if succesfully logined.
*/
    @Override
    public Boolean login(String email, String password){
        Customer willLogin = null;

        if((willLogin = getBranch().findCustomer(email)) == null){
            System.out.println("No subscription found, please register.");
            return false;
        }

        else if(!(willLogin.getPassword().equals(password))){
            System.out.println("Incorrect password.");
            return false;
        }

        else if((willLogin = getBranch().findCustomer(email)) != null && willLogin.getPassword().equals(password)){
            System.out.println("You have succesfully logined.");
        }

        return true;
    }
/** View previous orders of the customer.
*/
    public void viewPreviousOrders(){
        for (int i = 0; i < getPreviousOrders().getSize(); i++) {
            System.out.println("\n");
            System.out.println(getPreviousOrders().at(i).toString()); 
        }
    }

    public void setAdress(String adress){
        this.adress = adress;
    }

    public void setPhone(String phone){
        this.phoneNumber = phone;
    }

    public String getPhone(){
        return phoneNumber;
    }

    public String getAdress(){
        return adress;
    }

/** Shop online by entering adress and phone.
 * @param adress of customer
 * @param phone of customer
 * @param product to buy
*/
    public void shopOnline(String adress, String phone, Product product){
        Branch br;
        try{
            br = whereIsTheProduct(product.getName(), product.getColor(), product.getModel());
            setAdress(adress);
            setPhone(phone);
            try{
                br.getAllProducts().remove(product);
            }

            catch(NoSuchElementException e){
                e.getMessage();
            }
            
            getPreviousOrders().add(new Order(product));
        }

        catch(NoSuchElementException e){
            e.getMessage();
        }

        catch(IOException e){
            e.getMessage();
        }
    }

    @Override
    public String toString() { 
        return("Branch No:" + getBranch().getBranchNum() + "\n" + "Name: " + getName() + "\n" + "Surname: " + getSurname() + "\n" + 
                "Email: " + getEmail() + "\n" + "Customer No:" + getCustomerNo()); 
    }

    @Override
    public boolean equals(Object o) { 
        if (o == this) { 
            return true; 
        } 
        if (!(o instanceof Customer)){ 
            return false; 
        } 
        
        Customer c = (Customer) o; 
        return(c.getEmail().equals(getEmail()));
    } 
}

/*

Person p = new Customer();
p.login();
