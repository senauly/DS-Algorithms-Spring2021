public interface FurnitureAdministrator {
    Company getCompany();
    Branch addBranch();
    Branch addBranch(int branchNum);
    Boolean removeBranch(int branchNum);
    Employee addBranchEmployee(String name, String surname, Branch branch, String email, String password);
    Boolean removeBranchEmployee(String name, String surname, Branch branch, String email, String password);
    void queryNeeds();
    void supplyProduct(Product product, int piece);
}
