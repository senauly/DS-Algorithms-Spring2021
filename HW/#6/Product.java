import java.util.Comparator;

public class Product implements Comparable<Product>{
    private String ID;
    private Category category;
    private String name;
    private String description;
    private double price;
    private double discount;
    private String traderName;

    protected Product(){
        //empty on purpose
    }

    public Product(String ID, Category category, String name, String description, double price, double discount, String traderName) {
        this.ID = ID;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.traderName = traderName;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getTraderName() {
        return this.traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }


    @Override
    public String toString() {
        return " ID='" + getID() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", discount='" + getDiscount() + "'" +
            ", traderName='" + getTraderName();
    }

    @Override
    public int compareTo(Product o) {
        if(this.getName().compareTo(o.getName()) < 0 ) return -1;
        else if(this.getName().compareTo(o.getName()) > 0 ) return 1; 
        else return 0;
    }
}
