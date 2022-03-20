import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.SortedSet;

/**
 * SkipList implementation with add, remove, descendingIterator methods of NavigableSet interface.
 * @author Sena Ulukaya
 */
public class MySkipList<E extends Comparable<E>> implements NavigableSet<E>{
    private SLNode<E> head = null;
    private int maxCap = 2;
    private int maxLevel = (int) (Math.log(maxCap)/Math.log(2.0));
    private Random rand = new Random();
    private int size = 0;
    
    /**
     * Class represents SLNodes.
     */
    private static class SLNode<E> {
        SLNode<E>[] links;
        E data;
        
        /**
         * Create a SLNode with given level.
         * @param m level
         * @param data given data
         */
        public SLNode(int m, E data) {
            links = (SLNode<E>[]) new SLNode[m]; // create links
            this.data = data; // store item
        }
    }

    /**
     * Add an item.
     * @param e item to add
     * @return true if element does not exist before, false otherwise.
     */
    @Override
    public boolean add(E e) {
        return(insert(e));
    }

    /**
     * Remove an item if exist.
     * @param o item to remove
     * @return true if element removed, false otherwise.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        return(delete((E) o));
    }

    /**
     * Initialize a descending iterator.
     * @return iterator
     */
    @Override
    public Iterator<E> descendingIterator() {
        return(new Iter());
    }

    /** Method to generate a logarithmic distributed integer between 1 and maxLevel. i.e., 1/2 of the values returned are 1, 1/4 are 2, 1/8 are 3, etc.
     * @return a random logarithmic distributed int between 1 and maxLevel
    */
    private int logRandom() {
        int r = rand.nextInt(maxCap);
        int k = (int) (Math.log(r + 1) / Math.log(2.0));
        if (k > maxLevel - 1) k = maxLevel - 1;
        return maxLevel - k;
    }
    
    /**
     * Size of the skip-list.
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Check if skip-list is empty.
     * @return true if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return(size == 0);
    }

    /** Insert an item to the skip-list.
     * @param e The item to add
     * @return true if element does not exist before, false otherwise.
    */
    public boolean insert(E e){
        if(head == null){
            head = new SLNode<>(maxLevel,e);
            size++;
            return true;
        }
        if(head.data.compareTo(e) == 0)
            return false;

        SLNode<E>[] pred = search(e);
        if(pred[0].data.compareTo(e) == 0 && pred[0].links[0] != null && pred[0].links[0].data.compareTo(e) == 0) return false;
    
        int level = logRandom();
        SLNode<E> node = new SLNode<>(level,e);
        size++;

        if(e.compareTo(head.data) < 0){
            node.data = head.data;
            head.data = e;
        }
        
        
        for (int i = 0; i < level; i++){
            node.links[i] = pred[i].links[i];
            pred[i].links[i] = node;
        }

        if (size > maxCap) {
            maxLevel++;
            maxCap = newCap(maxLevel);
            head.links = Arrays.copyOf(head.links, maxLevel);
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    /** Search for an item in the list
     * @param item The item being sought
     * @return A SLNode array which references the predecessors of the target at each level.
    */
    private SLNode<E>[] search (E target){
        SLNode<E>[] pred = new SLNode[maxLevel];
        SLNode<E> curr = head;
        
        for (int i = curr.links.length-1; i >= 0; i--) {
            while (curr.links[i] != null && curr.links[i].data.compareTo(target) < 0) {
                curr = curr.links[i];
            }
        
            pred[i] = curr;
        }
       
        return pred;
    }

    /** Find an object in the skip-list
     * @param target The item being sought
     * @return A reference to the object in the skip-list that matches the target. If not found, null is returned.
    */
    public E find(E target) {
        SLNode<E>[] pred = search(target);
        if(pred[0].data.compareTo(target) == 0) return pred[0].data;
        if(pred[0].links[0] != null && pred[0].links[0].data.compareTo(target) == 0) return pred[0].links[0].data;
        else return null;
    }

    /**
     * Returns new capacity of 2^level -1.
     * @param level maxLevel
     * @return new capacity
     */
    private int newCap(int level){
        return((int) Math.pow(2, level) -1);
    }
    
    /**
     * Delete the item if exist.
     * @param e item to remove
     * @return true if element removed, false otherwise.
     */
    public boolean delete(E e){
        if(head == null) return false;

        SLNode<E>[] pred = search(e);
        if(pred[0].data.compareTo(e) == 0){
            
            if(pred[0].links[0] == null){
                head = null;
                size--;
                return true;
            }
            
            SLNode<E> temp = pred[0].links[0];
            int tempLvl = temp.links.length;
            temp.links = Arrays.copyOf(temp.links, maxLevel);

            for (int i = tempLvl; i < maxLevel; i++){
                temp.links[i] = head.links[i];
            }
            
            head = temp;
            size--;
            return true;
        }

        if(pred[0].links[0] != null && pred[0].links[0].data.compareTo(e) == 0){
            
            SLNode<E> remove = pred[0].links[0];
            for (int i = 0; i < remove.links.length; i++) {
                pred[i].links[i] = remove.links[i];
            }

            size--;
            return true;
        }

        return false;
    }

    /**
     * Displays first level of the skip-list.
     * @return string
     */
    @Override
    public String toString() {
        SLNode<E> curr = head;
        StringBuilder sb = new StringBuilder();
        while(curr != null){
            sb.append(curr.data);
            if(curr.links[0] != null) sb.append(" -> ");
            curr = curr.links[0];
        }

        return sb.toString();
    }

    /**
     * Iterator class to iterate through elements decreasing order.
     */
    private class Iter implements Iterator<E>{
        private int i = 0;
        private ArrayList<E> arr = new ArrayList<>();
        private E lastReturned = null;

        public Iter(){
            SLNode<E> curr = head;

            while(curr != null){
                arr.add(curr.data);
                curr = curr.links[0];
            }
        }
        
        /**
         * Indicate whether movement is defined.
         * @return true if call to next will not throw an exception
         */
        @Override
        public boolean hasNext() {
            return(i < size);
        }

        /** Move the iterator forward and return the next item.
         * @return The next item in the Hybridlist
         * @throws NoSuchElementException if there is no such object
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            i++;
            lastReturned = arr.get(size-i);
            return(lastReturned);
        }

        /** Remove the last item returned. This can only be done once per call to next or previous.
         *  @throws IllegalStateException if next was not called prior to calling this method
         */
        @Override
        public void remove(){
            if(lastReturned==null) throw new IllegalStateException();
            delete(lastReturned);
            arr.remove(lastReturned);
            i--;
        }
    }

    @Override
    public Comparator<? super E> comparator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E first() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E last() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(); 
    }

    @Override
    public E lower(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E floor(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E ceiling(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E higher(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        throw new UnsupportedOperationException();
    }
}