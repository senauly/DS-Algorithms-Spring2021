package weightedgraph;

import java.util.*;

/**
 * A ListGraph is an extension of the AbstractGraph abstract class that uses an
 * array of lists to represent the edges.
 * 
 * @author Koffman and Wolfgang
 */

public class WeightedListGraph extends WeightedAbstractGraph {

  // Data Field
  /**
   * An array of Lists to contain the edges that originate with each vertex.
   */
  private List<WeightedEdge>[] edges;
  private int weightSize;
  /**
   * Construct a graph with the specified number of vertices and directionality.
   * 
   * @param numV     The number of vertices
   * @param directed The directionality flag
   */
  public WeightedListGraph(int numV, boolean directed, int weightSize) {
    super(numV, directed);
    this.weightSize = weightSize;
    edges = new List[numV];
    for (int i = 0; i < numV; i++) {
      edges[i] = new LinkedList<WeightedEdge>();
    }
  }

  /**
   * Determine whether an edge exists.
   * 
   * @param source The source vertex
   * @param dest   The destination vertex
   * @return true if there is an edge from source to dest
   */
  public boolean isEdge(int source, int dest) {
    return edges[source].contains(new WeightedEdge(source, dest, weightSize));
  }

  /**
   * Insert a new edge into the graph.
   * 
   * @param edge The new edge
   */
  public void insert(WeightedEdge edge) {
    edges[edge.getSource()].add(edge);
    if (!isDirected()) {
      edges[edge.getDest()].add(new WeightedEdge(edge.getDest(), edge.getSource(), edge.getWeights()));
    }
  }

  public Iterator<WeightedEdge> edgeIterator(int source) {
    return edges[source].iterator();
  }

  /**
   * Get the edge between two vertices. If an edge does not exist, an Edge with a
   * weight of Double.POSITIVE_INFINITY is returned.
   * 
   * @param source The source
   * @param dest   The destination
   * @return the edge between these two vertices
   */
  public WeightedEdge getEdge(int source, int dest) {
    WeightedEdge target = new WeightedEdge(source, dest, new ArrayList<>());
    for (WeightedEdge edge : edges[source]) {
      if (edge.equals(target))
        return edge; // Desired edge found, return it.
    }for(int i = 0; i < weightSize; ++i)
      target.getWeights().add(Double.POSITIVE_INFINITY);
    // Assert: All edges for source checked.
    return target; // Desired edge not found.
  }
}
