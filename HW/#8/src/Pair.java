public class Pair<U, V extends Comparable<V>> implements Comparable<Pair<U,V>>{
    private U first;
    private V second;

    /**
    * Constructs a new Pair with the given values.
    * @param first  the first element
    * @param second the second element
    */
    public Pair(U first, V second){
        this.first = first;
        this.second = second;
    }

    /**
     * Get the first element of the pair.
     * @return first element
     */
    public U getFirst() {
        return this.first;
    }

    /**
     * Get the second element of the pair.
     * @return second element
     */
    public V getSecond() {
        return this.second;
    }

    /**
     * Compare the pair according to its second value.
     * @param o other pair
     * @return 1 if greater, -1 if smaller, 0 if equal.
     */
    @Override
    public int compareTo(Pair<U, V> o) {
        if(this.getSecond().compareTo(o.getSecond()) < 0) return -1;
        if(this.getSecond().compareTo(o.getSecond()) > 0) return 1;
        return 0;
    }
}   