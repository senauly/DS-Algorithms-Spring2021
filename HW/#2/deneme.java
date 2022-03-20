import java.util.ArrayList;

public class deneme{
    public static void main(String[] args) {
        ArrayList<Integer> array1 = new ArrayList<>(7);
        ArrayList<Integer> array2 = new ArrayList<>(7);
        ArrayList<Integer> list = new ArrayList<>(14);

        int count = 0;
        array1.add(a);
        array2.add(b);

        int i = 0;
        int j = 0;
        while (i < array1.size() && j < array2.size()) {
            if (array1.get(i) < array2.get(j)) {
                list.add(array1.get(i));
                i++;
                
            } 
            
            else {
                list.add(array2.get(j));
                j++;
            }
            count++;
        }
        System.out.println(count);

        count = 0;
        while (i < array1.size()) {
            list.add(array1.get(i));
            i++;
            count++;
        }
        System.out.println(count);
        count = 0;
        while (j < array2.size()) {
            list.add(array2.get(j));
            j++;
            count++;
        }
        System.out.println(count);

        for (int j2 = 0; j2 < list.size(); j2++) {
            System.out.print(list.get(j2) + " ");
        }
    }
}