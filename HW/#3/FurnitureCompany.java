/**
 * FurnitureCompany
 */
public interface FurnitureCompany {
    public void setCompanyName(String compName);
    public String getName();
    public Branch getBranch(int branchNum);
    public KWLinkedList<Branch> getAllBranches();
    public Administrator admin();
    public HybridList<Product> getStorage();
    public Branch findBranch(int branchNo);
    public Employee findEmployee(String email);
    public Customer findCustomer(String email);

}