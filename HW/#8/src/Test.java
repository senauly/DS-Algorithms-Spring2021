import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import graph.Graph;
import graph.Edge;
import graph.MatrixGraph;

import weightedgraph.WeightedEdge;
import weightedgraph.WeightedGraph;
import weightedgraph.WeightedListGraph;
import weightedgraph.WeightedMatrixGraph;

public abstract class Test{
    public static void part1(){
        WeightedGraph matrix = new WeightedMatrixGraph(5, false, 3);
        WeightedGraph list = new WeightedListGraph(5, false, 3);
    
        ArrayList<Double> w = new ArrayList<>();
        w.add(11.2);
        w.add(17.8);
        w.add(13.5);
        matrix.insert(new WeightedEdge(0, 1, w));
        list.insert(new WeightedEdge(0, 1, w));

        w.clear();
        w.add(21.2);
        w.add(27.8);
        w.add(23.5);
        matrix.insert(new WeightedEdge(1, 3, w));
        list.insert(new WeightedEdge(1, 3, w));

        w.clear();
        w.add(31.2);
        w.add(37.8);
        w.add(33.5);
        matrix.insert(new WeightedEdge(2, 4, w));
        list.insert(new WeightedEdge(2, 4, w));

        w.clear();
        w.add(31.2);
        w.add(37.8);
        w.add(33.5);
        matrix.insert(new WeightedEdge(4, 0, w));
        list.insert(new WeightedEdge(4, 0, w));

        w.clear();
        w.add(50.0);
        w.add(57.8);
        w.add(53.5);
        matrix.insert(new WeightedEdge(0, 2, w));
        list.insert(new WeightedEdge(0, 2, w));

        System.out.println("A new weighted matrix graph created with the 5 vertex and 3 weight values. Edges:\n");
        for (int i = 0; i < matrix.getNumV(); i++) {
            Iterator<WeightedEdge> it = matrix.edgeIterator(i);
            
            while(it.hasNext()){
                WeightedEdge e = it.next();
                System.out.println(e.toString());
            }
        }

        for (int i = 0; i < 3; i++) {
            part1_loop(matrix, i);
            System.out.println();
        }

        System.out.println("A new weighted list graph created with the 5 vertex and 3 weight values. Edges:\n");
        for (int i = 0; i < list.getNumV(); i++) {
            Iterator<WeightedEdge> it = list.edgeIterator(i);
            
            while(it.hasNext()){
                WeightedEdge e = it.next();
                System.out.println(e.toString());
            }
        }

        for (int i = 0; i < 3; i++) {
            part1_loop(list, i);
            System.out.println();
        }
    }

    private static void part1_loop(WeightedGraph matrix, int i){
        double[] dist = new double[matrix.getNumV()];
        int[] result = new int[matrix.getNumV()];
        try{
            result = DijkstrasAlgorithm.dijkstrasAlgorithm(matrix, dist, 0, i, new Operator(){
                @Override
                public double op(double a, double b){
                    return(a+b);
                }
            });
    
            System.out.println("\n\nDijkstra's algorithm parent array with the " + (i+1) + "th weight value and addition operator:(Start vertex:0)\n" + Arrays.toString(result));
            System.out.println("Dijkstra's algorithm distance array with the " + (i+1)  + "th weight value and addition operator:(Start vertex:0)\n" + Arrays.toString(dist));
        }

        catch(UnsupportedOperationException e){
            System.err.println("Operator is not associative.");
        }

        catch(NullPointerException e){
            System.err.println("Parameters can't be null.");
        }
        
        try{
            dist = new double[matrix.getNumV()];
            result = DijkstrasAlgorithm.dijkstrasAlgorithm(matrix, dist, 0, i, new Operator(){
                @Override
                public double op(double a, double b){
                    return(a*b);
                }
            });

            System.out.println("\n\nDijkstra's algorithm parent array with the " + (i+1)  + "th weight value and multiplicition operator:(Start vertex:0)\n" + Arrays.toString(result));
            System.out.println("Dijkstra's algorithm distance array with the " + (i+1)  + "th weight value and multiplicition operator:(Start vertex:0)\n" + Arrays.toString(dist));
        }

        catch(UnsupportedOperationException e){
            System.err.println("Operator is not associative.");
        }

        catch(NullPointerException e){
            System.err.println("Parameters can't be null.");
        }

        
        try{
            dist = new double[matrix.getNumV()];
            result = DijkstrasAlgorithm.dijkstrasAlgorithm(matrix, dist, 0, i, new Operator(){
                @Override
                public double op(double a, double b){
                    return((a+b)-(a*b));
                }
            });

            System.out.println("\n\nDijkstra's algorithm parent array with the " + (i+1)  + "th weight value and star operator:(Start vertex:0)\n" + Arrays.toString(result));
            System.out.println("Dijkstra's algorithm distance array with the " + (i+1)  + "th weight value and star operator:(Start vertex:0)\n" + Arrays.toString(dist));
        }

        catch(UnsupportedOperationException e){
            System.err.println("Operator is not associative.");
        }

        catch(NullPointerException e){
            System.err.println("Parameters can't be null.");
        }

        System.out.println("\n\nEXCEPTIONS:");
        System.out.println("\n\nDijkstra's algorithm with the non-associative(substraction) operator:\n");

        try{
            dist = new double[matrix.getNumV()];
            result = DijkstrasAlgorithm.dijkstrasAlgorithm(matrix, dist, 0, (i+1) , new Operator(){
            @Override
            public double op(double a, double b){
                return(a-b);
            }
        });

            System.out.println("\n\nDijkstra's algorithm parent array with the " + (i+1)  + "th weight value and non-associative(substraction) operator:(Start vertex:0)\n" + Arrays.toString(result));
            System.out.println("Dijkstra's algorithm distance array with the " + (i+1)  + "th weight value and non-associative(substraction) operator:(Start vertex:0)\n" + Arrays.toString(dist));
        }
        
        catch(UnsupportedOperationException e){
            System.err.println("Operator is not associative.");
        }

        catch(NullPointerException e){
            System.err.println("Parameters can't be null.");
        }
        
        System.out.println("\n\nDijkstra's algorithm with the null parameters:\n");
        try{
            dist = new double[matrix.getNumV()];
            result = DijkstrasAlgorithm.dijkstrasAlgorithm(null, null, 0, i, new Operator(){
                @Override
                public double op(double a, double b){
                    return(a+b);
                }
            });

            System.out.println("\n\nDijkstra's algorithm parent array with the " + (i+1)  + "th weight value and null parameters:(Start vertex:0)\n" + Arrays.toString(result));
            System.out.println("Dijkstra's algorithm distance array with the " + (i+1)  + "th weight value and null parameters:(Start vertex:0)\n" + Arrays.toString(dist));
        }
        
        catch(UnsupportedOperationException e){
            System.err.println("Operator is not associative.");
        }

        catch(NullPointerException e){
            System.err.println("Parameters can't be null.");
        }
    }
    public static void part2(){
        System.out.println("TEST THE ACCURACY OF THE METHODS\nNumber of Connected Components for Different Graphs:");
        
        for (int i = 0; i < 10; i++) {
            Pair<Graph,Integer> pair = GraphGenerator.createGraph(1000);
            Graph graph = pair.getFirst();
            int numC = pair.getSecond();
            System.out.println("\nGraphGenerator result:" + numC);
            System.out.println("BFS result:" + FindConnectedComp.findConnectedBFS(graph));
            System.out.println("DFS result:" + FindConnectedComp.findConnectedDFS(graph));
        }
        
        int[] size = {1000, 2000, 5000, 10000};
        System.out.println("\n\nRUNNING TIME COMPARISONS(ms)\nAverage of 10 Running Times of Each Method for Each Size:");
       
        for (int i = 0; i < size.length; i++) {
            double[] results = calculateRunTime(size[i]);
            System.out.println("\nFinding the number of connected components in a graph with a size of " + size[i] + ":\n");
            System.out.println("BFS:" + new DecimalFormat("##.##").format(results[0]));
            System.out.println("DFS:" + new DecimalFormat("##.##").format(results[1]));
        }
    }

    private static double[] calculateRunTime(int size){
        double[] startTime = new double[2];
        double[] timePassed = new double[2];
        double[] avgTime = new double[2];
        
        for (int i = 0; i < avgTime.length; i++) {
            avgTime[i] = 0;
        }
        
        for (int i = 0; i < 10; i++){
            Pair<Graph,Integer> pair = GraphGenerator.createGraph(size);
            Graph graph = pair.getFirst();
            startTime[0] = System.currentTimeMillis();
            FindConnectedComp.findConnectedBFS(graph);
            timePassed[0] = System.currentTimeMillis() - startTime[0];
            avgTime[0] += timePassed[0]/10;
            
            startTime[1] = System.currentTimeMillis();
            FindConnectedComp.findConnectedDFS(graph);
            timePassed[1] = System.currentTimeMillis() - startTime[1];
            avgTime[1] += timePassed[1]/10;
        }

        return avgTime;
    }

    public static void part3(){
        Graph g = new MatrixGraph(8, false);
        g.insert(new Edge(1,5));
        g.insert(new Edge(1,6));
        g.insert(new Edge(2,5));
        g.insert(new Edge(2,7));
        g.insert(new Edge(3,4));
        g.insert(new Edge(3,7));
        g.insert(new Edge(6,7));
        g.insert(new Edge(0,4));
        g.insert(new Edge(0,1));

        System.out.println("\n\nA new graph created with the 8 vertex. Edges:\n");
        for (int i = 0; i < g.getNumV(); i++) {
            Iterator<Edge> it = g.edgeIterator(i);
            
            while(it.hasNext()){
                Edge e = it.next();
                System.out.println(e.toString());
            }
        }

        System.out.println("Importance values are calculated for each vertex:\n");
        double[] values = new double[g.getNumV()];
        try{
            values = ImportanceValue.importanceValue(g);
            for (int i = 0; i < values.length; i++) {
                System.out.print("vertex " + i + ":\t");
                System.out.println(String.format("%.4f", values[i]));
            }
        }

        catch(UnsupportedOperationException e){
            System.out.println("Graph size should be at least 3 to calculate importance value.\n");
        }
        

        Graph g2 = new MatrixGraph(5, false);
        g2.insert(new Edge(1,4));
        g2.insert(new Edge(2,4));
        g2.insert(new Edge(3,2));
        g2.insert(new Edge(2,0));
        g2.insert(new Edge(0,1));


        System.out.println("\n\nA new graph created with the 5 vertex. Edges:\n");
        for (int i = 0; i < g2.getNumV(); i++) {
            Iterator<Edge> it = g2.edgeIterator(i);
            
            while(it.hasNext()){
                Edge e = it.next();
                System.out.println(e.toString());
            }
        }
        
        System.out.println("Importance values are calculated for each vertex:\n");
        try{
            values = ImportanceValue.importanceValue(g2);
            for (int i = 0; i < values.length; i++) {
                System.out.print("vertex " + i + ":\t");
                System.out.println(String.format("%.4f", values[i]));
            }
        }

        catch(UnsupportedOperationException e){
            System.out.println("Graph size should be at least 3 to calculate importance value.\n");
        }

        System.out.println("Test with graph size smaller than 3. We have at least 3 different vertex to calculate according to formula:\n");
        Graph g3 = new MatrixGraph(2, false);
        g3.insert(new Edge(1,0));

        try{
            values = ImportanceValue.importanceValue(g3);
            for (int i = 0; i < values.length; i++) {
                System.out.print("vertex " + i + ":\t");
                System.out.println(String.format("%.4f", values[i]));
            }
        }

        catch(UnsupportedOperationException e){
            System.out.println("Graph size should be at least 3 to calculate importance value.\n");
        }

        System.out.println("Importance values are calculated for each vertex with the 3 graph of size 100:\n");
        for (int i = 0; i < 3; i++) {
            Pair<Graph,Integer> pair = GraphGenerator.createGraph(100);
            Graph graph = pair.getFirst();
            try{
                ImportanceValue.importanceValue(graph);
                System.out.println("Succesfully tested.");
            }

            catch(UnsupportedOperationException e){
                System.out.println("Graph size should be at least 3 to calculate importance value.\n");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("\n\nPART1: Generalization the implementation of Dijkstra's algorithm\n\n");
        part1();
        System.out.println("\n\nPART2: Finding the number of connected components in a graph\n\n");
        part2();
        System.out.println("\n\nPART3: The importance of a vertex\n\n");
        part3();
    }
}
