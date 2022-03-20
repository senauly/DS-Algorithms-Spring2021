import java.util.ListIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class KWLinkedList<E>{
    private static class Node<E> {
        private E data;
        private Node<E> next = null;
        private Node<E> prev = null;
    
        /**
         * Construct a node with the given data value.
         * @param dataItem The data value
         */
        private Node(E dataItem) {
            data = dataItem;
        }
    }

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;
    
    /** Getter for size.
    @return the size of the KWLinkedList
    */
    public int size(){
        return size;
    }

    /** Add an item at position index.
    @param index The position at which the object is to be
    inserted
    @param obj The object to be inserted
    @throws IndexOutOfBoundsException if the index is out of range
    */
    public void add(int index, E obj) {
        listIterator(index).add(obj);
    }

    /** Get the element at position index.
    @param index Position of item to be retrieved
    @return The item at index
    */
    public E get(int index) {
        return listIterator(index).next();
    }

    /**
     * Insert an object at the beginning of the list.
     * @param item - the item to be added
     */
    public void addFirst(E item) {
    	Node<E> n = new Node<>(item);
    	head.prev=n;
    	n.next=head;
    	head=n;
    }

    /**
     * Insert an object at the end of the list.
     * @param item - the item to be added
     */
    public void addLast(E item) {
    	Node<E> n = new Node<>(item);
    	tail.next=n;
    	n.prev=tail;
    	tail=n;
    }

    /**
     * Get the first element in the list.
     * @return The first element in the list.
     */
    public E getFirst() {
    	return head.data;
    }

    /**
     * Get the last element in the list.
     * @return The last element in the list.
     */
    public E getLast() {
    	return tail.data;

    }

    /**
     * Remove the item if exists.
     * @param item to remove
     * @return true if removed.
     */
    public boolean remove(E item){
        Iterator<E> i = iterator();

        while(i.hasNext()){
            if(i.next().equals(item)){
                i.remove();
                return true;
            }
        }
        
        return false;
    }

    /**
     * Check if LinkedList contains the item.
     * @param item which will be checked.
     * @return true if element exists.
     */
    public boolean contains(E item){
        ListIterator<E> i = listIterator();
        while(i.hasNext()){
            if(i.next().equals(item)) return true;
        }

        return false;
    }

    /**
     * Return an Iterator to the list
     * @return an Itertor to the list
     */
    public Iterator<E> iterator() {
    	return new KWListIter(0);
    }

    /**
     * Return a ListIterator to the list
     * @return a ListItertor to the list
     */
    public ListIterator<E> listIterator() {
    	return new KWListIter(0);
    }

    /** Return a ListIterator that begins at index
     * @param index - The position the iteration is to begin
     * @return a ListIterator that begins at index
     */
    public ListIterator<E> listIterator(int index) {
    	return new KWListIter(index);

    }

    /**
     * Return a ListIterator that begins at the same
     * place as an existing ListIterator
     * @param iter - The other ListIterator
     * @return a ListIterator that is a copy of iter
    */
    public ListIterator <E> listIterator(ListIterator <E> iter) {
    	int index=0; //index iter is at
    	while(iter.hasPrevious()) {
    		iter.previous();
    		index++;
    	}
    	for(int i=0;i<index;i++)
    		iter.next(); //return iter to original state
    	return new KWListIter(index);
    }

    private class KWListIter implements ListIterator<E> {
        private Node<E> nextItem;
        private Node<E> lastItemReturned;
        private int index = 0;

        /**
         * Construct a KWListIter that will reference the ith item.
         * @param i The index of the item to be referenced
         */
        public KWListIter(int i) {
            // Validate i parameter.
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException(
                        "Invalid index " + i);
            }
            lastItemReturned = null; // No item returned yet.
            // Special case of last item.
            if (i == size) {
                index = size;
                nextItem = null;
            } else { // Start at the beginning
                nextItem = head;
                for (index = 0; index < i; index++) {
                    nextItem = nextItem.next;
                }
            }
        }

        /**
         * Construct a KWListIter that is a copy of another KWListIter
         * @param other The other KWListIter
         */
        public KWListIter(KWListIter other) {
            KWListIter itr = new KWListIter(0);
            itr.index = other.index;
            itr.lastItemReturned = other.lastItemReturned;
            itr.nextItem = other.nextItem;
        }

        /**
         * Indicate whether movement forward is defined.
         * @return true if call to next will not throw an exception
         */
        @Override
        public boolean hasNext() {
            return nextItem != null;
        }

        /** Move the iterator forward and return the next item.
        @return The next item in the list
        @throws NoSuchElementException if there is no such object
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        /**
         * Indicate whether movement backward is defined.
         * @return true if call to previous will not throw an exception
         */
        @Override
        public boolean hasPrevious() {
            return (nextItem == null && size != 0)
                    || (nextItem != null && nextItem.prev != null);
        }

        /**
         * Return the index of the next item to be returned by next
         * @return the index of the next item to be returned by next
         */
        @Override
        public int nextIndex() {
            return index;
        }

        /**
         * Return the index of the next item to be returned by previous
         * @return the index of the next item to be returned by previous
         */
        @Override
        public int previousIndex() {
            return index - 1;
        }

        /**
         * Move the iterator backward and return the previous item.
         * @return The previous item in the list
         * @throws NoSuchElementException if there is no such object
         */
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (nextItem == null) { // Iterator past the last element
                nextItem = tail;
            } else {
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        /**
         * Add a new item between the item that will be returned
         * by next and the item that will be returned by previous.
         * If previous is called after add, the element added is
         * returned.
         * @param obj The item to be inserted
         */
        @Override
        public void add(E obj) {
            if (head == null) { // Add to an empty list.
                head = new Node<>(obj);
                tail = head;
            } else if (nextItem == head) { // Insert at head.
                Node<E> newNode = new Node<>(obj);
                newNode.next = nextItem; 
                nextItem.prev = newNode; 
                head = newNode;
            } 
            else if (nextItem == null) { // Insert at tail.
                Node<E> newNode = new Node<>(obj);
                tail.next = newNode; 
                newNode.prev = tail; 
                tail = newNode;
            } 
            else { // Insert into the middle.
                Node<E> newNode = new Node<>(obj);
                newNode.prev = nextItem.prev;
                nextItem.prev.next = newNode; 
                newNode.next = nextItem;
                nextItem.prev = newNode;
            }
    
            size++;
            index++;
            lastItemReturned = null;
        }

        /** Remove the last item returned. This can only be
         *  done once per call to next or previous.
         *  @throws IllegalStateException if next or previous
         *  was not called prior to calling this method
         */
        @Override
        public void remove() {
            if(lastItemReturned == head){
                if(lastItemReturned != tail){
                    head = lastItemReturned.next;
                    head.prev = null;
                }
                else{
                    tail = null;
                    head = null;
                }
            }
            
            else if(lastItemReturned == tail){
                tail = lastItemReturned.prev;
                tail.next = null;
            }
            
            else{
                lastItemReturned.next.prev = lastItemReturned.prev;
                lastItemReturned.prev.next = lastItemReturned.next;
            }
            
            lastItemReturned = null;
            size--;
        }
    
        /** Replace the last item returned with a new value
         *  @param item The new value
         *  @throws IllegalStateException if next or previous
         *  was not called prior to calling this method
         */
        @Override
        public void set(E item) {
        	if(lastItemReturned==null)
        		throw new IllegalStateException();
        	lastItemReturned.data=item;
         }
    }
}
