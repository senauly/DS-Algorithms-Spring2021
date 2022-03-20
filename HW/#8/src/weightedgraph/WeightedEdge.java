package weightedgraph;

import java.util.ArrayList;

/**
 * An Edge represents a relationship between two vertices.
 * 
 * @author Koffman and Wolfgang
 */

public class WeightedEdge{
  // Data Fields
  /** The source vertix */
  private int source;

  /** The destination vertix */
  private int dest;

  /** The weight */
  private ArrayList<Double> weights;
  public int weightSize;
  
  /**
   * Construct a weighted edge with a source of from and a destination of to. Set
   * the weights positive infinity.
   * @param source
   * @param dest
   * @param weightSize
   */
  public WeightedEdge(int source, int dest, int weightSize){
    this.source = source;
    this.dest = dest;

    if(weightSize < 0) throw new ArrayIndexOutOfBoundsException();
    this.weightSize = weightSize;
    this.weights = new ArrayList<>(weightSize);

    for (int i = 0; i < weightSize; i++) {
      weights.add(Double.POSITIVE_INFINITY);
    }
  }
  
  /**
   * Construct a weighted edge with a source of from and a destination of to. Set
   * the weights arraylist with the given arraylist.
   * 
   * @param source - The source vertix
   * @param dest   - The destination vertix
   * @param w      - The weight
   */
  public WeightedEdge(int source, int dest, ArrayList<Double> w) {
    this.source = source;
    this.dest = dest;
    this.weights = new ArrayList<>(w);
    this.weightSize = w.size();
  }

  // Methods
  /**
   * Get the source
   * 
   * @return The value of source
   */
  public int getSource() {
    return source;
  }

  /**
   * Get the destination
   * 
   * @return The value of dest
   */
  public int getDest() {
    return dest;
  }

  public ArrayList<Double> getWeights() {
    return this.weights;
  }

  public int getWeightSize() {
    return this.weightSize;
  }

  /**
   * Get the weight
   * 
   * @return the value of weight
   */
  public double getWeight(int index) {
    if(index < 0 || index >= weights.size()) throw new ArrayIndexOutOfBoundsException(index + " " + weights.size());
    return weights.get(index);
  }

  /**
   * Return a String representation of the edge
   * 
   * @return A String representation of the edge
   */
  public String toString() {
    StringBuffer sb = new StringBuffer("[(");
    sb.append(Integer.toString(source));
    sb.append(", ");
    sb.append(Integer.toString(dest));
    sb.append("): ");
    sb.append(weights.toString());
    sb.append("]");
    return sb.toString();
  }

  /**
   * Return true if two edges are equal. Edges are equal if the source and
   * destination are equal. Weight is not conidered.
   * 
   * @param obj The object to compare to
   * @return true if the edges have the same source and destination
   */
  public boolean equals(Object obj) {
    if (obj instanceof WeightedEdge) {
      WeightedEdge edge = (WeightedEdge) obj;
      return (source == edge.getSource() && dest == edge.getDest());
    } else {
      return false;
    }
  }

  /**
   * Return a hash code for an edge. The hash code is the source shifted left 16
   * bits exclusive or with the dest
   * 
   * @return a hash code for an edge
   */
  public int hashCode() {
    return (source << 16) ^ dest;
  }
}
