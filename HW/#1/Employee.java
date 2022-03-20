import java.io.*;
import java.util.NoSuchElementException;
public class Employee extends Person implements FurnitureEmployee{
    Branch branch;
    public Employee(String name, String surname, Branch branch, String email, String password){
        super(name, surname, email);
        setPassword(password);
        this.branch = branch;
    }

    public Branch getBranch(){
        return branch;
    }
/** Override login function for employee to login the system.
 * @param email of employee
 * @param password of employee
 * @return true if succesfully logined.
*/
    @Override
    public Boolean login(String email, String password){
        Employee willLogin = null;

        if((willLogin = getBranch().findEmployee(email)) == null){
            System.out.println("There is no employee registered with this email.");
            return false;
        }

        else if(!(willLogin.getPassword().equals(password))){
            System.out.println("Incorrect password.");
            return false;
        }

        else if((willLogin = getBranch().findEmployee(email)) != null && willLogin.getPassword().equals(password)){
            System.out.println("You have succesfully logined.");
        }

        return true;
    }
/** Add product to branch.
 * @return added product
*/
    public Product addProduct(String type, String color, String model) throws IOException{
        Product add = new Product(type, color, model);
        getBranch().getAllProducts().add(add);
        FileWriter writer = new FileWriter("stockList.txt", true);
        writer.write(this.getBranch().getBranchNum() + "\n" + add.getName() + "\n" + add.getModel() + "\n" + add.getColor() + "\n\n");
        writer.close();
        return add;
    }
/** Remove product if it exist.
 * @return true if removed.
*/
    public Boolean removeProduct(String type, String color, String model){
        Product temp = new Product(type, color, model);
        return(getBranch().getAllProducts().remove(temp));
    }
/** Sell product to the given customer.
 * @throws NoSuchElementException when there's no such product.
 * @throws IOException when error occurs while reading the file.
*/
    public void makeSale(String name, String color, String model, Customer customer) throws IOException, NoSuchElementException{
        if(!(getBranch().getAllCustomers().contains(customer))){
            registerCustomer(customer.getName(), customer.getSurname(), customer.getEmail(), customer.getPassword());
        }

        Product product = new Product(name, color, model);
        if((getBranch().getAllProducts().find(product) == null) && getBranch().getCompany().getStorage().find(product) == null){
            informManager(product);
            throw new NoSuchElementException("Product is out of stock. Manager informed.\n");
        }

        if(getBranch().getAllProducts().find(product) != null) getBranch().getAllProducts().remove(product);
        else if(getBranch().getCompany().getStorage().find(product) != null) getBranch().getCompany().getStorage().remove(product);
        updatePreviousOrders(product,customer);
    }
/** See if given product is in stock.
 * @param product to check
 * @return true if product exist
*/
    public Boolean inquireStock(Product product){
        for (int i = 0; i < getBranch().getAllProducts().getSize(); i++){
            if(getBranch().getAllProducts().at(i).equals(product)) return true;
        }
        
        informManager(product);
        return false;
    }

 /** Inform manager about lack of product.
 * @param product to check
*/   
    public void informManager(Product product){
        getBranch().getCompany().admin().setStockShortage(true);
        getBranch().getCompany().admin().getToRenewedProducts().add(product);
    }
/** Register teh customer.
 * @return customer if succesfully registered.
 * @throws IllegalArgumentException if already registered.
 * @throws IOException when error occurs while reading the file.
*/
    public Customer registerCustomer(String name, String surname, String email, String password) throws IOException, IllegalArgumentException{
        Customer newCustomer = new Customer(name, surname, email,getBranch());
        newCustomer.setPassword(password);
        if(!(getBranch().getCompany().isRegistered(newCustomer))){
            newCustomer.setCustomerNo();
            getBranch().getAllCustomers().add(newCustomer);
            FileWriter writer = new FileWriter("customerList.txt", true);
            writer.write(newCustomer.getBranch().getBranchNum() + "\n" + newCustomer.getName() + "\n" + newCustomer.getSurname() + "\n"  + 
                         newCustomer.getEmail() + "\n" + newCustomer.getCustomerNo() + "\n\n");
            writer.close();
        }

        else throw new IllegalArgumentException("Customer is already registered.\n");
        return newCustomer;
    }
/** Acces the specific customer info.
 * @param customerNum of customer's.
 * @throws NoSuchElementException if customer does not exist.
*/
    public void accesCustomerInfo(int customerNum) throws NoSuchElementException{
        Boolean found = false;
        for (int i = 0; i < getBranch().getAllCustomers().getSize(); i++) {
            if(getBranch().getAllCustomers().at(i).getCustomerNo() == customerNum){
                System.out.println(getBranch().getAllCustomers().at(i).toString());
                found = true;
                break;
            }
        }

        if(!found) throw new NoSuchElementException("Customer does not exist.\n");
    }

/** Add new order to customer previous orders.
*/
    public void updatePreviousOrders(Product product, Customer customer){
        Order newOrder = new Order(product);
        customer.getPreviousOrders().add(newOrder);
    }

    @Override
    public String toString() { 
        return("Name: " + getName() + "\n" + "Surname: " + getSurname() + "\n" + "Branch No:" + getBranch().getBranchNum() + "\n" + "Email:" + getEmail() + "\n\n");
    } 

    public boolean equals(Object o) { 
        if (o == this) { 
            return true; 
        } 
        if (!(o instanceof Employee)){ 
            return false; 
        } 
        
        Employee e = (Employee) o; 
        return(e.getEmail().equals(getEmail()));
    } 
}
