import java.util.NoSuchElementException;

public class MyArray<E> implements FurnitureArray<E> {
    private E[] array;
    private int size;
    private int capacity;
    
    public MyArray(){
        this(10);
    }
    /** Set size 0 at the first place and create array with given capacity.
 * @param capacity capacity of the array
*/
    @SuppressWarnings("unchecked")
    public MyArray(int capacity){
        setCapacity(capacity);
        setSize(0);
        array = (E[]) new Object[getCapacity()];
    }

    public void setSize(int size){
        this.size = size;
    }

    public int getSize(){
        return(size);
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public int getCapacity(){
        return(capacity);
    }

    /** Change capacity to its double.
 * @return copied temp array
*/
    @SuppressWarnings("unchecked")
    public E[] changeCapacity(){
        setCapacity(getCapacity()*2);
        E[] temp = (E[]) new Object[getCapacity()];
        for(int i = 0; i < getSize(); i++){
            if(array[i] != null) temp[i] = array[i];
        }

        array = (E[]) new Object[getCapacity()];
        return(temp);
    }

    /** Checks if array contains specified element.
     * @param e checked element
     * @return true if HashSet contains the element.
    */
    public Boolean contains(E e){
        for(int i=0; i < getSize(); i++){
            if(array[i] != null && array[i].equals(e)) return true;
        }
        return false;
    }

    /** Clear array, makes it size 0.
    */
    @SuppressWarnings("unchecked")
    public void clear(){
        array = (E[]) new Object[getCapacity()];
        setSize(0);
    }

    /** Adds the specified element to collection if it does not exist before. Change capacity, if it is not enough.
 * @param e element to add
 * @return true if array is changed.
*/
    public Boolean add(E e) throws UnsupportedOperationException{
        if((e == null || contains(e)) && !(e instanceof Product)) return false;
        int nextSize;
        nextSize = getSize() + 1;
        if(nextSize > getCapacity()){
            E[] temp = changeCapacity();
            for(int i = 0; i < getSize(); i++){
                array[i] = temp[i];
            }
        }

        setSize(getSize()+1);
        array[getSize()-1] = e;
        return(true);
    }

    /** Checks if HashSet is empty.
 * @return true if its size is zero.
*/
    public Boolean isEmpty(){
        return(getSize() == 0);
    }

 /** Removes the specified element if it does exist.
 * @param e element to remove
 * @return true if it is changed.
 * @throws NoSuchElementException when size is 0 or element does not exist.
*/
    public Boolean remove(E e) throws NoSuchElementException{
        int index = 0;
        if(getSize() == 0) throw new NoSuchElementException("There is no such thing.");
        if(e != null && !contains(e)) throw new NoSuchElementException("There is no such thing.");

        for(int i = 0; i < getSize(); i++){
            if(array[i] == e) index = i;
        }

        setSize(getSize()-1);

        for(int i = 0; i < getSize(); i++){
            if(i >= index) array[i] = array[i+1];
        }

        return(true);
    }

/** Return the element at the index.
 * @param index to return
 * @return E element
 * @throws NoSuchElementException when index is bigger than size or smaller than 0.
*/
    public E at(int index) throws NoSuchElementException{
        if(index >= getSize() || index < 0) throw new NoSuchElementException("There is no such thing.\n");
        return(array[index]);
    }

/** Find the given element.
 * @param e element
 * @return E element
 * @throws NoSuchElementException when there's no such element.
*/
    public E find(E e) throws NoSuchElementException{
        E founded = null;
        if(!(contains(e))) throw new NoSuchElementException("There is no such thing.\n");
        for (int i = 0; i < array.length; i++) {
            if(array[i].equals(e)){
                founded = array[i];
                break;
            }
        }

        return founded;
    }
}
