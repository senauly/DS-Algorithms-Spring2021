import java.util.Random;

public abstract class IDGenerator{
    private static final int USERIDLENGTH = 8;
    private static final int PRIDLENGTH = 16;
    
    private IDGenerator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generates unique ID according to class type.
     * @param o class type
     * @param count number of instance of this class
     * @return ID
     * @throws UnsupportedOperationException if class type is sonething other than product, trader and customer.
     */
    public static String generateID(Object o, int count) throws UnsupportedOperationException{
        StringBuilder sb = new StringBuilder();
        
        if(o instanceof Product) sb.append("PRD");
        else if(o instanceof Trader) sb.append("TR");
        else if(o instanceof Customer) sb.append("CU");
        else throw new UnsupportedOperationException();

        sb.append(Integer.toHexString(count));
        if(o instanceof Product) appendID(PRIDLENGTH, sb);
        else appendID(USERIDLENGTH, sb);

        return sb.toString().toUpperCase();
    }

    /**
     * Append id until the Id length with the random numbers.
     * @param length final lenghth
     * @param sb string builder to append
     */
    private static void appendID(int length, StringBuilder sb){
        Random rand = new Random();
        while(sb.length() < length){
            int next = rand.nextInt(1000);
            if(next <= PRIDLENGTH - sb.length()) sb.append(Integer.toHexString(next));
        }
    }
}