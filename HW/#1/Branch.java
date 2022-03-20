public class Branch implements FurnitureBranch {
    private Company company;
    private int branchNum;
    private MyArray<Employee> employees;
    private MyArray<Customer> customers;
    private MyArray<Product> products;
    private MyArray<Product> stock;

    public Branch(Company comp){
        company = comp;
        employees = new MyArray<>(100);
        customers = new MyArray<>(500);
        products = new MyArray<>(1000);
        stock = new MyArray<>(100);
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
        for (int i = 1; i < getCompany().getAllBranches().getCapacity(); i++){
            if((getCompany().findBranch(i) == null)) result = i;
        }

        if(result != -1) return result;
        return getCompany().getAllBranches().getSize()+1;
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
        for (i = 0; i < getAllCustomers().getSize() ; i++) {
            if(getAllCustomers().at(i).getCustomerNo() == customerNo) return true;
        }

        return(false);

    }

    public Employee getEmployee(int employeeNum){
        return employees.at(employeeNum);
    }

    public MyArray<Employee> getAllEmployee(){
        return employees;
    }

    public MyArray<Product> getAllProducts(){
        return products;
    }

    public MyArray<Product> getStock(){
        return stock;
    }

    public MyArray<Customer> getAllCustomers(){
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
        for (int i = 0; i < getAllCustomers().getSize(); i++) {
            if(getAllCustomers().at(i).getEmail().equals(email)) return getAllCustomers().at(i);
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
        for (int i = 0; i < getAllProducts().getSize(); i++){
            if(getAllProducts().at(i).equals(searched)) return true;
        }

        return false;
    }
/** Find employee by email.
 * @param email of employee
 * @return employee if found, else null.
*/
    public Employee findEmployee(String email){
        for (int i = 0; i < getAllEmployee().getSize(); i++) {
            if(getAllEmployee().at(i).getEmail().equals(email)) return getAllEmployee().at(i);
        }

        return null;
    }

/** Add products to stock if it does not exist before.
*/
    public void refreshStock(){
        for (int i = 0; i < getAllProducts().getSize(); i++){
            if(!(getStock().contains(getAllProducts().at(i)))){
                getStock().add(getAllProducts().at(i));
            }
        }
    }

/** Count how many pieces a product have
 * @param product to count
 * @return number of product
*/
    public int howManyProduct(Product product){
        int result = 0;
        for (int i = 0; i < getAllProducts().getSize(); i++){
            if(getAllProducts().at(i).equals(product)){
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
