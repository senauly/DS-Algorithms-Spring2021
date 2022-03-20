import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * AVLTree implementation with add, Iterator, headSet, tailSet methods of NavigableSet interface.
 * @author Sena Ulukaya
 */
public class MyAVLTree<E extends Comparable<E>> extends BinarySearchTree<E> implements NavigableSet<E>{
    private boolean increase;
    
    /**
     * Class to represent an AVL Node. 
     */
    private static class AVLNode<E> extends Node<E>{
        public static final int LEFT_HEAVY = -1;
        public static final int BALANCED = 0;
        public static final int RIGHT_HEAVY = 1;
        private int balance;

        /** Construct a node with the given item as the data field.
         * @param item The data field
         */
        public AVLNode(E item) {
            super(item);
            balance = BALANCED;
            isRed = true;
        }
        
        /** Return a string representation of this object. The balance value is appended to the contents.
         *  @return String representation of this object
         */
        @Override
        public String toString() {
            return (balance + ": " + data.toString());
        }
    }
    
    /** Insert an item if it does not exist before.
     * @param e The item to add
     * @return true if element does not exist before, false otherwise.
    */
    @Override
    public boolean add(E e) {
        return(insert(e));
    }

    /**
     * Initialize a iterator.
     * @return iterator
     */
    @Override
    public Iterator<E> iterator() {
        return(new Iter());
    }

    /**
     * Create a new set that includes elements smaller than given element.
     * @param toElement create set until this element.
     * @param inclusive if true include element, otherwise exclude 
     * @throws NullPointerException if {@code toElement} is null.
     * @return the new set
     */
    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive){
        if(toElement == null) throw new NullPointerException();
        TreeSet<E> headSet = new TreeSet<>();
        headSetRec((AVLNode<E>) root, headSet, toElement, inclusive);
        return(headSet);
    }

    /**
     * Create a new set that includes elements greater than given element.
     * @param fromElement create set until this element.
     * @param inclusive if true include element, otherwise exclude 
     * @throws NullPointerException if {@code toElement} is null.
     * @return the new set
     */
    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive){
        if(fromElement == null) throw new NullPointerException();
        TreeSet<E> headSet = new TreeSet<>();
        tailSetRec((AVLNode<E>) root, headSet, fromElement, inclusive);
        return(headSet);
    }

    /**
     * Create a new set that includes elements smaller than given element. without including it.
     * @param toElement create set until this element.
     * @throws NullPointerException if {@code toElement} is null.
     * @return the new set
     */
    @Override
    public SortedSet<E> headSet(E toElement){
        return(headSet(toElement, false));
    }

    /**
     * Create a new set that includes elements greater than given element with including it.
     * @param fromElement create set until this element.
     * @throws NullPointerException if {@code toElement} is null.
     * @return the new set
     */
    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return(tailSet(fromElement, true));
    }

    /**
     * Recursive headSet method to find and add headSet elements.
     * @param root localroot
     * @param set for add elements
     * @param toElement add elements that smaller than or equal to this element.
     * @param inclusive if true include element, otherwise exclude 
     */
    private void headSetRec(AVLNode<E> root, TreeSet<E> set, E toElement, boolean inclusive){
        if(root == null){
            return;
        }

        if(toElement.compareTo(root.data) < 0){
            headSetRec((AVLNode<E>) root.left, set, toElement, inclusive);
        }

        else if(toElement.compareTo(root.data) == 0){
            if(inclusive) set.add(root.data);
            headSetRec((AVLNode<E>) root.left, set, toElement, inclusive);
        } 

        else{
            set.add(root.data);
            headSetRec((AVLNode<E>) root.left, set, toElement, inclusive);
            headSetRec((AVLNode<E>) root.right, set, toElement, inclusive);
        }
    }

    /**
     * Recursive tailSet method to find and add tailSet elements.
     * @param root localroot
     * @param set for add elements
     * @param toElement add elements that greater than or equal to this element.
     * @param inclusive if true include element, otherwise exclude 
     */
    private void tailSetRec(AVLNode<E> root, TreeSet<E> set, E fromElement, boolean inclusive){
        if(root == null){
            return;
        }

        if(fromElement.compareTo(root.data) >  0){
            tailSetRec((AVLNode<E>) root.right, set, fromElement, inclusive);
        }

        else if(fromElement.compareTo(root.data) == 0){
            if(inclusive) set.add(root.data);
            tailSetRec((AVLNode<E>) root.right, set, fromElement, inclusive);
        } 

        else{
            set.add(root.data);
            tailSetRec((AVLNode<E>) root.left, set, fromElement, inclusive);
            tailSetRec((AVLNode<E>) root.right, set, fromElement, inclusive);
        }
    }

    /** Insert an item to the skip-list.
     * @param e The item to add
     * @return true if element does not exist before, false otherwise.
    */
    public boolean insert(E e){
        AVLNode<E> node = insertRec((AVLNode<E>) root, e);
        if(node != null){
            root = node;
            return true;
        }
        return false;
    }

    /** Recursive insert method. 
     * @param root The local root of the subtree
     * @param item The item to add
     * @return The new local root of the subtree with the item inserted
     */
    private AVLNode<E> insertRec(AVLNode<E> root, E item){
        if(root == null){
            increase = true;
            return new AVLNode<>(item);
        }

        if(item.compareTo(root.data) == 0) {
            increase = false;
            return null;
        }

        if(item.compareTo(root.data) < 0){
            AVLNode<E> temp = insertRec((AVLNode<E>) root.left, item);
            if(temp != null){
                root.left = temp;
                if(increase){
                    root.balance--;
                    if(root.balance == MyAVLTree.AVLNode.BALANCED) increase = false;
                    if(root.balance < MyAVLTree.AVLNode.LEFT_HEAVY){
                        increase = false;
                        return rebalanceLeft(root);
                    }
                }
            }
            else return null;
        }

        else if(item.compareTo(root.data) > 0){
            AVLNode<E> temp = insertRec((AVLNode<E>) root.right, item);
            if(temp != null){
                root.right = temp;
                if(increase){
                    root.balance++;
                    if(root.balance == MyAVLTree.AVLNode.BALANCED) increase = false;
                    if(root.balance > MyAVLTree.AVLNode.RIGHT_HEAVY){
                        increase = false;
                        return rebalanceRight(root);
                    }
                }
            }
            else return null;
        }

        return root;
    }

    /** Method to rebalance left.
     * @pre localRoot is the root of an AVL subtree that is critically left-heavy.
     * @post Balance is restored.
     * @param localRoot Root of the AVL subtree that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {

        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        if (leftChild.balance > AVLNode.BALANCED) {
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
            if (leftRightChild.balance < AVLNode.BALANCED) {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } 
            
            else {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            
            localRoot.left = rotateLeft(leftChild);
        } 
        
        else{
       
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        
        return rotateRight(localRoot);
    }

    /** Method to rebalance right.
     * @pre localRoot is the root of an AVL subtree that is critically right-heavy.
     * @post Balance is restored.
     * @param localRoot Root of the AVL subtree that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        if (rightChild.balance < AVLNode.BALANCED) {
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
            
            if (rightLeftChild.balance > AVLNode.BALANCED) {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            } 
            
            else {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }

            localRoot.right = rotateRight(rightChild);
        } 
        
        else{
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        
        return rotateLeft(localRoot);
    }

    /** Method to perform a right rotation.
     * @param root The root of the binary tree to be rotated
     * @return The new root of the rotated tree
     */
    protected AVLNode<E> rotateRight(AVLNode<E> root) {
        AVLNode<E> temp = (AVLNode<E>) root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }
    
    /** Method to perform a left rotation.
     * @param root The root of the binary tree to be rotated
     * @return The new root of the rotated tree
     */
    protected AVLNode<E> rotateLeft(AVLNode<E> root) {
        AVLNode < E > temp = (AVLNode<E>) root.right;
        root.right = temp.left;
        temp.left = root;
        return temp;
    }
    
    /**
     * Preorder traversal of the tree.
     * @return string
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /** Perform a inorder traversal.
     * @param node The local root
     * @param depth The depth
     * @param sb The arrayList to save inorder
    */
    private void inOrderTraverse(AVLNode<E> node, int depth, ArrayList<E> arr) {
        if (node != null){
            inOrderTraverse((AVLNode<E>) node.left, depth + 1, arr);
            arr.add(node.data);
            inOrderTraverse((AVLNode<E>) node.right, depth + 1, arr);
        }
    }

    /**
     * Iterator class to iterate through elements.
     */
    private class Iter implements Iterator<E>{
        ArrayList<E> arr;
        int i = 0;
        
        /**
         * Initialize an ArrayList with the AVLTree element in increasing order.
         */
        public Iter(){
            arr = new ArrayList<>();
            inOrderTraverse((AVLNode<E>) root, 1, arr);
        }

        /**
         * Indicate whether movement is defined.
         * @return true if call to next will not throw an exception
         */
        @Override
        public boolean hasNext() {
            return(i < arr.size());
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

            return(arr.get(i++));
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
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
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
    public boolean remove(Object o) {
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
    public NavigableSet<E> descendingSet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        throw new UnsupportedOperationException();
    }
}