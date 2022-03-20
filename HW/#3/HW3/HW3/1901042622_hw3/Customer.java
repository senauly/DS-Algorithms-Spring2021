import java.util.ListIterator;
import java.util.Iterator;
public class Customer extends Person implements FurnitureCustomer{
    private String adress;
    private String phoneNumber;
    private int customerNo;
    private Branch branch;
    private HybridList<Order> orders;

    public Customer(String name, String surname, String email, Branch branch){
        super(name,surname,email);
        this.branch = branch;
        orders = new HybridList<>();
    }

    public void setCustomerNo(int num){
        customerNo = num;
    }

    public void setCustomerNo(){
        this.customerNo = generateCustomerNo();
    }

    public HybridList<Order> getPreviousOrders(){
        return orders;
    }
/** Generate customer number according to company, branch and customer order number.
 * @return customer number
*/
    public int generateCustomerNo(){
        String company = "01";
        String branchNo;
        String rank;
        if(getBranch().getBranchNum() > 9 && getBranch().getAllCustomers().size()+1 > 9){
            branchNo = String.valueOf(getBranch().getBranchNum());
            rank = String.valueOf(getBranch().getAllCustomers().size()+1);
        }

        else{
            branchNo = "0" + getBranch().getBranchNum();
            rank = "0" + getBranch().getAllCustomers().size()+1;

        }
        
        return(Integer.parseInt(company + branchNo + rank));
    }

    /** Update customer no if it is exist before.
     * @param custNo to change
     * @return new customer no
    */
    public int changeCustumerNo(int custNo){
        for (int i = 1; i < getBranch().getAllCustomers().size(); i++){
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
    public Boolean register(String email, String password){
        if(getBranch().getCompany().findCustomer(email) != null){
            return false;
        }
    
        setCustomerNo();
        setPassword(password);
        return true;
    }
/** Search product with given parameters.
 * @param name product name
 * @param color product color
 * @param model product model
 * @return branchNum of where product is.
*/
    public int searchProduct(String name, String color, String model){
        ListIterator<Branch> i = getBranch().getCompany().getAllBranches().listIterator();
        while(i.hasNext()){
            Branch br = i.next();
            br.refreshStock();
            if(br.findProduct(name, color, model)){
                return(br.getBranchNum());
            }
        }

        return -1;
    }
/** See all products of the company.
*/
    public void seeProducts(){
        Branch br = null;
        getBranch().refreshStock();
        ListIterator<Branch> j = getBranch().getCompany().getAllBranches().listIterator();
        while(j.hasNext()){
            br = j.next();
            br.refreshStock();
            Iterator<Product> i = br.getStock().iterator();
            while(i.hasNext()){
                Product pr = i.next();
                System.out.println("Product Name: " + pr.getName());
                System.out.println("Product Color: " + pr.getColor());
                System.out.println("Product Model: " + pr.getModel());
                System.out.println("Branch No: " + br.getBranchNum());
                System.out.println("Stock: " + br.howManyProduct(pr));
                System.out.println();
            }
        }
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
        Iterator<Order> i = getPreviousOrders().iterator();
        while(i.hasNext()){
            System.out.println("\n");
            Order or = i.next();
            System.out.println(or.toString()); 
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
 * @return true if shopping is succesful.
*/
    public Boolean shopOnline(String adress, String phone, Product product){
        Branch br = getBranch().getCompany().findProduct(product);
        if(br == null) return false;

        setAdress(adress);
        setPhone(phone);
        br.getAllProducts().remove(product);
        getPreviousOrders().add(new Order(product));
        return true;
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
