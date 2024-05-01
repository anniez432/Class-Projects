///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           User-Generated Movie Ratings
// Course:          CS200, Fall 2023
//
// Author:          Annie Zhao
// Email:           azhao37@wisc.edu
// Lecturer's Name: Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// Source or Recipient; Description
//
// ChatGPT; Helped with debugging when infinite loops were caused by errors.
//
///////////////////////////////// REFLECTION ///////////////////////////////////
//
// 1. Describe the problem you wrote the program to solve: This program allows
// the user to input their own movies and the ratings they gave those movies. This
// program also creates a representation of the user's ratings with the star symbol.
// 2. Why did you choose the method header for the read file method (e.g., return type,
//    parameters, throws clause)?  I mostly wanted to add information to each line in the
// file that this method reads, so I only needed the name of the file to be read as a
// parameter.
// 3. Why did you choose the method header for the write file method (e.g., return type,
//    parameters, throws clause)?  I wanted my write file method to be able to
// write two different ArrayLists to the file, which is why my  method has 3 parameters:
// the name of the file to create/write to, and then the 2 ArrayLists.
// 4. What are the biggest challenges you encountered: Infinite loops that the zyBooks
// auto-grader found.
// 3. What did you learn from this assignment: I learned that it is definitely harder
// to create test benches than I assumed-for previous labs I found it fairly easy, but
// trying to incorporate ArrayLists within reading/writing file methods definitely
// made the process a bit more complicated.
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This program generates a list of movies and the user's ratings
 * for each based on the user's input.
 *
 * @author Annie Zhao
 */
public class H12CustomApp {

    /**
     * This opens the specified file, reads the lines, and adds the rating
     * in '*' form based on the content on each line.
     *
     * Example: if filename is "data.txt" contains the line "hello there" then
     * if data.txt exists in the program's working directory then the string
     * "hello there" will be returned.
     *
     * @param titlesFile The name of the file to read from
     * @return The contents of the file or "" on an error reading from the file.
     */
    public static String readMoviesFile(String titlesFile) {
        File file = new File(titlesFile);
        Scanner fileReader = null;
        String output = "";
        int numStars = 0;
        String input = "";
        int indexOfLastChar = 0;
        try {

            fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                input = fileReader.nextLine();
                indexOfLastChar = input.charAt(input.length() - 1);

                if(indexOfLastChar >= '0' && indexOfLastChar <= '5'){
                    if(indexOfLastChar == '0'){
                        numStars = 0;
                        output += input + "/5\n";
                    }
                    else if(indexOfLastChar == '1'){
                        numStars = 1;
                        output += input + "/5 *\n";
                    }
                    else if(indexOfLastChar == '2'){
                        numStars = 2;
                        output += input + "/5 **\n";
                    }
                    else if(indexOfLastChar == '3'){
                        numStars = 3;
                        output += input + "/5 ***\n";
                    }
                    else if(indexOfLastChar == '4'){
                        numStars = 4;
                        output += input + "/5 ****\n";
                    }
                    else if(indexOfLastChar == '5'){
                        numStars = 5;
                        output += input + "/5 *****\n";
                    }
                }


            }
        } catch (FileNotFoundException e) {
            System.out.println("readMoviesFile FileNotFoundException");
        } finally {
            if (fileReader != null) fileReader.close();
        }
        return output;
    }

    /**
     * This writes the elements from the ArrayLists
     * to a file of the given name.
     *
     * @param filename The name of the file to write to.
     * @param movieList The ArrayList of movie titles.
     * @param movieRatings The ArrayList of ratings the user provided.
     * @return true if the file was created, false otherwise.
     */
    public static boolean writeMoviesFile(String filename, ArrayList<String> movieList,
                                          ArrayList<Integer> movieRatings) {
        //Note: this is an example method that is too trivial and won't count for credit.
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
            for(int i = 0; i < movieList.size(); ++i){
                writer.println(movieList.get(i) + " " + movieRatings.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("writeMoviesFile FileNotFoundException");
            return false;
        } finally {
            if (writer != null) writer.close();
        }
        return true;
    }

    /**
     * This program utilizes the user's input to create lists
     * of given movies and the user's ratings for each, then
     * writes that information to a file and outputs it to the
     * user in an organized format.
     *
     * Ex: If the user enters the movies "Hunger Games" and
     * "Star Wars" with respective ratings of 4 and 5, the
     * program will generate this output:
     * Hunger Games 4/5 ****
     * Star Wars 5/5 *****
     *
     * @param args Unused
     */
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        boolean titlesFileValid = false;
        String titlesFileName = "";

        int numLoops = 0;

        System.out.println("Please enter a name for a file:");
        while (!titlesFileValid) {

            if (scnr.hasNextLine()) {
                titlesFileName = scnr.nextLine();
                titlesFileValid = true;

                if (titlesFileName.trim().isEmpty()) {
                    titlesFileValid = false;
                    System.out.println("Please enter a valid name for a file.");
                    numLoops += 1;

                    if (numLoops == 5) {
                        System.out.println("Error: defaulting file name to movies");
                        titlesFileName = "movies";
                        titlesFileValid = true;
                    }
                }
            } else {
                System.out.println("Error: defaulting file name to movies");
                titlesFileName = "movies";
                titlesFileValid = true;
            }
        }



        System.out.println("How many movie titles would you like to add?" +
                " (Please enter a number <=20)");
        boolean validNumber = false;
        int numMovies = 0;

        while(!validNumber){
            if(scnr.hasNextInt()){
                numMovies = scnr.nextInt();
                if(numMovies <= 20 && numMovies > 0) {
                    validNumber = true;
                } else {
                    validNumber = false;
                    System.out.println("Please enter an integer between 1-20");
                }
            } else {
                System.out.println("Error: defaulting to 3 movies");
                numMovies = 3;
                validNumber = true;
            }
        }
        System.out.println("Please enter a movie title: ");
        ArrayList<String> movieTitles = new ArrayList<>();
        boolean validTitle = false;
        String movieTitle = "";
        for(int i = 0; i<numMovies ; ++i){
            while(!validTitle){
                if(scnr.hasNextLine()) {
                    movieTitle = scnr.nextLine().trim();
                    movieTitles.add(movieTitle);
                    if(movieTitles.get(0).equals("")){
                        movieTitles.clear();
                        numMovies += 1;
                        break;
                    }

                    if(i < numMovies - 1){
                        System.out.println("Please enter another movie title:");
                        break;
                    } else if (i == numMovies - 1){
                        System.out.println("Thank you! Your list of movies: "
                                            + movieTitles);
                        validTitle = false;
                        break;
                    } else {
                        validTitle = true;
                    }
                } else {
                    System.out.println("Error: defaulting to "
                            + "'Hunger Games', 'Star Wars', and 'Harry Potter'");
                    movieTitles.add("Hunger Games");
                    movieTitles.add("Star Wars");
                    movieTitles.add("Harry Potter");
                    validTitle = true;
                    i = numMovies - 1;
                }
            }
        }

        ArrayList<Integer> movieRatings = new ArrayList<>(movieTitles.size());
        boolean validRating = false;
        int movieRating = 0;
        for (int i = 1; i <= movieTitles.size(); ++i){
            System.out.println("What is your rating for movie " + i + "?" +
                    " (Please enter an integer from 0-5)");

            while(!validRating){
                if (scnr.hasNextInt()) {
                    movieRating = scnr.nextInt();
                    scnr.nextLine();
                    if(movieRating < 0 || movieRating > 5){
                        System.out.println("Please an integer between 0 and 5: ");
                        continue;
                    }
                    movieRatings.add(movieRating);
                    validRating = true;

                    if(i < movieTitles.size()){
                        continue;
                    } else if (i == movieTitles.size()){
                        System.out.println("Thank you! Your list of ratings: "
                                + movieRatings);
                    }

                } else {
                    System.out.println("Error: defaulting rating to 3");
                    movieRatings.add(3);
                    validRating = true;
                }
            }
            validRating = false;
        }

        writeMoviesFile(titlesFileName, movieTitles, movieRatings);

        System.out.println("Would you like a report of your movie ratings? (Y/N)");
        boolean validInput = false;
        String userAnswer = "";
        while(!validInput){
            if(scnr.hasNext()){
                userAnswer = scnr.next().toUpperCase();
                if(userAnswer.equals("Y")){
                    validInput = true;
                    System.out.println(readMoviesFile(titlesFileName));
                } else if (userAnswer.equals("N")){
                    validInput = true;
                    System.out.println("Ok.");
                } else {
                    System.out.println("Please enter Y or N:");
                    validInput = false;
                    scnr.nextLine();
                }
            } else {
                System.out.println("Error: defaulting to Y");
                validInput = true;
                System.out.println(readMoviesFile(titlesFileName));
            }
        }


    }
}
