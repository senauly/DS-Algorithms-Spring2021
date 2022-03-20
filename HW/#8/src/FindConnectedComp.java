import graph.BreadthFirstSearch;
import graph.DepthFirstSearch;
import graph.Graph;

/**
 * Find number of connected components using 2 different search algorithms.
 * @author Sena Ulukaya
 */
public abstract class FindConnectedComp{
    
    private FindConnectedComp(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * Find number of connected components with the BFS algorithm.
     * @param graph
     * @return number of connected compnents.
     */
    public static int findConnectedBFS(Graph graph){
        int n = graph.getNumV();
        if(n == 0 || n == 1) return n;
        boolean[] found = new boolean[n];
        int[] parent = new int[n];        
        int count = 0;
        
        for (int i = 0; i < found.length; i++) {
            found[i] = false;
        }
        
        for (int i = 0; i < found.length; i++) {
            if(!found[i]){
                parent = BreadthFirstSearch.breadthFirstSearch(graph, i);
                count++;

                for (int j = 0; j < parent.length; j++) {
                    if(parent[j] != -1){
                        found[j] = true;
                    }
                }
                
                found[i] = true;
            }
        }

        return count;
    }

    /**
     * Find number of connected components with the DFS algorithm.
     * @param graph
     * @return number of connected compnents.
     */
    public static int findConnectedDFS(Graph graph){
        int n = graph.getNumV();
        if(n == 0 || n == 1) return n;
        boolean[] found = new boolean[n];
        int[] path = new int[n];        
        int count = 0;
        
        for (int i = 0; i < found.length; i++) {
            found[i] = false;
        }
        
        for (int i = 0; i < found.length; i++) {
            if(!found[i]){
                path = DepthFirstSearch.depthFirstSearch(graph, i);
                count++;

                for (int j = 0; j < path.length; j++) {
                    if(path[j] != -1) found[path[j]] = true;
                }
            }
        }

        return count;
    }
}
