import java.io.*;
/**
 * FurnitureCompany
 */
public interface FurnitureCompany {
    public void setCompanyName(String compName);
    public String getName();
    public Branch getBranch(int branchNum);
    public MyArray<Branch> getAllBranches();
    public Administrator admin();
    public MyArray<Product> getStorage();
    public void addRegisteredCustomers() throws IOException;
    public void addOldStock() throws IOException;
    public Branch findBranch(int branchNo);
    public File getCustomerList();
    public Boolean isRegistered(Customer customer) throws IOException;
    public Employee findEmployee(String email);
    public Customer findCustomer(String email);

}