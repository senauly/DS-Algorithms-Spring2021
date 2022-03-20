import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;

public abstract class Test{
    public static final Random rand = new Random();
    
    public static void part1_1(){
        NavigableSet<Integer> sl = new MySkipList<>();
        Set<Integer> set = new HashSet<>();

        while(set.size() != 10000){
            set.add(rand.nextInt(100000));
        }
        
        System.out.println("\n\nADD TEST:\n\n");
        ArrayList<Integer> arr = new ArrayList<>(List.copyOf(set));
        Collections.shuffle(arr);
        System.out.println("size: " + sl.size());
        System.out.println("Adding 10.000 unique elements to MySkipList:(There will be error message if unsuccesful)\n");
        for (int i = 0; i < arr.size(); i++) {
            try{
                if(!sl.add(arr.get(i))) System.out.println("Adding is unsuccesfull.");
            }

            catch(NullPointerException e){
                System.err.println("You can't add null elements.");
            }
        }

        System.out.println("size: " + sl.size());
        System.out.println("Adding same number 5 times to MySkipList:\n");
        for (int i = 0; i < 5; i++) {
            try{
                if(sl.add(-100)){
                    System.out.println("Adding is succesfull.");
                    arr.add(-100);
                } 
                else System.out.println("Adding is unsuccesfull.");
            }

            catch(NullPointerException e){
                System.err.println("You can't add null elements.");
            }
        }

        System.out.println("size: " + sl.size());
        System.out.println("\nAdding null element to MySkipList:\n");
        
        try{
            if(sl.add(null)) System.out.println("Adding is succesfull.");
            else System.out.println("Adding is unsuccesfull.");
        }

        catch(NullPointerException e){
            System.err.println("You can't add null elements.");
        }

        System.out.println("\n\nREMOVE TEST:\n\n");
        System.out.println("size: " + sl.size());
        System.out.println("Removing non-existing elements from MySkipList:\n");
        
        for (int i = 0; i <5; i++) {
            try{
                if(sl.remove(200001)) System.out.println("Removing is succesfull.");
                else System.out.println("Removing is unsuccesfull.");
            }

            catch(NullPointerException e){
                System.err.println("Element can't be null.");
            }
        }
        System.out.println("size: " + sl.size());
        System.out.println("\nRemoving all numbers from MySkipList:(There will be error message if unsuccesful)\n");

        for (int i = 0; i < arr.size(); i++) {
            try{
                if(!sl.remove(arr.get(i))) System.out.println("Removing is unsuccesfull.");
            }

            catch(NullPointerException e){
                System.err.println("Element can't be null.");
            }
        }

        System.out.println("size: " + sl.size());
        System.out.println("\n\nDESCENDING ITERATOR TEST:\n\n");
        NavigableSet<Integer> sl2 = new MySkipList<>();
        
        for (int j = 0; j <= 30; j++) {
            sl2.add(j);
        }
        System.out.println("Numbers between 0-30 added.");
        System.out.println("SkipList Elements with ToString: " + sl2.toString());

        Iterator<Integer> it = sl2.descendingIterator();
        System.out.println("SkipList Elements with descendingIterator: ");
        
        while(it.hasNext()){
            System.out.println(it.next());
        }

        System.out.println("\nRemoving number 12 with descendingIterator: ");
        it = sl2.descendingIterator();
        while(it.hasNext()){
            int temp = it.next();
            if(temp == 12) it.remove();
        }

        System.out.println("SkipList Elements with ToString: " + sl2.toString());
    }

    public static void part1_2(){
        System.out.println("\n\nADDING DEMONSTRATION\n\n");
        NavigableSet<String> avl = new MyAVLTree<>();
    
        avl.add("The");
        System.out.println(avl.toString());
        avl.add("quick");
        System.out.println(avl.toString());
        avl.add("brown");
        System.out.println(avl.toString());
        avl.add("fox");
        System.out.println(avl.toString());
        avl.add("jumps");
        System.out.println(avl.toString());
        avl.add("over");
        System.out.println(avl.toString());
        avl.add("the");
        System.out.println(avl.toString());
        avl.add("lazy");
        System.out.println(avl.toString());
        avl.add("dog");
        System.out.println(avl.toString());

        NavigableSet<Integer> avl2 = new MyAVLTree<>();

        System.out.println("\n\nADD TEST:\n\n");
        Set<Integer> set = new HashSet<>();
        while(set.size() != 10000){
            set.add(rand.nextInt(100000));
        }
        
        ArrayList<Integer> arr = new ArrayList<>(List.copyOf(set));
        Collections.shuffle(arr);
        System.out.println("Adding 10.000 unique elements to MyAVLTree:(There will be error message if unsuccesful)\n");
        for (int i = 0; i < arr.size(); i++) {
            try{
                if(!avl2.add(arr.get(i))) System.out.println("Adding is unsuccesfull.");
            }

            catch(NullPointerException e){
                System.err.println("You can't add null elements.");
            }
        }
        System.out.println("Adding same number 5 times to MyAVLTree:\n");
        for (int i = 0; i < 5; i++) {
            try{
                if(avl2.add(-100)) System.out.println("Adding is succesfull.");
                else System.out.println("Adding is unsuccesfull.");
            }

            catch(NullPointerException e){
                System.err.println("You can't add null elements.");
            }
        }
        System.out.println("\nAdding null element to MyAVLTree:\n");
        
        try{
            if(avl2.add(null)) System.out.println("Adding is succesfull.");
            else System.out.println("Adding is unsuccesfull.");
        }

        catch(NullPointerException e){
            System.err.println("You can't add null elements.");
        }

        NavigableSet<Integer> avl3 = new MyAVLTree<>();
        System.out.println("\n\nHEAD SET AND TAIL SET:\n\n");
        System.out.println("Added numbers between 0-30.");
        for (int i = 0; i <= 30; i++) {
            try{
                avl3.add(i);
            }

            catch(NullPointerException e){
                System.err.println("You can't add null elements.");
            }
        }

        System.out.println("Head Set with the given element 12 excluded:\n" + avl3.headSet(12).toString());
        System.out.println("\nHead Set with the given element 12 included:\n" + avl3.headSet(12,true).toString());
        System.out.println("\nTail Set with the given element 17 excluded:\n" + avl3.tailSet(17,false).toString());
        System.out.println("\nTail Set with the given element 17 included:\n" + avl3.tailSet(17).toString());
    }
    
    public static void part2(){
        BinarySearchTree<Integer> avl = new MyAVLTree<>();
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        BinarySearchTree<Integer> rbt = new RedBlackTree<>();
        BinarySearchTree<Integer>[] trees = new BinarySearchTree[3];
        trees[0] = avl;
        trees[1] = bst;
        trees[2] = rbt;

        int[] arr = {5,10,15,17,25,42};
        System.out.println("\n\nAdding 5-10-15-17-25-42 in order to the AVLTree, BinarySearchTree and RedBlackTree:\n\n");
        
        for (int i = 0; i < trees.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                trees[i].add(arr[j]);
            }
        }
        
        System.out.println("Checking whether AVLtree or RedBlackTree with my function:\n");
        for (int i = 0; i < trees.length; i++) {
            if(CompareTree.whichTree(trees[i]) == 0) System.out.println("It is a AVL Tree.");
            else if(CompareTree.whichTree(trees[i]) == 1) System.out.println("It is a Red Black Tree.");
            else System.out.println("It is neither of them");
        }

        System.out.println("\nChecking with instance of function to compare:\n");
        for (int i = 0; i < trees.length; i++) {
            if(trees[i] instanceof MyAVLTree) System.out.println("It is a AVL Tree.");
            else if(trees[i] instanceof RedBlackTree) System.out.println("It is a Red Black Tree.");
            else System.out.println("It is neither of them");
        }
    }

    public static void part3(){
        int[] times = {10000, 20000, 40000, 80000};
        
        CalculateRunTime.calculateBST(10000);
        CalculateRunTime.calculateRBT(10000);
        CalculateRunTime.calculateBT(10000);
        CalculateRunTime.calculateSL(10000);
        CalculateRunTime.calculateTTT(10000);

        System.out.println("RUNNING TIME COMPARISONS (nanosecond)");
        for (int i = 0; i < times.length; i++) {
            System.out.println("\nAdding 100 elements with data size of " + times[i] + ":\n");
            System.out.println("Binary Search Tree:" + CalculateRunTime.calculateBST(times[i]));
            System.out.println("Red-Black Tree:" + CalculateRunTime.calculateRBT(times[i]));
            System.out.println("2-3 Tree:" + CalculateRunTime.calculateTTT(times[i]));
            System.out.println("B Tree:" + CalculateRunTime.calculateBT(times[i]));
            System.out.println("Skip List:" + CalculateRunTime.calculateSL(times[i]));
        }
    }
    
    public static void main(String[] args) {
        System.out.println("\n\n------ PART1: SKIPLIST ------\n\n");
        part1_1();
        System.out.println("\n\n------ PART1: AVLTREE ------\n\n");
        part1_2();
        System.out.println("\n\n------ PART2: AVLTREE OR REDBLACK TREE ------\n\n");
        part2();
        System.out.println("\n\n------ PART3: RUN TIME COMPARISONS ------\n\n");
        part3();
    }
}
