import java.util.Iterator;
public class Administrator extends Person implements FurnitureAdministrator{
    private Company company;
    private Boolean stockShortage;
    private HybridList<Product> toRenewedProducts;
    
    public Administrator(String name, String surname, Company company){
        super(name,surname);
        this.company = company;
        setStockShortage(false);
        toRenewedProducts = new HybridList<>();
    }
/** Return company.
 * @return company
*/
    public Company getCompany(){
        return company;
    }

    public HybridList<Product> getToRenewedProducts(){
        return toRenewedProducts;
    }

    public void setStockShortage(Boolean stock){
        stockShortage = stock;
    }

    public Boolean getStockShortage(){
        return stockShortage;
    }

/** Override login function for admin to login the system.
 * @param email of admin
 * @param password of admin
 * @return true if succesfully logined.
*/
    @Override
    public Boolean login(String email, String password){
        if(!(this.getEmail().equals(email))){
            System.err.println("Incorrect email.");
            return false;
        }

        else if(!(this.getPassword().equals(password))){
            System.err.println("Incorrect password.");
            return false;
        }

        else if(this.getEmail().equals(email) && this.getPassword().equals(password)){
            System.out.println("You have succesfully logined.");
        }

        return true;
    }

/** Add branch to system and set branch no, if it is not exist before.
 * @return added branch
*/
    public Branch addBranch(){
        Branch newBranch = new Branch(getCompany());
        newBranch.setBranchNum();
        getCompany().getAllBranches().add(getCompany().getAllBranches().size(), newBranch);
        return(newBranch);
    }

    /** Add branch with branch number for exceptional cases.
     * @param branchNum of branch
     * @return added Branch
    */
    public Branch addBranch(int branchNum){
        Branch newBranch = new Branch(getCompany());
        newBranch.setBranchNum(branchNum);
        getCompany().getAllBranches().addLast(newBranch);
        return(newBranch);
    }
    /** Remove branch if it exist.
     * @param branchNum of the branch
     * @return true if removed.
    */
    public Boolean removeBranch(int branchNum){
        return(getCompany().getAllBranches().remove(getCompany().getBranch(branchNum)));
    }
    /** Add given branch employee to the branch.
     * @param name employee  
     * @param surname employee  
     * @param branch current
     * @param email employee  
     * @param password employee  
     * @return added Employee
    */
    public Employee addBranchEmployee(String name, String surname, Branch branch, String email, String password){
        Employee newEmployee = new Employee(name, surname, branch, email, password);
        if(!getCompany().getBranch(branch.getBranchNum()).getAllEmployee().contains(newEmployee)){
            getCompany().getBranch(branch.getBranchNum()).getAllEmployee().add(newEmployee);
            return newEmployee;
        }
        return null;
    }
    /** Remove given employee from branch.
     * @param name employee  
     * @param surname employee  
     * @param branch current
     * @param email employee  
     * @param password employee  
     * @return removed Employee
    */
    public Boolean removeBranchEmployee(String name, String surname, Branch branch, String email, String password){
        Employee newEmployee = new Employee(name, surname, branch, email, password);
        if(getCompany().getBranch(branch.getBranchNum()).getAllEmployee().contains(newEmployee)){
            getCompany().getBranch(branch.getBranchNum()).getAllEmployee().remove(newEmployee);
            return true;
        }
        return false;
    }
    /** Check if empoloyee informed and if informed supply missing products.
     * @return true if some products is out of stock.
    */
    public Boolean queryNeeds(){
        if(stockShortage){
            Iterator<Product> i = getToRenewedProducts().iterator();
            while(i.hasNext()){
                Product needed = i.next();
                System.out.println(needed.toString());
                supplyProduct(needed,5);
            }
            return true;
        }

        System.out.println("No product needs to be supplied.");
        return false;
    }

/** Supply missing products.
*/
    public void supplyProduct(Product product, int piece){
        for (int i = 0; i < piece; i++) {
            getCompany().getStorage().add(product);
        }   
    }
}
