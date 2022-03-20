import java.util.Arrays;
import java.util.NoSuchElementException;

public class KWArrayList<E> {
    
    private static final int INITIAL_CAPACITY = 10;
    private E[] theData;
    private int size = 0;
    private int capacity = 0;
    
    @SuppressWarnings("unchecked")
    public KWArrayList() {
        capacity = INITIAL_CAPACITY;
        theData = (E[]) new Object[capacity];
    }

    public KWArrayList(KWArrayList<E> other) {
        capacity = other.capacity;
        size = other.size;
        theData = Arrays.copyOf(other.theData, other.capacity);
    }

    public int capacity(){
        return capacity;
    }

    public int size() {
        return size;
    }
        
    public boolean add(E anEntry) {
        if(size == capacity) {
            reallocate();
        }
        
        theData[size] = anEntry;
        size++;
        return true;
    }
    
    public void add(int index, E anEntry) {
        if(index<0 || index>=size){
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if(size == capacity) {
            reallocate();
        }
        
        for(int i=size; i>index; i--){
            theData[i] = theData[i-1];
        }
        
        theData[index] = anEntry;
        size++;
    }

    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException(index);
        
        E returnValue = theData[index];

        for (int i = index + 1; i < size; i++)
            theData[i - 1] = theData[i];
        
        size--;
        return returnValue;
    }

    /** Checks if array contains specified element.
     * @param e checked element
     * @return true if contains the element.
    */
    public Boolean contains(E e){
        for(int i=0; i < size(); i++){
            if(theData[i] != null && theData[i].equals(e)) return true;
        }
        return false;
    } 

     /** Removes the specified element if it does exist.
     * @param e element to remove
     * @return true if it is changed.
     * @throws NoSuchElementException when size is 0 or element does not exist.
    */
    public Boolean remove(E e) throws NoSuchElementException{
        int index = 0;
        if(size() == 0) throw new NoSuchElementException("There is no such thing.");
        if(e != null && !contains(e)) throw new NoSuchElementException("There is no such thing.");

        for(int i = 0; i < size(); i++){
            if(theData[i] == e) index = i;
        }

        size--;

        for(int i = 0; i < size(); i++){
            if(i >= index) theData[i] = theData[i+1];
        }

        return(true);
    }

    public E get(int index) {
        if(index<0 || index>=size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return theData[index];
    }
    
    public E set(int index, E newValue) {
        if(index<0 || index>=size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        
        E oldValue = theData[index];
        theData[index] = newValue;
        return oldValue;
    }
    
    private void reallocate() {
        capacity = 2*capacity;
        theData = Arrays.copyOf(theData, capacity);
    }
}