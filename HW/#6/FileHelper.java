import java.util.Iterator;

public abstract class FileHelper{
    private FileHelper() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Trim the line string according to its data category.
     * @param line in the file
     * @param dataCategory for productFile 1: id, 2:product_name, 3:product_category_tree, 4: price, 5: discounted_price, 6: description, 7: trader representations.
                           for userFile 1: id, 2: userName, 3: password.
                           for orderFile 1:userID, 2:traderName, 3:productID, 4:orderStatus
     * @return the requested data 
     */
    public static String trimLine(String line, int dataCategory){
        int index = line.indexOf(";"); 
        if(index == -1) return null;
        
        if(dataCategory == 1)return(line.substring(0 , index));

        if(dataCategory == 7){
            index = line.lastIndexOf(";");
            return(line.substring(index+1,line.length()));
        }
        
        else return(trimLine(line.substring(index+1,line.length()), dataCategory-1));

    }

    /**
     * Create product file line.
     * @param id product id
     * @param name product name
     * @param category product category
     * @param price product price
     * @param discountedPrice product discountedPrice
     * @param description product description
     * @param trader trader name
     * @return line
     */
    public static String createLine(String id, String name, Category category, double price, double discountedPrice, String description, String trader){
        return(id + ";" + name + ";" + category.toString() + ";" + price + ";" + discountedPrice + ";" + description + ";" + trader);
    }

    /**
     * Returns category object according to file.
     * @param category string
     * @return category object
     */
    public static Category trimCategory(String category){
        Category cg = new Category();
        category = category.substring(4, category.length()-4);
        String[] str = category.split(" >> ");
        
        for (int i = 0; i < str.length; i++) {
            cg.add(str[i]);
        }
        
        return cg;
    }
}
