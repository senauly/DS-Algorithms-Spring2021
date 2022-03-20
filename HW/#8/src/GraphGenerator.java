import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import graph.Edge;
import graph.Graph;
import graph.MatrixGraph;

/**
 * Create graphs with various sparsity.
 * @author Sena Ulukaya
 */
public abstract class GraphGenerator{
    protected static Random rand = new Random();
    protected static int numV;
    protected static int numE;
    protected static int componentSize = 0;
    
    private GraphGenerator(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create a graph.
     * @param n size
     * @return pair of graph and its component count
     */
    public static Pair<Graph,Integer> createGraph(int n){
        numV = n;
        numE = rand.nextInt(numV/2) + 1;
        
        Graph g = new MatrixGraph(numV, false);
        ArrayList<Integer> vertices = new ArrayList<>();
        
        for (int i = 0; i < numV; i++) {
            vertices.add(i);
        }

        Collections.shuffle(vertices);
        int temp = numV;
        int count = 0;
        int numComponent = 0;
        int totalEdge = 0;

        while(count != numV){
            if(temp == 1){
                numComponent++;
                break;
            }

            int componentSize = -1;
            do{
                componentSize = rand.nextInt(temp);
            } while(componentSize == 0);
            
            ArrayList<Integer> component = new ArrayList<>();
            for (int i = count; i < count + componentSize; i++) {
                component.add(vertices.get(i));
            }

            for (int i = 0; i < component.size()-1; i++) {
                g.insert(new Edge(component.get(i), component.get(i+1)));
                totalEdge++;
            }

            if(totalEdge < numE && componentSize != 1){
                int extraEdge = rand.nextInt(componentSize/2);
                for (int i = 0; i < extraEdge; i++){
                    int a = rand.nextInt(component.size());
                    int b;
                    do{
                        b = rand.nextInt(component.size());
                    } while(a == b);
                    
                    g.insert(new Edge(component.get(a), component.get(b)));
                    totalEdge++;
                }
            }

            count += componentSize;
            temp = numV - count;
            numComponent++;
        }

        return(new Pair<Graph,Integer>(g,numComponent));
    }
}
