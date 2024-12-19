// === CS400 File Header Information ===
// Email: bisom@wisc.edu
// All credits for this file go to the student above
// Lecturer: Professor Florian Heimerl

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;


/**
 * the backend of a bigger app that can take data and perform algorithms on it
 */
public class Backend implements BackendInterface{

    // Graph data field to help us store data from the campus.dot file
    protected GraphADT<String,Double> graph;

    /**
     * constructor to create a Backend object
     * @param graph a GraphADT object to store in data field
     */
    public Backend(GraphADT<String,Double> graph){
        // stors param in a data field
        this.graph = graph;
    }


     /**
      * Loads graph data from a dot file.  If a graph was previously loaded, this
      * method should first delete the contents (nodes and edges) of the existing 
      * graph before loading a new one.
      * @param filename the path to a dot file to read graph data from
      * @throws IOException if there was any problem reading from this file
      */
    @Override
    public void loadGraphData(String filename) throws IOException {
        //tries to read the file input and if this fails it will throw an IOException 
        try ( BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = reader.readLine();
            // loops through the file reading lines until they are none left and returns null
            while (line  != null){
                // will only parse the line if the length is greater than 17
                // 17 is not arbitrary that is the amount of characters in the format of the line
                // anything less is a line that does not need parsing
                if(line.length()>=17){
                    // calls a private helper method to parse the line and add things to the graph
                    parse(line);
                }
                // goes to the next line
                line = reader.readLine();
                
            }
        }
        
    }

    /**
     * helper method to parse a line of an inputted file given
     * this line is assumed to be of a set format and method adds 2 nodes, an edge, and weight to that edge
     * @param line a formatted line of a file to be parsed
     */
    private void parse(String line){
      
    
        // tries parsing the line given and if anything goes wrong then throws an exception that there was an error
        try{

            // starts by splitting the line from the "->" to seperate it into the first node and the second+weight
            String[] parting = line.split(" -> ");
            
            // sets the starting node to be the info infront of -> with removed "" and trimmed to no whitespace 
            // in front or back and only if words have whitespace between
            String startNode = parting[0].replaceAll("\"", "").trim();

            // splits the back half of line into the end node and the weight by splitting at the [seconds= 
            String[]endAndWeight = parting[1].split(" \\[seconds=");

            // takes what is infront of the [seconds= and removes the "" and trimms to no whitespace in front
            // or back and only if the words have whitespace in between eachother 
            String endNode = endAndWeight[0].replaceAll("\"", "").trim();

            // creates a double from the parsed double of the string removing ]; and any outside whitespace
            Double weight = Double.parseDouble(endAndWeight[1].replace("];", "").trim());

            // we now have a formatted start node, ending node, and weight of the edge to use the GraphADT methods to input

            // checks if the starting node is already in the graph and if so does not input it 
            if(!graph.containsNode(startNode)){
                graph.insertNode(startNode);
            }
            // checks if the ending node is already in the graph and if so does not add it 
            if(!graph.containsNode(endNode)){
                graph.insertNode(endNode);
            }
            // checks if the edge produced is already in the graph and if so then does not add the edge
            if(!graph.containsEdge(startNode, endNode)){
                graph.insertEdge(startNode, endNode, weight);
            }
            

        }
        // if any exceptions are thrown while parsing the line then throws this exception 
        catch(Exception e){
            throw new IllegalArgumentException("error in parsing");
        }
    }

    /**
     * Returns a list of all locations (node data) available in the graph.
     * @return list of all location names
     */
    @Override
    public List<String> getListOfAllLocations() {
        // Uses helper method of GraphADT to get all the nodes of the graph and returns them 
        return graph.getAllNodes();
    }


    /**
      * Return the sequence of locations along the shortest path from 
      * startLocation to endLocation, or an empty list if no such path exists.
      * @param startLocation the start location of the path
      * @param endLocation the end location of the path
      * @return a list with the nodes along the shortest path from startLocation 
      *         to endLocation, or an empty list if no such path exists
      */
    @Override
    public List<String> findLocationsOnShortestPath(String startLocation, String endLocation) {
        // Uses the graphs shortestPathData method to get a list of the shortest path between
        // the start location and the end location
        return graph.shortestPathData(startLocation, endLocation);
    }


     /**
      * Return the walking times in seconds between each two nodes on the 
      * shortest path from startLocation to endLocation, or an empty list of no 
      * such path exists.
      * @param startLocation the start location of the path
      * @param endLocation the end location of the path
      * @return a list with the walking times in seconds between two nodes along 
      *         the shortest path from startLocation to endLocation, or an empty 
     *         list if no such path exists
      */
    @Override
    public List<Double> findTimesOnShortestPath(String startLocation, String endLocation) {
        // gets a list of the shortest path between the start location and the ending location
        List<String> shortPath = graph.shortestPathData(startLocation, endLocation);
        
        List<Double> times = new ArrayList<Double>();
        // traverses through a loop to get the data of the time of each path in the shortest Path path
        for(int i=0; i<shortPath.size()-1;i++){
            
            times.add(graph.getEdge(shortPath.get(i), shortPath.get(i+1)));
        }
        // returns that final list of times between each
        return times;
    }

    /**
      * Returns a list of the ten closest destinations that can be reached most
      * quickly when starting from the specified startLocation.
      * @param startLocation the location to find the closest destinations from
      * @return the ten closest destinations from the specified startLocation
      * @throws NoSuchElementException if startLocation does not exist, or if
      *         there are no other locations that can be reached from there
      */
    @Override
    public List<String> getTenClosestDestinations(String startLocation) throws NoSuchElementException {
        // Checks first if the start location is a valid location and throws exception if not 
        if(!graph.containsNode(startLocation)){
            throw new NoSuchElementException("Start location does not exist.");
        }
        // creating lists to be used later in the method 
        // this list of all locations removes the start location from it to not mess up later calculations
        List<String> all = graph.getAllNodes();
        all.remove(all.indexOf(startLocation));
        List<Double> top10Doubles = new ArrayList<Double>();
        HashMap<String, Double> top10 = new HashMap<>();
        List<String> finalTop10 = new ArrayList<String>();

        // first initiates a hashmap with key's being every node and the value being the time it takes to get there 
        // also adds every time of path to a seperate List of doubles to keep track of and sort later
        for(int i = 0; i<graph.getNodeCount(); i++){
            try{
                // puts this in a try catch loop so that if shortestPathCost throws the exception for no path 
                // existing it catches it and does not add it
                Double time = graph.shortestPathCost(startLocation, all.get(i));
                top10.put(all.get(i), time);
                top10Doubles.add(time);
            }
            catch (Exception e){
                // does nothing just does not throw anything 
            }
        }

       // after the doubles list is full sorts the list with smallest values at the start 
        top10Doubles.sort(null);
        // chops off the list only leaving the ten smallest doubles 
        for(int i = 10; i<top10Doubles.size(); i++){
            top10Doubles.remove(i);
        }

      
        // now that we have a list of the shortest doubles we take each one in the list and compare it
        // to the hashmap to see which path each double corresponds to.
        for( int i = 0; i<top10Doubles.size(); i++){
            // gets ith value in the list
            Double topValue = top10Doubles.get(i);
            for(int j = 0; j<all.size(); j++){
                // gets the hashmap value from the key of the jth node
                Double target = top10.get(all.get(j));
                // if the target matches the top value then this is a possible fastest time
                if(target == topValue){
                    // checks if the finalTop10 list contains that key already and if not adds it and if it does then skips that value
                    // also checks that it is not the start location
                    if(!finalTop10.contains(all.get(j)) &&  finalTop10.size()<10){
                        finalTop10.add(all.get(j));
                    }
                    
                    
                }
            }
        }

        // checks if the final return list is empty and if so there were no connecting nodes
        // so throws an exception
        if(finalTop10.size() == 0){
            throw new NoSuchElementException("no connecting nodes.");
        }

        // returns the finalTop10 list that contains the strings of the 10 fastest paths 
        return finalTop10;
    }
    
}

