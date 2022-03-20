package graph;
import java.util.*;

/** Class to implement the depth-first search algorithm.
*   @author Koffman and Wolfgang
* */

public abstract class DepthFirstSearch{
  protected static int[] discoveryOrder;
  protected static int[] finishOrder;
  protected static boolean[] visited;
  protected static int discoverIndex = 0;
  protected static int finishIndex = 0;

  private DepthFirstSearch(){
    throw new IllegalStateException("Utility class");
  }
  
  /**
   * depth-first search the graph starting at the given index.
   * @param graph graph
   * @param start The start vertex
   */
  public static int[] depthFirstSearch(Graph graph, int start){
    int n = graph.getNumV();
    visited = new boolean[n];
    discoveryOrder = new int[n];
    finishOrder = new int[n];
    discoverIndex = 0;
    finishIndex = 0;

    for (int i = 0; i < finishOrder.length; i++) {
      finishOrder[i] = -1;
    }

    depthFirstSearchRec(graph, start);
    return finishOrder;
  }
  
  /** Recursively depth-first search the graph
      starting at vertex current.
      @param current The start vertex
   */
  private static void depthFirstSearchRec(Graph graph, int current) {
    /* Mark the current vertex visited. */
    visited[current] = true;
    discoveryOrder[discoverIndex++] = current;
    /* Examine each vertex adjacent to the current vertex */
    Iterator < Edge > itr = graph.edgeIterator(current);
    while (itr.hasNext()) {
      int neighbor = itr.next().getDest();
      /* Process a neighbor that has not been visited */
      if (!visited[neighbor]) {
        /* Recursively apply the algorithm
           starting at neighbor. */
        depthFirstSearchRec(graph,neighbor);
      }
    }
    /* Mark current finished. */
    finishOrder[finishIndex++] = current;
  }
}
