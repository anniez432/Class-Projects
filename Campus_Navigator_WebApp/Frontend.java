// FILE HEADER
// Title: P209.RoleCode and P214.Integration
// Authors: Annie Zhao and UW-Madison CS400
// Lecturer: Professor Florian Heimerl
// Email: azhao37@wisc.edu

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * This class creates the Frontend for this app, which helps a user determine paths and times between
 * locations in a graph. It implements the FrontendInterface provided by the CS400 course.
 */
public class Frontend implements FrontendInterface{

    //create a variable for the backend that is of type Backend_Placeholder
    private BackendInterface backend;

    /**
     * This constructor instantiates the Frontend with a provided instance of the backend.
     * @param backend
     */
    public Frontend(BackendInterface backend){
        this.backend = backend;
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="start", for the start location
     * - a text input field with the id="end", for the destination
     * - a button labelled "Find Shortest Path" to request this computation
     * Ensure that these text fields are clearly labelled, so that the user
     * can understand how to use them.
     *
     * @return an HTML string that contains input controls that the user can
     * make use of to request a shortest path computation
     */
    @Override
    public String generateShortestPathPromptHTML() {
        return "<label>Start location: </label>" + //label for the start text field
                "<input id='start' type='text' placeholder='ex: Union South'/> " + //start text input field
                "<label>Destination: </label>" + //label for destination text field
                "<input id='end' type='text' placeholder='ex: Computer Sciences and Statistics'/> " + //text input for end destination
                "<button>Find Shortest Path</button>"; //button for user's request
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) that describes the path's start and end locations
     * - an ordered list (ol) of locations along that shortest path
     * - a paragraph (p) that includes the total travel time along this path
     * Or if there is no such path, the HTML returned should instead indicate
     * the kind of problem encountered.
     *
     * @param start is the starting location to find a shortest path from
     * @param end   is the destination that this shortest path should end at
     * @return htmlFragment an HTML string that describes the shortest path between these
     * two locations
     */
    @Override
    public String generateShortestPathResponseHTML(String start, String end) {
        String htmlFragment = "";

        if(start == null || end == null){
            htmlFragment = "<p>There is invalid input. Please re-enter values for both locations" +
                    "and try again.<p>";
        } else {
            //instantiate the HTML fragment to be returned stating the start and end locations
            htmlFragment = "<p>The start location of this path: " + start + ". The end location of " +
                    "this path: " + end + ". </p>";


            //create a list of the shortest path's locations
            List<String> shortestPathLocations = new ArrayList<>();
            List<Double> pathTimes = new ArrayList<>();
            try {
                shortestPathLocations = backend.findLocationsOnShortestPath(start, end);

                //if there are no paths or the locations aren't in the graph, return the HTML fragment
                //detailing that there are no such paths to be returned
                if (shortestPathLocations.isEmpty() || !backend.getListOfAllLocations().contains(start) ||
                        !backend.getListOfAllLocations().contains(end)) {
                    htmlFragment = htmlFragment.concat("<p> There are no paths available between these two locations.</p>");
                    return htmlFragment;
                }

                //create a list of the path times for the paths between these nodes
                pathTimes = backend.findTimesOnShortestPath(start, end);
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
            //return the HTML fragment if there are no such paths to be returned
            if (pathTimes.isEmpty()) {
                htmlFragment = htmlFragment.concat("<p> There are no paths available between these two locations.</p>");
                return htmlFragment;
            }

            //create a variable to keep track of total times
            Double totalTravelTime = 0.0;

            //otherwise, add each path time to the total travel time
            for (Double time : pathTimes) {
                totalTravelTime += time;
            }

            //create the ordered list of locations along the shortest path
            htmlFragment = htmlFragment.concat("<p>Locations along its shortest path:</p>" +
                    "<ol>");

            //add each location along the shortest path as list elements
            for (String location : shortestPathLocations) {
                htmlFragment = htmlFragment.concat("<li>" + location + "</li>");
            }

            //return the total travel time to the HTML fragment as well
            htmlFragment = htmlFragment.concat("</ol>" +
                    "<p>The total travel time along this path is: " + totalTravelTime + ".</p>");

        }
            return htmlFragment;

    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a text input field with the id="from", for the start location
     * - a button labelled "Ten Closest Destinations" to submit this request
     * Ensure that this text field is clearly labelled, so that the user
     * can understand how to use it.
     *
     * @return an HTML string that contains input controls that the user can
     * make use of to request a ten closest destinations calculation
     */
    @Override
    public String generateTenClosestDestinationsPromptHTML() {
        return "<br><label>Start location: </label>" + //label for start text input
                "<input id='from' type='text' placeholder='ex: Union South' /> " + //text input field
                "<button>Ten Closest Destinations</button>"; //button for submiting request
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a
     * larger html page.  This HTML output should include:
     * - a paragraph (p) that describes the start location that travel time to
     * the closest destinations are being measured from
     * - an unordered list (ul) of the ten locations that are closest to start
     * Or if no such destinations can be found, the HTML returned should
     * instead indicate the kind of problem encountered.
     *
     * @param start is the starting location to find close destinations from
     * @return htmlFragment an HTML string that describes the closest destinations from the
     * specified start location.
     */
    @Override
    public String generateTenClosestDestinationsResponseHTML(String start) {
        //instantiate the HTML fragment to be returned
        String htmlFragment = "<p>The start location of this path: " + start + ". We will find the 10 closest" +
                " destinations to this location.</p>";

	//initialize list that'll have the locations
	List<String> locations = new ArrayList<>();
        //generate the list of 10 closest destinations to the start node
	//catch the exception thrown in getTenClosestDestinations()
	try{
        	locations = backend.getTenClosestDestinations(start);
	} catch (NoSuchElementException e){
		System.out.println(e.getMessage());
	}

        //if there are no such destinations, return that to the user .
        if(locations.isEmpty() || !backend.getListOfAllLocations().contains(start)){
            htmlFragment += "<p> There are no locations available for this start locations.</p>";
            return htmlFragment;
        }

        htmlFragment += "Ten closest locations:</p>" +
                "<ul>";

        //add each location to the HTML fragment
        for(String location : locations){
            htmlFragment = htmlFragment.concat("<li>" + location + "</li>");
        }

        htmlFragment = htmlFragment.concat("</ul>");

        return htmlFragment;
    }
}
