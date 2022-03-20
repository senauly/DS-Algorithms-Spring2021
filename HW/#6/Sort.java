import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public abstract class Sort{

    /**
     * Apply merge sort algorithm.
     * @param <T> compared type
     * @param arr to sort
     * @param cp comparator to compare according to request.
     */
    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> arr, Comparator<T> cp){
        if(arr.size() > 1){
            int halfSize = arr.size()/2;

            ArrayList<T> left = new ArrayList<>();
            ArrayList<T> right = new ArrayList<>();
            
            for (int i = 0; i < halfSize; i++) {
                left.add(arr.get(i));
            }

            for (int i = halfSize; i < arr.size(); i++) {
                right.add(arr.get(i));
            }
           
            mergeSort(left,cp);
            mergeSort(right,cp);
            arr.clear();
            merge(left,right,arr,cp);
        }
    }

    /**
     * Merge 2 arraylists into the another one with an increasing order.
     * @param <T> compared type
     * @param left arraylist
     * @param right arraylist
     * @param result arraylist
     * @param comp comparator to compare according to request.
     */
    private static <T extends Comparable<T>> void merge(ArrayList<T> left, ArrayList<T> right, ArrayList<T> result, Comparator<T> comp){
        int i = 0;
        int j = 0;
        
        while(i < left.size() && j < right.size()){
            if(comp.compare(left.get(i), right.get(j)) < 0) result.add(left.get(i++));
            else result.add(right.get(j++));
        }

        for (;i < left.size(); i++){
            result.add(left.get(i));
        }


        for (;j < right.size(); j++){
            result.add(right.get(j));
        }
    }

    /**
     * Apply quick sort algorithm.
     * @param <T> compared type
     * @param arr to sort
     * @param cp comparator to compare according to request.
     */
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> arr, Comparator<T> cp){
        quickSort(arr,0,arr.size()-1,cp);
    }
    private static <T extends Comparable<T>> void quickSort(ArrayList<T> arr, int first, int last, Comparator<T> cp){
        if(first < last){
            int piv = partition(arr, first, last,cp);

            quickSort(arr, first, piv - 1, cp);
            quickSort(arr, piv + 1, last, cp);
        }
    }
    
    /**
     * values from first to pivot are less than or equal to the pivot value, and values from pivot to last are greater than the pivot value.
     * @param <T> compared type
     * @param arr to sort
     * @param first lower range
     * @param last upper lange
     * @param cp comparator to compare according to request.
     * @return pivot's location
     */
    private static <T extends Comparable<T>> int partition(ArrayList<T> arr, int first, int last, Comparator<T> cp){
        T pivot = arr.get(first);
        int up = first;
        int down = last;

        do{
            while(up < last &&  cp.compare(arr.get(up), pivot) <= 0) up++;
            while(cp.compare(arr.get(down), pivot) > 0) down--;
           
            if(up < down){
                T temp = arr.get(up);
                arr.set(up,arr.get(down));
                arr.set(down,temp);
            }

        } while(up < down);

        T temp = arr.get(first);
        arr.set(first,arr.get(down));
        arr.set(down,temp);

        return down;
    }

    /**
     * Apply insertion sort algorithm
     * @param <T> compared type
     * @param arr to sort 
     * @param comp comparator to compare according to request.
     */
    public static <T extends Comparable<T>> void insertionSort(ArrayList<T> arr, Comparator<T> comp){

        for(int i = 0; i < arr.size(); i++){
            insert(arr,i,comp);
        }
    } 

    /**
     * Insert the values, where they should be.
     * @param <T> compared type
     * @param arr to sort 
     * @param pos position of the element
     * @param comp comparator to compare according to request.
     */
    private static <T extends Comparable<T>> void insert(ArrayList<T> arr,int pos, Comparator<T> comp){
        T key = arr.get(pos);
        
        while(pos > 0 && comp.compare(arr.get(pos-1), key) > 0){
            arr.set(pos, arr.get(pos-1));
            pos--;
        }
        
        arr.set(pos,key);
    }
}
