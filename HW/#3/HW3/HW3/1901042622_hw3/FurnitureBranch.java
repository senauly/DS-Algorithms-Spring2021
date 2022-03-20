public interface FurnitureBranch {
    public void setBranchNum();
    public void setBranchNum(int branchNum) throws UnsupportedOperationException;
    public int generateBranchNum();
    public Company getCompany();
    public Boolean customerDoesExist(int customerNo);
    public Employee getEmployee(int employeeNum);
    public KWArrayList<Employee> getAllEmployee();
    public HybridList<Product> getAllProducts();
    public HybridList<Product> getStock();
    public KWArrayList<Customer> getAllCustomers();
    public int getBranchNum();
    public Customer findCustomer(String email);
    public Boolean findProduct(String name, String color, String model);
    public Employee findEmployee(String email);
    public void refreshStock();
    public int howManyProduct(Product product);
}
