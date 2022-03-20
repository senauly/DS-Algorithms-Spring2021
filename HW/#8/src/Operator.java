/**
 * Represents an associative operator.
 */
public interface Operator {
    /**
     * Calculates results with associative operator.
     * @param a double number
     * @param b double number
     * @return the result of the calculation
     */
    public abstract double op(double a, double b);
}
