import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class CalculateRunTime{
    public static final Random rand = new Random();
    public static final int BOUND_MULTI = 1000;

    /**
     * Add unique items to arraylist.
     * @param dataSize data size
     * @return arraylist
     */
    public static ArrayList<Integer> addItem(int dataSize){
        HashSet<Integer> set = new HashSet<>();

        while(set.size() != dataSize+100){
           set.add(rand.nextInt(dataSize*BOUND_MULTI));
        }

        ArrayList<Integer> arr = new ArrayList<>(List.copyOf(set));
        Collections.shuffle(arr);
        return arr;
    }

    /**
     * Calculate average running time
     * @param dataSize data size
     * @return average running time
     */
    public static long calculateBST(int dataSize){
        long startTime;
        long timePassed;
        long avgTime = 0;
        
        for (int i = 0; i < 10; i++){
            ArrayList<Integer> arr = addItem(dataSize);
            int[] extra = new int[100];
            int k = 0;
            BinarySearchTree<Integer> o = new BinarySearchTree<>();
            
            for (int j = 0; j < dataSize+100; j++) {
                if(j < dataSize) o.add(arr.get(j));
                else extra[k++] = arr.get(j);
            }

            startTime = System.nanoTime();
            for (int j = 0; j < extra.length; j++) {
                o.add(extra[j]);
            }
            timePassed = System.nanoTime() - startTime;
            avgTime += timePassed/10;
        }

        return avgTime;
    }

    /**
     * Calculate average running time
     * @param dataSize data size
     * @return average running time
     */
    public static long calculateRBT(int dataSize){
        long startTime;
        long timePassed;
        long avgTime = 0;
        
        for (int i = 0; i < 10; i++){
            ArrayList<Integer> arr = addItem(dataSize);
            int[] extra = new int[100];
            int k = 0;
            RedBlackTree<Integer> o = new RedBlackTree<>();
            
            for (int j = 0; j < dataSize+100; j++) {
                if(j < dataSize) o.add(arr.get(j));
                else extra[k++] = arr.get(j);
            }

            startTime = System.nanoTime();
            for (int j = 0; j < extra.length; j++) {
                o.add(extra[j]);
            }
            timePassed = System.nanoTime() - startTime;
            avgTime += timePassed/10;
        }

        return avgTime;
    }

    /**
     * Calculate average running time
     * @param dataSize data size
     * @return average running time
     */
    public static long calculateTTT(int dataSize){
        long startTime;
        long timePassed;
        long avgTime = 0;
        
        for (int i = 0; i < 10; i++){
            ArrayList<Integer> arr = addItem(dataSize);
            int[] extra = new int[100];
            int k = 0;
            TwoThreeTree<Integer> o = new TwoThreeTree<>();
            
            for (int j = 0; j < dataSize+100; j++) {
                if(j < dataSize) o.insert(arr.get(j));
                else extra[k++] = arr.get(j);
            }

            startTime = System.nanoTime();
            for (int j = 0; j < extra.length; j++) {
                o.insert(extra[j]);
            }
            timePassed = System.nanoTime() - startTime;
            avgTime += timePassed/10;
        }

        return avgTime;
    }

    /**
     * Calculate average running time
     * @param dataSize data size
     * @return average running time
     */
    public static long calculateBT(int dataSize){
        long startTime;
        long timePassed;
        long avgTime = 0;
        
        for (int i = 0; i < 10; i++){
            ArrayList<Integer> arr = addItem(dataSize);
            int[] extra = new int[100];
            int k = 0;
            BTree<Integer> o = new BTree<>(50);
            
            for (int j = 0; j < dataSize+100; j++) {
                if(j < dataSize) o.insert(arr.get(j));
                else extra[k++] = arr.get(j);
            }

            startTime = System.nanoTime();
            for (int j = 0; j < extra.length; j++) {
                o.insert(extra[j]);
            }
            timePassed = System.nanoTime() - startTime;
            avgTime += timePassed/10;
        }

        return avgTime;
    }
    
    /**
     * Calculate average running time
     * @param dataSize data size
     * @return average running time
     */
    public static long calculateSL(int dataSize){
        long startTime;
        long timePassed;
        long avgTime = 0;
        
        for (int i = 0; i < 10; i++){
            ArrayList<Integer> arr = addItem(dataSize);
            int[] extra = new int[100];
            int k = 0;
            SkipList<Integer> o = new SkipList<>();
            
            for (int j = 0; j < dataSize+100; j++) {
                if(j < dataSize) o.insert(arr.get(j));
                else extra[k++] = arr.get(j);
            }

            startTime = System.nanoTime();
            for (int j = 0; j < extra.length; j++) {
                o.insert(extra[j]);
            }
            timePassed = System.nanoTime() - startTime;
            avgTime += timePassed/10;
        }

        return avgTime;
    }
}
