// FILE HEADER
// Author email: zzou39@wisc.edu
// All credits for this file goes to the author

import java.util.List;
import java.util.Scanner;
import java.io.IOException;

/**
 * The Frontend class implements the FrontendInterface and provides the
 * text-based user interface for interacting with
 * the iSongly system. It allows users to load song files, get songs based on
 * specified year ranges, set loudness filters,
 * and display the top five most danceable songs.
 */
public class Frontend implements FrontendInterface {

    /** Scanner used to read input from the user */
    Scanner in;

    /** BackendInterface instance used to interact with the backend logic */
    BackendInterface backend;

    /**
     * Constructs the Frontend with the provided scanner and backend interface.
     *
     * @param in      the scanner used to read user input
     * @param backend the backend interface that handles backend operations
     */
    public Frontend(Scanner in, BackendInterface backend) {
        this.in = in;
        this.backend = backend;
    }

    /**
     * Starts the command loop, repeatedly prompting the user to issue new commands
     * until they select 'Q' to quit. The available commands include loading a song
     * file, getting songs by year, filtering by loudness, and displaying the top
     * five most danceable songs.
     */
    @Override
    public void runCommandLoop() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            String command = in.nextLine().trim().toUpperCase();
            switch (command) {
                case "L":
                    loadFile();
                    break;
                case "G":
                    getSongs();
                    break;
                case "F":
                    setFilter();
                    break;
                case "D":
                    displayTopFive();
                    break;
                case "Q":
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the main menu options to the user. The options include loading a
     * song file, getting songs by year, setting a loudness filter, displaying the
     * top five most danceable songs, or quitting the application.
     */
    @Override
    public void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("[L] Load Song File");
        System.out.println("[G] Get Songs by Year");
        System.out.println("[F] Filter Songs by Loudness");
        System.out.println("[D] Display Top Five Danceable Songs");
        System.out.println("[Q] Quit");
        System.out.print("Choose an option: ");
    }

    /**
     * Prompts the user to enter the filename of the CSV file they want to load.
     * This method will repeatedly prompt the user to enter a valid filename if the
     * backend encounters an IOException while trying to load the file. Once a valid
     * file is loaded, the method will print a success message.
     */
    @Override
    public void loadFile() {
        boolean success = false;
        while (!success) {
            System.out.print("Enter the filename to load: ");
            String filename = in.nextLine().trim();
            try {
                backend.readData(filename); // Attempt to load the file via the backend
                System.out.println("File loaded successfully.");
                success = true; // Exit the loop if the file loads successfully
            } catch (IOException e) {
                System.out.println("Error loading file: " + e.getMessage());
                System.out.println("Please try entering a valid filename.");
            }
        }
    }

    /**
     * Retrieving a list of song titles that are sorted by year. The user is
     * prompted to optionally specify a minimum and/or maximum year to limit the
     * number of songs within the specified year range. If no songs are found in the
     * given range, a message is displayed.
     */
    @Override
    public void getSongs() {
        try {
            System.out.print("Enter minimum year (or press enter for no minimum): ");
            String minYearInput = in.nextLine().trim();
            Integer minYear = minYearInput.isEmpty() ? null : Integer.parseInt(minYearInput);

            System.out.print("Enter maximum year (or press enter for no maximum): ");
            String maxYearInput = in.nextLine().trim();
            Integer maxYear = maxYearInput.isEmpty() ? null : Integer.parseInt(maxYearInput);

            List<String> songs = backend.getRange(minYear, maxYear);
            if (songs.isEmpty()) {
                System.out.println("No songs found in the specified range.");
            } else {
                System.out.println("Songs in the specified range:");
                for (String song : songs) {
                    System.out.println(song);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    /**
     * Prompts the user to enter a loudness threshold to filter the songs. The
     * backend is used to retrieve songs that have a loudness value smaller than the
     * specified threshold. If no threshold is provided, the filter is cleared. The
     * filtered list of songs is displayed to the user.
     */
    @Override
    public void setFilter() {
        try {
            System.out.print("Enter the maximum loudness threshold (or press enter to clear filter): ");
            String thresholdInput = in.nextLine().trim();
            Integer threshold = thresholdInput.isEmpty() ? null : Integer.parseInt(thresholdInput);

            List<String> filteredSongs = backend.setFilter(threshold);
            if (filteredSongs.isEmpty()) {
                System.out.println("No songs found below the loudness threshold.");
            } else {
                System.out.println("Filtered songs:");
                for (String song : filteredSongs) {
                    System.out.println(song);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    /**
     * Displays the titles of the top five most danceable songs within the current
     * year range and loudness filter. If there are no such songs, then this method
     * should indicate that and recommend that the user change their current range
     * or filter settings.
     */
    @Override
    public void displayTopFive() {
        List<String> topFiveSongs = backend.fiveMost();
        if (topFiveSongs.isEmpty()) {
            System.out.println("No danceable songs found.");
            System.out.println("You may want to change the current year range or filter settings and try again.");
        } else {
            System.out.println("Top five most danceable songs:");
            for (String song : topFiveSongs) {
                System.out.println(song);
            }
        }
    }
}