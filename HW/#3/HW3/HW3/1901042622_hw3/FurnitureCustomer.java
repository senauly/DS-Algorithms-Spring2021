public interface FurnitureCustomer{
    Boolean register(String email, String password);
    int searchProduct(String name, String color, String model);
    void seeProducts();
    Boolean shopOnline(String adress, String phone, Product product);
    void viewPreviousOrders();
}