import java.util.LinkedList;
import java.util.ListIterator;

public class Category{
    LinkedList<String> categoryTree;
    int size = 0;

    public Category() {
        categoryTree = new LinkedList<>();
    }

    /**
     * add sub-category.
     * @param str to remove
     * @return true if succesfull
     */
    public boolean add(String str){
        if(categoryTree.add(str)){
            size++;
            return true;
        }

        return false;
    }
    /**
     * remove sub-category.
     * @param str to remove
     * @return true if succesfull
     */
    public boolean remove(String str){
        if(categoryTree.remove(str)){
            size--;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        int count = 0;
        ListIterator<String> it = categoryTree.listIterator();
        StringBuilder sb = new StringBuilder();
        sb.append("\"[\"\"");
        while(it.hasNext()){
            sb.append(it.next());
            count++;
            if(count != size) sb.append(" >> ");
        }

        sb.append("\"\"]\"");
        return(sb.toString());
    }
}
