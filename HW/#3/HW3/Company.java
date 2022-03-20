import java.io.*;
import java.util.ListIterator;
import java.util.Iterator;
/**
 * @author Sena Ulukaya 
 * This class created for the automation system.
 */

public class Company implements FurnitureCompany{
    private String companyName;
    private Administrator admin;
    private KWLinkedList<Branch> branches;
    private HybridList<Product> storage;
    
    public Company(String companyName, String adminName, String adminSurname){
        setCompanyName(companyName);
        admin = new Administrator(adminName, adminSurname,this);
        branches = new KWLinkedList<>();
        storage = new HybridList<>();
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

    public KWLinkedList<Branch> getAllBranches(){
        return branches;
    }

    public Administrator admin(){
        return admin;
    }

    public HybridList<Product> getStorage(){
        return storage;
    }

/** Find branch by branch number.
 * @param branchNo of branch
 * @return branch if it is found, else null.
*/
    public Branch findBranch(int branchNo){
        ListIterator<Branch> i = getAllBranches().listIterator();
        while(i.hasNext()){
            Branch branch = i.next();
            if(branch.getBranchNum() == branchNo) return(branch);
        }
        return null;
    }

    public File getCustomerList(){
        return customerList;
    }

    /** Find customer by email.
     * @param email of customer
     * @return customer if found, else null.
     * 
    */
    public Customer findCustomer(String email){
        Customer customer = null;
        ListIterator<Branch> i = getAllBranches().listIterator();
        while(i.hasNext()){
            Branch branch = i.next();
            if((customer = branch.findCustomer(email)) != null ) return(customer);
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
        ListIterator<Branch> i = getAllBranches().listIterator();
        while(i.hasNext()){
            Branch branch = i.next();
            if((employee = branch.findEmployee(email)) != null ) return(employee);
        }

        return null;
    }

    
    /** Find which branch does the product in.
     * @param product toFind
     * @return product if found, else null.
     * 
    */
    public Branch findProduct(Product product){
        ListIterator<Branch> i = getAllBranches().listIterator();
        Branch whichBranch = null;
        while(i.hasNext()){
            Branch br = i.next();
            Iterator<Product> j = br.getAllProducts().iterator();
            while(j.hasNext()){
                Product pr = j.next();
                if(pr.equals(product)){
                    return(br);
                }
            }
        }

        return whichBranch;
    }

    @Override
    public String toString(){
        return("Company Name: " + companyName + "\n" + "Admin:\n" + admin().toString() + "\n");
    }
}
