import java.io.*;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ListIterator;
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
    public Product addProduct(String type, String color, String model){
        Product add = new Product(type, color, model);
        getBranch().getAllProducts().add(add);
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
*/
    public void makeSale(String name, String color, String model, Customer customer) throws NoSuchElementException{
        Product product = new Product(name, color, model);
        Boolean inBranch = getBranch().getAllProducts().contains(product);
        Boolean inStorage = getBranch().getCompany().getStorage().contains(product);
        
        if(!(getBranch().getAllCustomers().contains(customer))){
            registerCustomer(customer.getName(), customer.getSurname(), customer.getEmail(), customer.getPassword());
        }
        
        if(!inBranch && !inStorage){
            informManager(product);
            throw new NoSuchElementException();
        }

        if(inBranch) getBranch().getAllProducts().remove(product);
        else if(inStorage) getBranch().getCompany().getStorage().remove(product);
        updatePreviousOrders(product,customer);
    }
/** See if given product is in stock.
 * @param product to check
 * @return true if product exist
*/
    public Boolean inquireStock(Product product){
        Iterator<Product> i = getBranch().getAllProducts().iterator();
        while(i.hasNext()){
            Product pr = i.next();
            if(pr.equals(product)) return true;
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
    */
    public Customer registerCustomer(String name, String surname, String email, String password) throws IllegalArgumentException{
        Customer newCustomer = new Customer(name, surname, email,getBranch());
        newCustomer.setPassword(password);
        if(getBranch().getCompany().findCustomer(email) == null ){
            newCustomer.setCustomerNo();
            getBranch().getAllCustomers().add(newCustomer);
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
        for (int i = 0; i < getBranch().getAllCustomers().size(); i++) {
            if(getBranch().getAllCustomers().get(i).getCustomerNo() == customerNum){
                System.out.println(getBranch().getAllCustomers().get(i).toString());
                System.out.println("Previous Orders: ");
                getBranch().getAllCustomers().get(i).viewPreviousOrders();
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
