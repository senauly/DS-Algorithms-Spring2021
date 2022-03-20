import java.util.NoSuchElementException;

public interface FurnitureArray<E>{
    public void setSize(int size);
    public int getSize();
    public void setCapacity(int capacity);
    public int getCapacity();
    public E[] changeCapacity();
    public Boolean contains(E e);
    public void clear();
    public Boolean add(E e) throws UnsupportedOperationException;
    public Boolean isEmpty();
    public Boolean remove(E e) throws NoSuchElementException;
    public E at(int index) throws NoSuchElementException;
    public E find(E e) throws NoSuchElementException;

}
