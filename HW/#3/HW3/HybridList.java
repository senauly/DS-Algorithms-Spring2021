import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class HybridList<E>{
    private static final int MAX_NUMBER = 10;
    private KWLinkedList<KWArrayList<E>> theData;
    private int totalSize = 0;
    private int listSize = 0;

    public HybridList(){
        theData = new KWLinkedList<>();
    }

    
    /**
     * Remove the item.
     * @param item which will be removed.
     * @return true if element existed and removed.
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
     * Add element to end of the HybridList.
     * @param item which will be added.
     */
    public void add(E item){
        iterator(totalSize).add(item);
    }

    /**
     * Check if HybridList contains the item.
     * @param item which will be checked.
     * @return true if element exists.
     */
    public boolean contains(E item){
        Iterator<E> i = iterator();
        while(i.hasNext()){
            if(i.next().equals(item)) return true;
        }

        return false;
    }

    /**
     * Init a iterator which will be point to index.
     * @param index which will iterator point.
     * @return a iterator.
     */
    public HybridIter iterator(int index){
        return new HybridIter(index);
    }

    /**
     * Init a iterator which will be point to first index.
     * @return a iterator.
     */
    public HybridIter iterator(){
        return new HybridIter(0);
    }

    private class HybridIter implements Iterator<E> {
        private KWArrayList<E> nextList;
        private KWArrayList<E> currList;
        private KWArrayList<E> lastListReturned;
        ListIterator<KWArrayList<E>> itr;
        private E nextItem;
        private E lastItemReturned;
        private int innerIndex = 0;
        private int outerIndex = 0;
        private int i = 0;

        /**
         * Construct a HybridIter that will reference the index.
         * @param index The index of the LinkedList to be referenced
         */
        public HybridIter(int index) {
            if(index < 0 || index > totalSize) throw new IndexOutOfBoundsException("Invalid index " + index);
            
            lastItemReturned = null;
            lastListReturned = null;
            itr = theData.listIterator();
            //hybrid list empty.
            if(totalSize == 0){
                nextItem = null;
                nextList = null;
                currList = null;
            }
            //last item case.
            else if (index == totalSize){
                itr = theData.listIterator(theData.size());
                nextList = null;
                nextItem = null;
                currList = theData.getLast();
                outerIndex = listSize - 1;
                innerIndex = theData.getLast().size();
                i = totalSize;
            }

            //start at the beginning
            else{
                currList = itr.next();
                
                if(index < currList.size()) 
                {
                    innerIndex = index;
                    nextItem = currList.get(innerIndex);
                }

                int count = currList.size();
                
                while(count < index){
                    currList = itr.next();
                    count += currList.size();             
                    outerIndex++;
                    
                    if(count > index){
                        innerIndex = index-currList.size()-1;
                        nextItem = currList.get(innerIndex);
                        i = count + innerIndex;
                        break;
                    }
                }
            }
        }

        /**
         * Construct a HybridIter that will reference the first index.
         * @param index The index of the LinkedList to be referenced
         */
        public HybridIter(){
            lastItemReturned = null;
            lastListReturned = null;
            itr = theData.listIterator();
            
            if(listSize == 0){
                nextItem = null;
                nextList = null;
                currList = null;
            }
            
            else{
                currList = itr.next();
                if(currList.size() == 0) nextItem = null;
                else nextItem = currList.get(0);
            }
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
        @return The next item in the Hybridlist
        @throws NoSuchElementException if there is no such object
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            innerIndex++;
            i++;

            if(innerIndex >=  currList.size()){
                if(outerIndex + 1 < theData.size()){
                    lastListReturned = currList;
                    outerIndex++;
                    currList = itr.next();
                    innerIndex = 0;
                }
                else{
                    lastItemReturned = nextItem;
                    nextItem = null;
                    return lastItemReturned;
                }
            }

            lastItemReturned = nextItem;
            nextItem = currList.get(innerIndex);

            return lastItemReturned; 
        }

         /**
         * Add a new item between the item that will be returned
         * by next and the item that will be returned by previous.
         * If previous is called after add, the element added is
         * returned.
         * @param obj The item to be inserted
         */
        public void add(E item){
            if(listSize == 0 || currList.size() >= MAX_NUMBER){
               itr.add(new KWArrayList<>());
               currList = itr.previous();
               itr.next();
               listSize++;
               outerIndex++;
               innerIndex = 0;
            }
            
            currList.add(item);
            totalSize++;
            innerIndex++;
            i++;
            lastItemReturned = null;
        }

        
        /** Remove the last item returned. This can only be
         *  done once per call to next or previous.
         *  @throws IllegalStateException if next or previous
         *  was not called prior to calling this method
         */
        @Override
        public void remove(){
            if(lastItemReturned==null) throw new IllegalStateException();

            currList.remove(innerIndex-1);
            innerIndex--;
            totalSize--;
            i--;

            if(currList.size() == 0){
                nextList = null;
                itr.remove();
                if(itr.hasPrevious()){
                    currList = itr.previous();
                    innerIndex = currList.size();
                }
                else {
                    currList = null;
                    nextItem = null;
                }
                listSize--;
                outerIndex--;
                i--;
            }

            lastItemReturned = null;
        }
    }
}