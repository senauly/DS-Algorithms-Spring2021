public class Order{
    private String customerID;
    private String traderName;
    private Product product;
   
    public enum status{
        WAITING,
        ACCEPTED,
        DENIED
    }
    
    public Order(String customerID, String traderName, Product product) {
        this.customerID = customerID;
        this.traderName = traderName;
        this.product = product;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getTraderName() {
        return this.traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    @Override
    public String toString() {
        return " customerID='" + getCustomerID() + "'" +
            ", traderName='" + getTraderName() + "'" +
            ", product='" + getProduct().getName() + "'";
    }

}
