// FILE HEADER
// Title: P209.RoleCode and P214.Integration
// Authors: Annie Zhao and UW-Madison CS400
// Lecturer: Professor Florian Heimerl
// Email: azhao37@wisc.edu

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class tests the implementation of Frontend, determining if its methods have outputs accurate to
 * the frontend interface.
 */
public class FrontendTests {

    /**
     * This test determines if the frontend's generateShortestPathPromptHTML() method
     * has the correct values in its output.
     */
    @Test
    public void roleTest1(){
        /*
        This HTML output should include:
                * - a text input field with the id="start", for the start location
                * - a text input field with the id="end", for the destination
                * - a button labelled "Find Shortest Path" to request this computation
                * Ensure that these text fields are clearly labelled, so that the user
                * can understand how to use them.
         */
        //instantiate a graph placeholder to be used for backend
        Graph_Placeholder graph = new Graph_Placeholder();
        //instantiate a backend placeholder for the frontend
        Backend_Placeholder backend = new Backend_Placeholder(graph);
        //instantiate the frontend
        Frontend frontend = new Frontend(backend);

        //instantiate the output by calling the desired method on the frontend
        String output = frontend.generateShortestPathPromptHTML();

        //add the values that should be in the HTML fragment (output)
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("<input");
        expectedText.add("id='start'");
        expectedText.add("placeholder");
        expectedText.add("type='text'");
        expectedText.add("id='end'");
        expectedText.add("<button>");
        expectedText.add("Find Shortest Path");
        expectedText.add("</button>");
        expectedText.add("<label>");
        expectedText.add("</label>");

        //the test fails if any of those values aren't in the output
        for(String text : expectedText){
            Assertions.assertTrue(output.contains(text));
        }

    }

    /**
     * This test determines if the HTML output from frontend's generateShortestPathResponseHTML returns
     * the correct values as specified by the Frontend interface
     */
    @Test
    public void roleTest2(){
        /*
        Returns an HTML fragment that can be embedded within the body of a
         * larger html page.  This HTML output should include:
             * - a paragraph (p) that describes the path's start and end locations
             * - an ordered list (ol) of locations along that shortest path
             * - a paragraph (p) that includes the total travel time along this path
             * Or if there is no such path, the HTML returned should instead indicate
             * the kind of problem encountered.
         */

        //instantiate a graph placeholder to be used for backend
        Graph_Placeholder graph = new Graph_Placeholder();
        //instantiate a backend placeholder for the frontend
        Backend_Placeholder backend = new Backend_Placeholder(graph);
        //instantiate the frontend
        Frontend frontend = new Frontend(backend);

        //instantiate the output by calling the desired method on the frontend
        String output = frontend.generateShortestPathResponseHTML("Union South", "Computer Sciences and Statistics");

        //add the values that should be in the HTML fragment (output)
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("<p>");
        expectedText.add("<ol>");
        expectedText.add("<li>");
        expectedText.add("</li>");
        expectedText.add("</ol>");
        expectedText.add("</p>");
        expectedText.add("shortest path");
        expectedText.add("total travel time");
        expectedText.add("location");
        expectedText.add("Union South");
        expectedText.add("Computer Sciences and Statistics");
        //findTimesOnShortestPath: for(int i=0;i<locations.size();i++) times.add(i+1.0); 2 locations, should be
        //(0 + 1) + (1 + 1) = 3
        expectedText.add("3.0");

        //the test fails if any of those values aren't in the output
        for(String text : expectedText){
            Assertions.assertTrue(output.contains(text));
        }

        //test that this method works between two other nodes as well
        String outputUStoAOSS = frontend.generateShortestPathResponseHTML("Union South", "Atmospheric, " +
                "Oceanic and Space Sciences");
        //add the expected values - don't need to add ones checked by the previous test case
        ArrayList<String> expectedText2 = new ArrayList<>();
        expectedText2.add("Union South");
        expectedText2.add("Atmospheric, Oceanic and Space Sciences");
        //findTimesOnShortestPath: for(int i=0;i<locations.size();i++) times.add(i+1.0); 2 locations, should be
        //(0 + 1) + (1 + 1) + (2 + 1) = 6
        expectedText2.add("6.0");

        //the test fails if any of those values aren't in the output
        for(String text : expectedText2){
            Assertions.assertTrue(outputUStoAOSS.contains(text));
        }

        //test that this method works with the third combination of locations
        String outputCStoAOSS = frontend.generateShortestPathResponseHTML("Computer Sciences and Statistics",
                "Atmospheric, Oceanic and Space Sciences");

        //add the values expected to be outputted
        ArrayList<String> expectedText3 = new ArrayList<>();
        expectedText3.add("Computer Sciences and Statistics");
        expectedText3.add("Atmospheric, Oceanic and Space Sciences");
        //findTimesOnShortestPath: for(int i=0;i<locations.size();i++) times.add(i+1.0); 2 locations, should be
        //(0 + 1) + (1 + 1) = 3
        expectedText3.add("3.0");

        //the test fails if any of those values aren't in the output
        for(String text : expectedText3){
            Assertions.assertTrue(outputCStoAOSS.contains(text));
        }

        //test that output correctly handles a location that's not in the graph
        String outputNoDestinations = frontend.generateShortestPathResponseHTML("Union South", "Memorial Union");
        //expected outputs
        ArrayList<String> expectedText4 = new ArrayList<>();
        expectedText4.add("no paths");
        expectedText4.add("locations");

        //test fails if this case is not correctly handled
        for(String text : expectedText4){
            Assertions.assertTrue(outputNoDestinations.contains(text));
        }
    }

    /**
     * This test determines if frontend's generateTenClosestDestinationsPromptHTML() returns
     * an HTML fragment with the correct values.
     */
    @Test
    public void roleTest3(){
        /*
         This HTML output should include:
             * - a text input field with the id="from", for the start location
             * - a button labelled "Ten Closest Destinations" to submit this request
             * Ensure that this text field is clearly labelled, so that the user
             * can understand how to use it.
             * @return an HTML string that contains input controls that the user can
             *         make use of to request a ten closest destinations calculation
         */

        //instantiate a graph placeholder to be used for backend
        Graph_Placeholder graph = new Graph_Placeholder();
        //instantiate a backend placeholder for the frontend
        Backend_Placeholder backend = new Backend_Placeholder(graph);
        //instantiate the frontend
        Frontend frontend = new Frontend(backend);

        //instantiate the output by calling the desired method on the frontend
        String output = frontend.generateTenClosestDestinationsPromptHTML();

        //values the output should contain
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("<input");
        expectedText.add("type='text'");
        expectedText.add("id='from'");
        expectedText.add("placeholder");
        expectedText.add("<button>");
        expectedText.add("Ten Closest Destinations");
        expectedText.add("</button>");
        expectedText.add("<label>");
        expectedText.add("</label>");

        //test fails if output doesn't contain those values
        for(String text : expectedText){
            Assertions.assertTrue(output.contains(text));
        }
    }

    /**
     * This test determines if the HTML output from frontend's generateTenClosestDestinationsResponseHTML returns
     * the correct values as specified by the Frontend interface.
     */
    @Test
    public void roleTest4(){
        /*
        This HTML output should include:
             * - a paragraph (p) that describes the start location that travel time to
             *        the closest destinations are being measured from
             * - an unordered list (ul) of the ten locations that are closest to start
             * Or if no such destinations can be found, the HTML returned should
             * instead indicate the kind of problem encountered.
         */

        //instantiate a graph placeholder to be used for backend
        Graph_Placeholder graph = new Graph_Placeholder();
        //instantiate a backend placeholder for the frontend
        Backend_Placeholder backend = new Backend_Placeholder(graph);
        //instantiate the frontend
        Frontend frontend = new Frontend(backend);

        //instantiate the output by calling the desired method on the frontend
        String output = frontend.generateTenClosestDestinationsResponseHTML("Union South");

        //expected values in the output
        ArrayList<String> expectedText = new ArrayList<>();
        expectedText.add("<p>");
        expectedText.add("</p>");
        expectedText.add("<ul>");
        expectedText.add("</ul>");
        expectedText.add("<li>");
        expectedText.add("</li>");
        expectedText.add("start");
        expectedText.add("closest destinations");
        expectedText.add("Union South");
        expectedText.add("Computer Sciences and Statistics");
        expectedText.add("Atmospheric, Oceanic and Space Sciences");

        //this test fails if the output doesn't contain any of those values
        for(String text : expectedText){
            Assertions.assertTrue(output.contains(text));
        }

        //check that this test passes with another node in the graph
        String outputCS = frontend.generateTenClosestDestinationsResponseHTML("Computer Sciences and Statistics");
        ArrayList<String> expectedText2 = new ArrayList<>();
        expectedText2.add("Union South");
        expectedText2.add("Atmospheric, Oceanic and Space Sciences");

        //test fails if output doesn't contain any of the expected values
        for(String text : expectedText2){
            Assertions.assertTrue(outputCS.contains(text));
        }

        //check that this test passes with another node in the graph
        String outputAOSS = frontend.generateTenClosestDestinationsResponseHTML("Atmospheric, " +
                "Oceanic and Space Sciences");
        ArrayList<String> expectedText3 = new ArrayList<>();
        expectedText3.add("Computer Sciences and Statistics");
        expectedText3.add("Union South");

        //test fails if output doesn't contain any of the expected values
        for(String text : expectedText3){
            Assertions.assertTrue(outputAOSS.contains(text));
        }

        //check if test will work with a location that's not in the graph
        String outputNoDestinations = frontend.generateTenClosestDestinationsResponseHTML("Memorial Union");
        ArrayList<String> expectedText4 = new ArrayList<>();
        expectedText4.add("no locations");
        expectedText4.add("Memorial Union");

        //test fails if the expected text is in the output
        for(String text : expectedText4){
            Assertions.assertTrue(outputNoDestinations.contains(text));
        }
    }

    /**
     * This test determines if the integrated program correctly generates output
     * when the user enters a valid location for finding the 10 closest destinations.
     */
    @Test
    public void Integration_test1(){
        //initialize appropriate objects
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>();
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);

        //load the campus data into backend
        try {
            backend.loadGraphData("campus.dot");
        } catch (IOException e){
            System.out.println(e.getMessage());
            Assertions.fail(); //test fails if data can't be loaded
        }

        //determine output when user wants the ten closest destinations to Union South
        String output = frontend.generateTenClosestDestinationsResponseHTML("Union South");
        //looking in campus.dot, we can expect the direct paths will be included in these locations
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Wendt Commons");
        expected.add("Memorial Arch");
        expected.add("1410 Engineering Dr");
        expected.add("Computer Sciences and Statistics");
        expected.add("Atmospheric, Oceanic and Space Sciences");
        expected.add("Wisconsin Institute for Discovery");
        expected.add("Union South"); //output should display input value

        //test fails if the output does not contain any of the expected locations
        for(String location : expected){
            Assertions.assertTrue(output.contains(location));
        }
    }

    /**
     * This test determines if the integrated program correctly generates output
     * when the user enters an invalid location for finding the 10 closest destinations.
     */
    @Test
    public void Integration_test2(){
        //initialize appropriate objects
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>();
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);

        //load the campus data into backend
        try {
            backend.loadGraphData("campus.dot");
        } catch (IOException e){
            System.out.println(e.getMessage());
            Assertions.fail(); //test fails if data can't be loaded
        }

        //determine output when user wants the ten closest destinations to Union South
        String output = frontend.generateTenClosestDestinationsResponseHTML("Building");
        //looking in campus.dot, we can expect the direct paths will be included in these locations
        ArrayList<String> expected = new ArrayList<>();
        expected.add("There are no locations available for this start locations.");
        expected.add("Building"); // output should contain the input value

        //test fails if the output does not contain any of the expected locations
        for(String location : expected){
            Assertions.assertTrue(output.contains(location));
        }
    }

    /**
     * This test determines if the integrated program correctly generates output
     * when the user enters valid locations for finding the shortest path.
     */
    @Test
    public void Integration_test3(){
        //initialize appropriate objects
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>();
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);

        //load the campus data into backend
        try {
            backend.loadGraphData("campus.dot");
        } catch (IOException e){
            System.out.println(e.getMessage());
            Assertions.fail(); //test fails if data can't be loaded
        }

        //determine output when user wants the shortest path between the 2
        String output = frontend.generateShortestPathResponseHTML("Camp Randall Stadium",
                "Computer Sciences and Statistics");
        //looking in campus.dot, we can these locations and the total time
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Camp Randall Stadium");
        expected.add("Civil War Stockade"); //133.6
        expected.add("Memorial Arch"); //315.4
        expected.add("Wendt Commons"); //428.2
        expected.add("Computer Sciences and Statistics"); //617.3
        expected.add("617.3");

        //test fails if the output does not contain any of the expected locations
        for(String location : expected){
            Assertions.assertTrue(output.contains(location));
        }
    }

    /**
     * This test determines if the integrated program correctly generates output
     * when the user enters invalid locations for finding the shortest path.
     */
    @Test
    public void Integration_test4(){
        //initialize appropriate objects
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>();
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);

        //load the campus data into backend
        try {
            backend.loadGraphData("campus.dot");
        } catch (IOException e){
            System.out.println(e.getMessage());
            Assertions.fail(); //test fails if data can't be loaded
        }

        //determine output when user wants the shortest path between the 2
        String output = frontend.generateShortestPathResponseHTML("Memorial",
                "Computer Sciences and Statistics");
        //no locations, should output error message
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Memorial");
        expected.add("Computer Sciences and Statistics");
        expected.add("There are no paths available between these two locations.");

        //test fails if the output does not contain any of the expected output
        for(String location : expected){
            Assertions.assertTrue(output.contains(location));
        }
    }

}
