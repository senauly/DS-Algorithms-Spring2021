import java.util.Iterator;

public class Branch implements FurnitureBranch {
    private Company company;
    private int branchNum;
    private KWArrayList<Employee> employees;
    private KWArrayList<Customer> customers;
    private HybridList<Product> products;
    private HybridList<Product> stock;

    public Branch(Company comp){
        company = comp;
        employees = new KWArrayList<>();
        customers = new KWArrayList<>();
        products = new HybridList<>();
        stock = new HybridList<>();
    }
    
    public void setBranchNum(){
        branchNum = generateBranchNum();
    }

    public void setBranchNum(int branchNum) throws UnsupportedOperationException{
        if((getCompany().findBranch(branchNum) != null)) this.branchNum = branchNum;
        else throw new UnsupportedOperationException("This branch no already exist.\n");
    }

    /** Generate unique branch no.
 * @return branch No
*/
    public int generateBranchNum(){
        int result = -1;
        for (int i = 1; i < getCompany().getAllBranches().size(); i++){
            if((getCompany().findBranch(i) == null)) result = i;
        }

        if(result != -1) return result;
        return getCompany().getAllBranches().size()+1;
    }

    public Company getCompany(){
        return company;
    }

/** Check if specific customer exist.
 * @param customerNo of costumer
 * @return true if exist.
*/
    public Boolean customerDoesExist(int customerNo){
        int i;
        for (i = 0; i < getAllCustomers().size() ; i++) {
            if(getAllCustomers().get(i).getCustomerNo() == customerNo) return true;
        }
        return(false);

    }

    public Employee getEmployee(int employeeNum){
        return employees.get(employeeNum);
    }

    public KWArrayList<Employee> getAllEmployee(){
        return employees;
    }

    public HybridList<Product> getAllProducts(){
        return products;
    }

    public HybridList<Product> getStock(){
        return stock;
    }

    public KWArrayList<Customer> getAllCustomers(){
        return customers;
    }

    public int getBranchNum(){
        return branchNum;
    }

/** Find customer by email.
 * @param email of customer
 * @return customer if found, else null.
*/
    public Customer findCustomer(String email){
        for (int i = 0; i < getAllCustomers().size(); i++) {
            if(getAllCustomers().get(i).getEmail().equals(email)) return getAllCustomers().get(i);
        }

        return null;
    }

    /** Find given product
     * @param name product name
     * @param color product color
     * @param model product model
     * @return true if found
    */
    public Boolean findProduct(String name, String color, String model){
        Product searched = new Product(name, color, model);
        return(getAllProducts().contains(searched));
    }

    /** Find employee by email.
     * @param email of employee
     * @return employee if found, else null.
    */
    public Employee findEmployee(String email){
        for (int i = 0; i < getAllEmployee().size(); i++) {
            if(getAllEmployee().get(i).getEmail().equals(email)) return getAllEmployee().get(i);
        }
        return null;
    }

    /** Add products to stock if it does not exist before.
    */
    public void refreshStock(){
        Iterator<Product> i = getAllProducts().iterator();
        while(i.hasNext()){
            Product item = i.next();
            if(!(getStock().contains(item))){
                getStock().add(item);
            }
        }
    }

    /** Count how many pieces a product have
     * @param product to count
     * @return number of product
    */
    public int howManyProduct(Product product){
        int result = 0;
        Iterator<Product> i = getAllProducts().iterator();
        while(i.hasNext()){
            Product item = i.next();
            if(item.equals(product)){
                result++;
            }
        }

        return(result);
    }

    @Override
    public String toString() { 
        return("Branch No: " + branchNum); 
    }

    @Override
    public boolean equals(Object o) { 
        if (o == this) { 
            return true; 
        } 
        if (!(o instanceof Branch)){ 
            return false; 
        } 
        
        Branch b = (Branch) o; 
        return(b.getBranchNum() == getBranchNum());
    } 
}
