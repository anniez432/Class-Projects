// === CS400 File Header Information ===
// Name: Annie Zhao
// Email: azhao37@wisc.edu
// Lecturer: Professor Florian Heimerl

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     */
    public DijkstraGraph() {
        super(new HashtableMap<>());
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        //if the start and end nodes aren't in the graph throw an exception
        if(!this.containsNode(start) || !this.containsNode(end)){
            throw new NoSuchElementException("The provided nodes to not correspond to data in the graph's nodes.");
        }

        //create a map with the shortest paths of each node
        MapADT<Node, Double> shortPaths = new HashtableMap<>();
        //create a map with visited nodes
        MapADT<Node, Boolean> visitedNodes = new HashtableMap<>();

        //use priority queue to explore shorter paths
        PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>(SearchNode::compareTo);

        //creates the start node with the start data
        Node startNode = nodes.get(start);
        SearchNode startSearchNode = new SearchNode(startNode, 0.0, null);
        //add the start search node to the priority queue
        priorityQueue.add(startSearchNode);

        //add the first path into the short paths
        shortPaths.put(startNode, 0.0);


        //start loop of when the priority queue isn't empty
        while(!priorityQueue.isEmpty()) {
            SearchNode currentNode = priorityQueue.remove();

            //if this node is already visited, skip rest of loop
            if (visitedNodes.containsKey(currentNode.node)) continue;

            //mark it as visited if not
            visitedNodes.put(currentNode.node, true);

            //if it is the end node, return the current node (endpoint)
            if (currentNode.node.data.equals(end)) return currentNode;

            //insert each edge to unvisited nodes into the pq
            for (Edge neighborEdge : currentNode.node.edgesLeaving) {
                //the neighbor node is the successor of the edge
                Node neighbor = neighborEdge.successor;

                //new cost is the past path cost + this edge's cost
                double newCost = currentNode.cost + neighborEdge.data.doubleValue();

                //if the neighbor is already in shortpaths and this new cost is less than that path's cost,
                //and the node hasn't been visied yet, replace the neighbor's path in shortpaths and add the
                //neighbor to the PQ
                if ((shortPaths.containsKey(neighbor) && newCost < shortPaths.get(neighbor)) && !visitedNodes.containsKey(neighbor)) {
                    shortPaths.remove(neighbor);
                    shortPaths.put(neighbor, newCost);
                    priorityQueue.add(new SearchNode(neighbor, newCost, currentNode));
                }
                //otherwise, if the neighbor hasn't been visited yet and it's not in short paths,
                //it can just be added to shortpaths and the PQ
                else if (!visitedNodes.containsKey(neighbor) && !shortPaths.containsKey(neighbor)) {
                    //if lowmap doesn't contain the neighbor yet, then add it to pq
                    shortPaths.put(neighbor, newCost);
                    priorityQueue.add(new SearchNode(neighbor, newCost, currentNode));
                }

            }
        }
        //getting to this point means a path to the end wasn't found - throw an exception
       throw new NoSuchElementException("No path is available to be returned.");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        //if the nodes are not in the graph throw the exception
        if(!this.containsNode(start) || !this.containsNode(end)){
            throw new NoSuchElementException("The provided nodes to not correspond to data in the graph's nodes.");
        }
        //find the current node (the end node of the shortest path)
        SearchNode currentNode = computeShortestPath(start, end);

        //create a list for the nodes along the path
        List<NodeType> pathNodes = new ArrayList<>();

        //for all valid nodes, add the node to the list
        //change current node to its predecessor
        while(currentNode != null){
            pathNodes.add(currentNode.node.data);
            currentNode = currentNode.predecessor;
        }

        //reverse the list of nodes so it goes from start to finish
        Collections.reverse(pathNodes);

        //return the list of nodes
        return pathNodes;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        //throw the exception if either nodes are not in the graph
        if(!this.containsNode(start) || !this.containsNode(end)){
            throw new NoSuchElementException("The provided nodes to not correspond to data in the graph's nodes.");
        }

        //the shortest path's cost is recorded in the result of the shortest path
        SearchNode endSearchNode = computeShortestPath(start, end);
        return endSearchNode.cost;
    }


    /**
     * This test uses the example from Florian's lecture to determine if this class correctly
     * determines the shortest paths between various sequences of nodes and their costs (the first
     * two required tests).
     */
    @Test
    public void dijkstra_test1() {
        //example from lecture

        //instantiate graph, nodes, and edges
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4.0);
        graph.insertEdge("A", "E", 15.0);
        graph.insertEdge("A", "C", 2.0);

        graph.insertEdge("B", "E", 10.0);
        graph.insertEdge("B", "D", 1.0);

        graph.insertEdge("C", "D", 5.0);

        graph.insertEdge("D", "E", 3.0);
        graph.insertEdge("D", "F", 0.0);

        graph.insertEdge("F", "D", 2.0);
        graph.insertEdge("F", "H", 4.0);

        graph.insertEdge("G", "H", 4.0);


        /*
        //for a path from A to E, the ending node should be E with its predecessor on that path as D
        Assertions.assertEquals(graph.computeShortestPath("A", "E").node.data, "E");
        Assertions.assertEquals(graph.computeShortestPath("A", "E").predecessor.node.data, "D");

        //for a path from A to F, the ending node should be F with its predecessor on that path as D
        Assertions.assertEquals(graph.computeShortestPath("A", "F").node.data, "F");
        Assertions.assertEquals(graph.computeShortestPath("A", "F").predecessor.node.data, "D");

        //for a path from A to F, the ending node should be F with its predecessor on that path as H
        Assertions.assertEquals(graph.computeShortestPath("A", "G").node.data, "G");
        Assertions.assertEquals(graph.computeShortestPath("A", "G").predecessor.node.data, "H");


        //determine if the nodes along the shortest path of A to E are as expected
         */


        List<String> expected = new ArrayList<>();
        expected.add("A");
        expected.add("B");
        expected.add("D");
        expected.add("E");
        Assertions.assertEquals(graph.shortestPathData("A", "E"), expected);

        //determine if the nodes along the shortest path of A to F are as expected
        expected.remove("E");
        expected.add("F");
        Assertions.assertEquals(graph.shortestPathData("A", "F"), expected);

        Assertions.assertEquals(graph.shortestPathCost("A", "E"), 8.0);
        Assertions.assertEquals(graph.shortestPathCost("A", "F"), 5.0);

    }

    /**
     * This test determines if an exception is correctly thrown when an attempt to find a path between
     * existing nodes but without a sequence of connecting edges is made.
     */
    @Test
    public void dijkstra_graph2(){
        //instantiate graph, nodes, and edges
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4.0);
        graph.insertEdge("A", "C", 2.0);

        graph.insertEdge("B", "E", 10.0);
        graph.insertEdge("B", "D", 3.0);

        graph.insertEdge("C", "D", 5.0);

        graph.insertEdge("D", "F", 0.0);

        graph.insertEdge("E", "G", 15.0);

        graph.insertEdge("F", "H", 4.0);

        //trying to find a non-existent path should call a NoSuchElementException with a message
        try {
            graph.computeShortestPath("H", "A");
            Assertions.fail();
        } catch (NoSuchElementException e){
            if(e.getMessage() == null || e.getMessage().isBlank()){
                Assertions.fail();
            }
            System.out.println(e.getMessage());
        } catch (Exception e){
            if(e.getMessage() == null || e.getMessage().isBlank()){
                Assertions.fail();
            }
            System.out.println(e.getMessage());
            Assertions.fail();
        }
    }

    /**
     * This test determines if the implemented Dijkstra's Algorithm correctly determines the shortest
     * path between nodes. Uses a similar graph to the first test, but with a few different edges and
     * path direction.
     */
    @Test
    public void dijkstra_test3(){
        //instantiate graph, nodes, and edges
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>();

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        graph.insertEdge("A", "B", 4.0);
        graph.insertEdge("A", "E", 15.0);
        graph.insertEdge("A", "C", 2.0);

        graph.insertEdge("B", "E", 10.0);
        graph.insertEdge("B", "D", 1.0);

        graph.insertEdge("C", "D", 5.0);

        graph.insertEdge("D", "E", 3.0);
        graph.insertEdge("D", "F", 0.0);
        graph.insertEdge("D", "B", 0.0);

        graph.insertEdge("F", "D", 0.0);
        graph.insertEdge("F", "H", 4.0);

        graph.insertEdge("G", "H", 4.0);

        graph.insertEdge("H", "F", 4.0);

        //the shortest path between H and E should end on node E, with its predecessor on that path as D
        Assertions.assertEquals(graph.computeShortestPath("H", "E").node.data, "E");
        Assertions.assertEquals(graph.computeShortestPath("H", "E").predecessor.node.data, "D");

        //determine if the nodes along the shortest path are as expected
        ArrayList<String> expected = new ArrayList<>();
        expected.add("H");
        expected.add("F");
        expected.add("D");
        expected.add("E");

        Assertions.assertEquals(graph.shortestPathData("H", "E"), expected);

        //the cost of this shortest path should be 7.0
        Assertions.assertEquals(graph.shortestPathCost("H", "E"), 7.0);
    }
}
