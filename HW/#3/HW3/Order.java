import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Order{
    LocalDate date;
    LocalTime time;
    DateTimeFormatter dateFormatter;
    DateTimeFormatter timeFormatter;
    Product product;

/** Add the current time and date with a format to the order.
 * @param product to order
*/
    public Order(Product product){
        this.product = product;
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        date = LocalDate.now();
        time = LocalTime.now();
    }

    @Override
    public String toString(){
        return(dateFormatter.format(date) + " " + timeFormatter.format(time) + "\n" + product.toString());
    }
}
