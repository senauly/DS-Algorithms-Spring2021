import java.util.Iterator;

import graph.DepthFirstSearch;
import graph.Edge;
import graph.Graph;

public abstract class ImportanceValue{
    private static int MIN_VALUE;
    private static boolean[] visited;

    private ImportanceValue(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * Calculate importance value of each vertex of the given graph.
     * @param graph graph
     * @return array with the importance value of each vertex
     */
    public static double[] importanceValue(Graph graph){
        if(graph.getNumV() < 3) throw new UnsupportedOperationException();
        double[] result = new double[graph.getNumV()];
        
        for (int i = 0; i < graph.getNumV(); i++) {
            
            result[i] = calculateImportanceValue(graph, i);
        }

        return result;
    }
    
    /**
     * Count number of shortest paths and number of shortest path with given vertex v recursively.
     * @param graph graph
     * @param count array counts number of shortest paths and number of shortest path with given vertex v
     * @param start start vertex
     * @param end end vertex
     * @param v given vertex intermediate
     * @param curr current vertex
     * @param length current lenghth of the path
     * @param isIntermediate if v is in the path true, false otherwise
     * @return number of shortest path with given vertex v/number of shortest paths
     */
    private static double countPaths(Graph graph, int[] count, int start, int end, int v, int curr, int length, boolean isIntermediate){
        if(curr == end){
            if(length < MIN_VALUE){
                MIN_VALUE = length;
                count[0] = 0;
                count[1] = 0;
            }

            if(length == MIN_VALUE){
                if(isIntermediate) count[1] += 1;
                count[0] += 1;
            }

            return 0.0;
        }
        
        if(curr == v) isIntermediate = true;

        Iterator<Edge> it = graph.edgeIterator(curr);
        visited[curr] = true;
    
        while(it.hasNext()){
            Edge e = it.next();
            if(!visited[e.getDest()]){
                countPaths(graph, count, start, end, v, e.getDest(), length+1, isIntermediate);
            }
        }

        visited[curr] = false;
        
        if(start == curr){
            if(count[0] == 0.0) throw new ArithmeticException();
            return((double)count[1]/(double)count[0]);
        }

        return 0.0;
    } 

    /**
     * Calculate importance value for a vertex.
     * @param g graph
     * @param v vertex
     * @return the importance value of the vertex.
     */
    private static double calculateImportanceValue(Graph g, int v){
        int[] connectedComp = DepthFirstSearch.depthFirstSearch(g, v);
        int count;
        for(count = 0; count < connectedComp.length && connectedComp[count] != -1; ++count);
        if(count < 3) return 0.0;
        double result = 0;
        for (int i = 0; i < count; i++){
            for (int j = i+1; j < count; j++){
                if(connectedComp[i] != v && connectedComp[j] != v){
                    try{
                        visited = new boolean[g.getNumV()];
                        MIN_VALUE = Integer.MAX_VALUE;
                        result += countPaths(g, new int[2], connectedComp[i], connectedComp[j], v, connectedComp[i], 0, false);
                    }

                    catch(ArithmeticException e){
                        result += 0.0;
                    }
                } 
            }
        }

        return(result/Math.pow((double)count,2.0));
    }
}
