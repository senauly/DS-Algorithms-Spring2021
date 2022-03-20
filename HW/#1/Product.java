public class Product{
    private String name;
    private String color;
    private String model;

    public Product(String name, String color, String model){
        setName(name);
        setColor(color);
        setModel(model);
    }

    public String getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public String getModel(){
        return model;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setModel(String model){
        this.model = model;
    }

    @Override
    public boolean equals(Object other) { 
        if (other == this) { 
            return true; 
        } 

        if (!(other instanceof Product)) { 
            return false; 
        } 

        Product p = (Product) other;
        if(p.getColor() == this.getColor() && p.getName() == this.getName() && p.getModel() == this.getModel()) return true;
        return(false);

    }

    @Override
    public String toString() { 
        return(getName() + " " + getModel() + " in " + getColor() + " color\n"); 
    }
}
