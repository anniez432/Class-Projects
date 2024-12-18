// FILE HEADER
// Author: Annie Zhao
// Email: azhao37@wisc.edu

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class tests the Backend class and its methods with JUnit tests.
 */
public class BackendTests{

    /*
    each of the 4 methods of the backend should
    be called at least once across your set of test methods.
     */

    /**
     * This test method tests the readData() method. Asserts false if any
     * expectations are not met.
     */
    @Test
    public void roleTest1() {

        //create an empty Song tree to test Backend
        Tree_Placeholder tree = new Tree_Placeholder();
        //create a Backend
        Backend backendTest = new Backend(tree);

        // make sure this tree is empty and correctly
        // created before continuing tests
        // placeholder always returns false for isEmpty
        // need to check size() - returns 3 for empty trees
        if(backendTest.tree.size() != 3) Assertions.fail();

        //test if readData() correctly throws an exception
        // with an invalid filename
        try{
            backendTest.readData("incorrect.csv");
            System.out.println("incorrect");
            Assertions.fail();
        } catch (Exception e) {
            if(e.getMessage() == null || e.getMessage().isEmpty()) {
                System.out.println("no message");
                Assertions.fail();
            }
            System.out.println(e.getMessage());
        }

        // check if readData() correctly reads songs.csv and enters
        // the data into the tree
        try{
            backendTest.readData("songs.csv");
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Caught exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            Assertions.fail();
        }

        //check if data was correctly inserted into the tree
        //based on placeholder, tree shouldn't be empty and
        // size should return 4 since lastAddedSong != null

        Assertions.assertTrue(!backendTest.tree.isEmpty() && backendTest.tree.size() == 4);

        //lastAddedSong should be Kills You Slowly
        Song lastSongTest = new Song("Kills You Slowly", "The Chainsmokers", "electropop", 2019, 150, 44, 70,-9,13);
        //if(!backendTest.tree.lastAddedSong.getTitle().equals(lastSongTest.getTitle())) return false;
        //Assertions.assertTrue(backendTest.tree.lastAddedSong.getTitle().equals(lastSongTest.getTitle()));

    }

    /**
     * This tester method tests Backend's getRange() method. Asserts false
     * if expectations aren't met.
     */
    @Test
    public void roleTest2(){
        //test getRange()
        //create an empty Song tree to test Backend
        Tree_Placeholder tree = new Tree_Placeholder();
        //create a Backend
        Backend backendTest = new Backend(tree);

        //read data from songs.csv - will only keep tr
        try {
            backendTest.readData("songs.csv");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Caught exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            Assertions.fail();
        }

        //test nulls low/high

        backendTest.getRange(null, null);

        //System.out.println(backendTest.tree.lastAddedSong.getTitle());
        //double check size
        //if(backendTest.tree.size() != 4) return false;
        Assertions.assertTrue(backendTest.tree.size() == 4);

        //returns a list of songs in order from low->high Year
        //thus lastAddedSong should be "Kills You Slowly (2019)"
        Song lastSongTest = new Song("Kills You Slowly", "The Chainsmokers", "electropop", 2019, 150, 44, 70,-9,13);
        //if(!backendTest.tree.lastAddedSong.getTitle().equals(lastSongTest.getTitle())) return false;
        //Assertions.assertTrue(backendTest.tree.lastAddedSong.getTitle().equals(lastSongTest.getTitle()));

        //test null low

        //should return the hardcoded songs <= 2018 sorted in ascending year
        ArrayList<String> expected = new ArrayList<>();
        expected.add("BO$$");
        expected.add("Cake By The Ocean");
        expected.add("A L I E N S");

       // if(!backendTest.getRange(null, 2018).equals(expected)) return false;
        Assertions.assertTrue(backendTest.getRange(null, 2018).equals(expected));

        //test null high

        //should return the hardcoded songs >= 2016
        expected.remove("BO$$");
        expected.add("Kills You Slowly");
        //if(!backendTest.getRange(2016, null).equals(expected)) return false;
        Assertions.assertTrue(backendTest.getRange(2016, null).equals(expected));

        //test valid low and high values

        //should return hardcoded songs <= 2018 and >= 2016
        // Cake By The Ocean, A L I E N S
        expected.remove("Kills You Slowly");

        //if(!backendTest.getRange(2016, 2018).equals(expected)) return false;
        Assertions.assertTrue(backendTest.getRange(2016, 2018).equals(expected));
    }

    /**
     * This test method tests the setFilter() method. Asserts false
     * if expectations are not met.
     */
    @Test
    public void roleTest3(){
        //create an empty Song tree to test Backend
        Tree_Placeholder songTree = new Tree_Placeholder();
        //create a Backend
        Backend backendTest = new Backend(songTree);

        //if the lastAddedSong isn't Cake By The Ocean, return false
        //change to assertEquals with JUnit

        //if(backendTest.tree.lastAddedSong.compareTo(lastSongTest) != 0) return false;

        //created an ArrayList of the expected result
        ArrayList<String> expectedTitles = new ArrayList<>();

        expectedTitles.add("BO$$");
        expectedTitles.add("Cake By The Ocean");
        expectedTitles.add("A L I E N S");

        // if filter of 0 is applied, all three songs should still be returned but ordered low->high by year
        //if(!backendTest.setFilter(0).equals(expectedTitles)) return false;
        Assertions.assertTrue(backendTest.setFilter(0).equals(expectedTitles));

        int lastIndex = backendTest.tree.size() - 1;

        Song expectedSong = new Song("A L I E N S",
                "Coldplay","permanent wave",2017,148,88,43,-5,21);

        //System.out.println(backendTest.setFilter(0).get(lastIndex));

       // if(backendTest.setFilter(0).get(lastIndex).compareTo(expectedSong.getTitle()) != 0) return false;
        //the last item in the setFilter(0) should be equal to expectedSong
        Assertions.assertTrue(backendTest.setFilter(0).get(lastIndex).compareTo(expectedSong.getTitle()) == 0);

        //TEST THAT A SONG IS NOT RETURNED BY FILTER
        //add a Song that is above the filter
        Song aboveThresholdSong = new Song("Under", "DNCE","dance pop",2018,119,75,77,-10,4);
        backendTest.tree.insert(aboveThresholdSong);

        //make sure that the tree is not empty
        //if(backendTest.size() != 4) return false;
        Assertions.assertTrue(backendTest.tree.size() == 4);

        backendTest.setFilter(0); //should result in boss, cake by the ocean, aliens

        //if the filteredList still ends with
        //if(backendTest.setFilter(0).get(lastIndex).compareTo(aboveThresholdSong.getTitle()) == 0 || !backendTest.setFilter(0).get(lastIndex).equals("A L I E N S")) return false;
        Assertions.assertTrue(backendTest.setFilter(0).get(lastIndex).compareTo(aboveThresholdSong.getTitle()) != 0);
        Assertions.assertTrue(backendTest.setFilter(0).get(lastIndex).equals("A L I E N S"));

        //set a -10 filter so no songs pass the filter - empty list should be returned
        //if(backendTest.size() != 4) return false;
        Assertions.assertTrue(backendTest.tree.size() == 4);

        backendTest.setFilter(-10);
        ArrayList<String> emptyList = new ArrayList<>();

        //if(!backendTest.setFilter(-10).equals(emptyList)) return false;
        Assertions.assertTrue(backendTest.setFilter(-10).equals(emptyList));


    }

    /**
     * This tester method tests the functionality of the fiveMost() method.
     * Asserts false if expectations are not met.
     */
    @Test
    public void roleTest4() {
        //create an empty Song tree to test Backend
        Tree_Placeholder songTree = new Tree_Placeholder();
        //create a Backend
        Backend backendTest = new Backend(songTree);

        //only adds in Kills You Slowly since it was the last added song from the csv?
        //read the songs in
        try{
            backendTest.readData("songs.csv");
            //System.out.println(backendTest.tree.lastAddedSong.getTitle());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //fiveMost() on boss (81), cake by the ocean (77), kills you slowly (70), aliens (43) returns list
        // no setFilter call - any loudness is ok
        // no getRange call - any year is ok
        // should return list in descending order of danceability
        // System.out.println(backendTest.fiveMost());
        ArrayList<String> expected = new ArrayList<>();
        expected.add("BO$$");
        expected.add("Cake By The Ocean");
        expected.add("Kills You Slowly");
        expected.add("A L I E N S");

        //if(!backendTest.fiveMost().equals(expected)) return false;
        //System.out.println(backendTest.fiveMost());
        //System.out.println(backendTest.fiveMost());
        Assertions.assertTrue(backendTest.fiveMost().equals(expected));

        //test after getRange call
        backendTest.getRange(2016, 2019);
        expected.remove("BO$$");
        //should only return cake by the ocean, kills you slowly, aliens
        Assertions.assertTrue(backendTest.fiveMost().equals(expected));

        //test after setFilter call
        backendTest.setFilter(-7); //so only Kills You Slowly is returned
        expected.remove("Cake By The Ocean");
        expected.remove("A L I E N S");
        Assertions.assertTrue(backendTest.fiveMost().equals(expected));
        //test insert song

        Song noDanceability = new Song("Can't dance", "Julius", "pop", 2016,119,75,0,-5,4);

        //can only use the hardcoded songs, can't add songs and then test them?
        //insert can be a test case

        //Test that inserts into the tree work
        backendTest.tree.insert(noDanceability);
        //Assertions.assertTrue(backendTest.lastAddedSong.compareTo(noDanceability) == 0);


    }


    /**
     * This integration test determines if the app correctly
     * displays the main menu and interprets user input when the user
     * wants to load songs or quit the program. It also determines
     * if the program correctly identifies incorrect file names, as well as
     * if the program correctly handles uppercase or lowercase input.
     */
    @Test
    public void IntegrationTest1(){
        //Determine if L and Q work as intended

        //instantiate the TextUITester with desired inputs
        TextUITester tester = new TextUITester("L\nincorrect\nsongs.csv\nQ");
        //instantiate the scanner, tree, backend, and frontend necessary
        Scanner testScanner = new Scanner(System.in);
        IterableRedBlackTree<Song> tree = new IterableRedBlackTree<>();
        Backend backend = new Backend(tree);
        Frontend frontend = new Frontend(testScanner, backend);

        //run the program with the desired input
        frontend.runCommandLoop();

        //determine what the program outputs as the input is fed in
        String output = tester.checkOutput();

        //test fails if the output is empty
        Assertions.assertTrue(!output.isEmpty());

        //check that the output contains the output specified when user
        // enters L, then an invalid file name and then a valid file name in Frontend.java
        Assertions.assertTrue(output.contains("Main Menu")
                && output.contains("Enter the filename to load: ")
                && output.contains("Please try entering a valid filename.")
                && output.contains("File loaded successfully."));

        //check that the output contains the output specified in Frontend.java
        //for when the user quits the program
        Assertions.assertTrue(output.contains("Error loading file: File not found"));
        Assertions.assertTrue(output.contains("Exiting... Goodbye!"));

        //close scanner
        testScanner.close();


        //check that lower case inputs have the same exact functionality

        //instantiate the TextUITester with desired inputs
        TextUITester testerLower = new TextUITester("l\nincorrect\nsongs.csv\nq");
        //instantiate the scanner, tree, backend, and frontend necessary
        Scanner testScannerLower = new Scanner(System.in);
        IterableRedBlackTree<Song> treeLower = new IterableRedBlackTree<>();
        Backend backendLower = new Backend(treeLower);
        Frontend frontendLower = new Frontend(testScannerLower, backendLower);

        //run the program with the desired input
        frontendLower.runCommandLoop();

        //determine the outputs
        String outputLower = testerLower.checkOutput();

        //test fails if the output is empty
        Assertions.assertTrue(!outputLower.isEmpty());

        //output should be the same as the first half of this test
        Assertions.assertTrue(outputLower.contains("Main Menu")
                && outputLower.contains("Enter the filename to load: ")
                && outputLower.contains("Please try entering a valid filename.")
                && outputLower.contains("File loaded successfully."));

        Assertions.assertTrue(outputLower.contains("Error loading file: File not found"));
        Assertions.assertTrue(outputLower.contains("Exiting... Goodbye!"));

        //close scanner
        testScannerLower.close();
    }

    /**
     * This integration test determines if the program works as specified by
     * Frontend and Backend when the user wants to getRange on their songs
     * if min & max = null, min=null while max has a value, min has a value while max=null,
     * and when both min & max have values.
     */
    @Test
    public void IntegrationTest2(){
        //test if G works

        //null min and max - should return all songs - no filter
        //instantiate the TextUITester with desired input - user wants getRange
        // with no specified years
        TextUITester tester = new TextUITester("L\nsongs.csv\nG\n\n\n\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScanner = new Scanner(System.in);
        IterableRedBlackTree<Song> tree = new IterableRedBlackTree<>();
        Backend backend = new Backend(tree);
        Frontend frontend = new Frontend(testScanner, backend);

        //run program
        frontend.runCommandLoop();

        //determine what the program output is for this specified input
        String output = tester.checkOutput();

        //test fails if output is empty
        Assertions.assertTrue(!output.isEmpty());

        //check that output contains output specified by Frontend java
        //when user enters G
        Assertions.assertTrue(output.contains("Enter minimum year (or press enter for no minimum): ")
                && output.contains("Enter maximum year (or press enter for no maximum): ")
                && output.contains("Songs in the specified range:"));

        //since all songs should be returned, iterate through the tree and
        //create list of song titles
        Iterator<Song> treeIterator = tree.iterator();
        ArrayList<String> songTitles = new ArrayList<>();

        while(treeIterator.hasNext()){
            songTitles.add(treeIterator.next().getTitle());
        }

        //test fails if any of the titles were not outputted by the program
        for(String title : songTitles){
            Assertions.assertTrue(output.contains(title));
        }

        //close scanner
        testScanner.close();



        //null min, valid max
        //should only return 2010 songs
        //initialize TextUITester with year max limit as 2010
        TextUITester testMax = new TextUITester("L\nsongs.csv\nG\n\n2010\nQ");
        //initialize scanner, tree, backend, frontend
        Scanner testScannerMax = new Scanner(System.in);
        IterableRedBlackTree<Song> treeMax = new IterableRedBlackTree<>();
        Backend backendMax = new Backend(treeMax);
        Frontend frontendMax = new Frontend(testScannerMax, backendMax);

        //run program
        frontendMax.runCommandLoop();

        //if output is empty, test fails
        String outputMax = testMax.checkOutput();
        Assertions.assertTrue(!outputMax.isEmpty());

        //it is expected that only songs released in 2010 are returned
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Hey, Soul Sister");
        expected.add("Love The Way You Lie");
        expected.add("TiK ToK");
        expected.add("Bad Romance");
        expected.add("Just the Way You Are");
        expected.add("Baby");
        expected.add("Dynamite");
        expected.add("Secrets");
        expected.add("Empire State of Mind (Part II) Broken Down");
        expected.add("Only Girl (In The World)");
        expected.add("Club Can't Handle Me (feat. David Guetta)");
        expected.add("Marry You");
        expected.add("Cooler Than Me - Single Mix");
        expected.add("Telephone");
        expected.add("Like A G6");
        expected.add("OMG (feat. will.i.am)");
        expected.add("Eenie Meenie");
        expected.add("The Time (Dirty Bit)");
        expected.add("Alejandro");
        expected.add("Your Love Is My Drug");
        expected.add("Meet Me Halfway");
        expected.add("Whataya Want from Me");
        expected.add("Take It Off");
        expected.add("Misery");
        expected.add("All The Right Moves");
        expected.add("Animal");
        expected.add("Naturally");
        expected.add("I Like It");
        expected.add("Teenage Dream");
        expected.add("California Gurls");
        expected.add("3");
        expected.add("My First Kiss - feat. Ke$ha");
        expected.add("Blah Blah Blah (feat. 3OH!3)");
        expected.add("Imma Be");
        expected.add("Try Sleeping with a Broken Heart");
        expected.add("Sexy Bitch (feat. Akon)");
        expected.add("Bound To You - Burlesque Original Motion Picture Soundtrack");
        expected.add("If I Had You");
        expected.add("Rock That Body");
        expected.add("Dog Days Are Over");
        expected.add("Something's Got A Hold On Me - Burlesque Original Motion Picture Soundtrack");
        expected.add("Doesn't Mean Anything");
        expected.add("Hard");
        expected.add("Loca");
        expected.add("You Lost Me");
        expected.add("Not Myself Tonight");
        expected.add("Written in the Stars (feat. Eric Turner)");
        expected.add("DJ Got Us Fallin' In Love (feat. Pitbull)");
        expected.add("Castle Walls (feat. Christina Aguilera)");
        expected.add("Break Your Heart");
        expected.add("Hello");

        //test fails if any of the expected songs do not
        // appear in the program output
        for(String title : expected){
            Assertions.assertTrue(outputMax.contains(title));
        }

        //close scanner
        testScannerMax.close();

        //valid min, null max

        //instantiate TextUITester with a min of 2019 but no max, so the
        //only songs returned are the ones released in 2019
        TextUITester testMin = new TextUITester("L\nsongs.csv\nG\n2019\n\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerMin = new Scanner(System.in);
        IterableRedBlackTree<Song> treeMin = new IterableRedBlackTree<>();
        Backend backendMin = new Backend(treeMin);
        Frontend frontendMin = new Frontend(testScannerMin, backendMin);

        //run the program
        frontendMin.runCommandLoop();

        //test fails if output is empty
        String outputMin = testMin.checkOutput();
        Assertions.assertTrue(!outputMin.isEmpty());

        //since the min is set as 2019 with no max, only songs released in 2019
        // should be outputted
        expected.clear();
        expected.add("Memories");
        expected.add("Lose You To Love Me");
        expected.add("Someone You Loved");
        expected.add("Se�orita");
        expected.add("How Do You Sleep?");
        expected.add("South of the Border (feat. Camila Cabello & Cardi B)");
        expected.add("Trampoline (with ZAYN)");
        expected.add("Happier");
        expected.add("Truth Hurts");
        expected.add("Good as Hell (feat. Ariana Grande) - Remix");
        expected.add("Higher Love");
        expected.add("Only Human");
        expected.add("Beautiful People (feat. Khalid)");
        expected.add("Sucker");
        expected.add("Don't Call Me Up");
        expected.add("I Don't Care (with Justin Bieber)");
        expected.add("Talk (feat. Disclosure)");
        expected.add("Giant (with Rag'n'Bone Man)");
        expected.add("Takeaway");
        expected.add("All Around The World (La La La)");
        expected.add("Girls Like You (feat. Cardi B)");
        expected.add("Call You Mine");
        expected.add("No Guidance (feat. Drake)");
        expected.add("Antisocial (with Travis Scott)");
        expected.add("Taki Taki (feat. Selena Gomez, Ozuna & Cardi B)");
        expected.add("Con Calma - Remix");
        expected.add("Find U Again (feat. Camila Cabello)");
        expected.add("Cross Me (feat. Chance the Rapper & PnB Rock)");
        expected.add("No Brainer (feat. Justin Bieber, Chance the Rapper & Quavo)");
        expected.add("Nothing Breaks Like a Heart (feat. Miley Cyrus)");
        expected.add("Kills You Slowly");

        //test fails if any of the expected songs are not in the output
        for(String title : expected){
            Assertions.assertTrue(outputMin.contains(title));
        }

        //close scanner
        testScannerMin.close();


        //valid min/max

        //instantiate the TextUITester object with a min of 2018 and a max of 2019
        // this means that all songs from 2018 and 2019 should be outputted
        TextUITester testBoth = new TextUITester("L\nsongs.csv\nG\n2018\n2019\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerBoth = new Scanner(System.in);
        IterableRedBlackTree<Song> treeBoth = new IterableRedBlackTree<>();
        Backend backendBoth = new Backend(treeBoth);
        Frontend frontendBoth = new Frontend(testScannerBoth, backendBoth);

        //run the program
        frontendBoth.runCommandLoop();

        //test fails if output is empty
        String outputBoth = testBoth.checkOutput();
        Assertions.assertTrue(!outputBoth.isEmpty());

        //since min=2018 and max=2019, all songs released in 2018 or 2019
        //should be outputted
        expected.clear();
        expected.add("One Kiss (with Dua Lipa)");
        expected.add("Havana (feat. Young Thug)");
        expected.add("I Like It");
        expected.add("New Rules");
        expected.add("There's Nothing Holdin' Me Back");
        expected.add("no tears left to cry");
        expected.add("IDGAF");
        expected.add("In My Blood");
        expected.add("Wolves");
        expected.add("Dusk Till Dawn - Radio Edit");
        expected.add("Attention");
        expected.add("Electricity (with Dua Lipa)");
        expected.add("Love On The Brain");
        expected.add("Let Me Go (with Alesso, Florida Georgia Line & watt)");
        expected.add("Silence");
        expected.add("Sorry Not Sorry");
        expected.add("Shallow - Radio Edit");
        expected.add("These Days");
        expected.add("What Lovers Do (feat. SZA)");
        expected.add("Finesse - Remix; feat. Cardi B");
        expected.add("Perfect Duet (Ed Sheeran & Beyonc�)");
        expected.add("Bad At Love");
        expected.add("Him & I (with Halsey)");
        expected.add("Friends (with BloodPop�)");
        expected.add("Wild Thoughts (feat. Rihanna & Bryson Tiller)");
        expected.add("My My My!");
        expected.add("Capital Letters");
        expected.add("Sick Boy");
        expected.add("Tequila");
        expected.add("Look What You Made Me Do");
        expected.add("Youth (feat. Khalid)");
        expected.add("Bad Liar");
        expected.add("Anywhere");
        expected.add("Say Something");
        expected.add("Chun-Li");
        expected.add("Sign of the Times");
        expected.add("Familiar");
        expected.add("Let Me");
        expected.add("Supernova");
        expected.add("Nervous");
        expected.add("First Time");
        expected.add("End Game");
        expected.add("Mi Gente (feat. Beyonc�)");
        expected.add("Lemon");
        expected.add("For You (With Rita Ora)");
        expected.add("Want To");
        expected.add("What I Need (feat. Kehlani)");
        expected.add("Wait");
        expected.add("What About Us");
        expected.add("Kissing Strangers");
        expected.add("2U (feat. Justin Bieber)");
        expected.add("Walk On Water (feat. Beyonc�)");
        expected.add("This Town");
        expected.add("Girls (feat. Cardi B, Bebe Rexha & Charli XCX)");
        expected.add("MOVE TO MIAMI");
        expected.add("Miss You (with Major Lazer & Tory Lanez)");
        expected.add("Filthy");
        expected.add("Never Be the Same - Radio Edit");
        expected.add("Ferrari");
        expected.add("Supplies");
        expected.add("Boom Boom");
        expected.add("...Ready For It? - BloodPop� Remix");
        expected.add("Drip (feat. Migos)");
        expected.add("Tell Me You Love Me - NOTD Remix");
        expected.add("Memories");
        expected.add("Lose You To Love Me");
        expected.add("Someone You Loved");
        expected.add("Se�orita");
        expected.add("How Do You Sleep?");
        expected.add("South of the Border (feat. Camila Cabello & Cardi B)");
        expected.add("Trampoline (with ZAYN)");
        expected.add("Happier");
        expected.add("Truth Hurts");
        expected.add("Good as Hell (feat. Ariana Grande) - Remix");
        expected.add("Higher Love");
        expected.add("Only Human");
        expected.add("Beautiful People (feat. Khalid)");
        expected.add("Sucker");
        expected.add("Don't Call Me Up");
        expected.add("I Don't Care (with Justin Bieber)");
        expected.add("Talk (feat. Disclosure)");
        expected.add("Giant (with Rag'n'Bone Man)");
        expected.add("Takeaway");
        expected.add("All Around The World (La La La)");
        expected.add("Girls Like You (feat. Cardi B)");
        expected.add("Call You Mine");
        expected.add("No Guidance (feat. Drake)");
        expected.add("Antisocial (with Travis Scott)");
        expected.add("Taki Taki (feat. Selena Gomez, Ozuna & Cardi B)");
        expected.add("Con Calma - Remix");
        expected.add("Find U Again (feat. Camila Cabello)");
        expected.add("Cross Me (feat. Chance the Rapper & PnB Rock)");
        expected.add("No Brainer (feat. Justin Bieber, Chance the Rapper & Quavo)");
        expected.add("Nothing Breaks Like a Heart (feat. Miley Cyrus)");
        expected.add("Kills You Slowly");

        //test fails if any expected songs were not outputted
        for(String title : expected){
            Assertions.assertTrue(outputBoth.contains(title));
        }

        //close scanner
        testScannerBoth.close();

        //check lowercase G
        //instantiate with user input being lowercase, should have same
        //output as first test in this test method
        TextUITester testLower = new TextUITester("l\nsongs.csv\ng\n\n\n\nq");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerLower = new Scanner(System.in);
        IterableRedBlackTree<Song> treeLower = new IterableRedBlackTree<>();
        Backend backendLower = new Backend(treeLower);
        Frontend frontendLower = new Frontend(testScannerLower, backendLower);

        //run program
        frontendLower.runCommandLoop();

        //test fails if output is empty
        String outputLower = testLower.checkOutput();
        Assertions.assertTrue(!outputLower.isEmpty());

        //determine if output contains output specified in Frontend when user
        //enters G to get songs
        Assertions.assertTrue(output.contains("Enter minimum year (or press enter for no minimum): ")
                && output.contains("Enter maximum year (or press enter for no maximum): ")
                && output.contains("Songs in the specified range:"));

        //since no min/max was specified, all songs should be returned
        Iterator<Song> treeLowerIterator = treeLower.iterator();
        ArrayList<String> songTitlesLower = new ArrayList<>();

        while(treeLowerIterator.hasNext()){
            songTitlesLower.add(treeLowerIterator.next().getTitle());
        }

        //test fails if not all songs are outputted
        for(String title : songTitlesLower){
            Assertions.assertTrue(outputLower.contains(title));
        }

        //close scanner
        testScannerLower.close();
    }

    /**
     * This integration test determines if the program correctly sets a filter
     * based on user input and if the program output is as expected from
     * Frontend.java and Backend.java.
     */
    @Test
    public void IntegrationTest3(){
        //test if F works - filter

        // no filter should return all songs
        //instantiate TextUITester object with user input so there is no filter
        TextUITester tester = new TextUITester("L\nsongs.csv\nF\n\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScanner = new Scanner(System.in);
        IterableRedBlackTree<Song> tree = new IterableRedBlackTree<>();
        Backend backend = new Backend(tree);
        Frontend frontend = new Frontend(testScanner, backend);

        //run program
        frontend.runCommandLoop();

        //test fails if output is empty
        String output = tester.checkOutput();
        Assertions.assertTrue(!output.isEmpty());

        //determine if output contains output specified by Frontend when user wants to
        //add a filter and the filter is valid
        Assertions.assertTrue(output.contains("Enter the maximum loudness threshold (or press enter to clear filter): ")
                && output.contains("Filtered songs:"));

        //since there are no songs filtered out, all songs should be returned
        Iterator<Song> treeIterator = tree.iterator();
        ArrayList<String> songTitles = new ArrayList<>();

        while(treeIterator.hasNext()){
            songTitles.add(treeIterator.next().getTitle());
        }

        //if output doesn't contain all songs, test fails
        for(String title : songTitles){
            Assertions.assertTrue(output.contains(title));
        }

        //close scanner
        testScanner.close();


        //set filter of -13 should just return certain songs

        //instantiate TextUITester with user input where user sets a filter of -13 -
        // only two songs are below that loudness threshold
        TextUITester testWithFilter = new TextUITester("L\nsongs.csv\nF\n-13\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerWithFilter = new Scanner(System.in);
        IterableRedBlackTree<Song> treeWithFilter = new IterableRedBlackTree<>();
        Backend backendWithFilter = new Backend(treeWithFilter);
        Frontend frontendWithFilter = new Frontend(testScannerWithFilter, backendWithFilter);

        //run program
        frontendWithFilter.runCommandLoop();

        //test fails if output is empty
        String outputWithFilter = testWithFilter.checkOutput();
        Assertions.assertTrue(!outputWithFilter.isEmpty());

        //determine if output contains output specified by Frontend.java when filter
        //is valid
        Assertions.assertTrue(outputWithFilter.contains("Enter the maximum loudness threshold (or press enter to clear filter): ")
                && outputWithFilter.contains("Filtered songs:"));

        //since only two songs are below -13 loudness, only those two songs should be returned
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Start");
        expected.add("Million Years Ago");

        //if those songs are not in the output, test fails
        for(String title : expected){
            Assertions.assertTrue(outputWithFilter.contains(title));
        }

        //close scanner
        testScannerWithFilter.close();

        //check threshold that no songs fall under

        //instantiate TextUITester where user specifies a threshold that no songs
        //fall under
        TextUITester testLowFilter = new TextUITester("L\nsongs.csv\nF\n-60\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerLowFilter = new Scanner(System.in);
        IterableRedBlackTree<Song> treeLowFilter = new IterableRedBlackTree<>();
        Backend backendLowFilter = new Backend(treeLowFilter);
        Frontend frontendLowFilter = new Frontend(testScannerLowFilter, backendLowFilter);

        //run program
        frontendLowFilter.runCommandLoop();

        //test fails if output is empty
        String outputLowFilter = testLowFilter.checkOutput();
        Assertions.assertTrue(!outputLowFilter.isEmpty());

        //determine if output contains output specified by Frontend when
        //user inputs a filter that no songs fall under
        Assertions.assertTrue(outputLowFilter.contains("Enter the maximum loudness threshold (or press enter to clear filter): ")
                && outputLowFilter.contains("No songs found below the loudness threshold."));

        //close scanner
        testScannerLowFilter.close();


        //check lowercase f
        //instantiate TextUITester object where user uses lowercase inputs
        //should have same output as first test in this test method
        TextUITester testLower = new TextUITester("L\nsongs.csv\nF\n\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerLower = new Scanner(System.in);
        IterableRedBlackTree<Song> treeLower = new IterableRedBlackTree<>();
        Backend backendLower = new Backend(treeLower);
        Frontend frontendLower = new Frontend(testScannerLower, backendLower);

        //run program
        frontendLower.runCommandLoop();

        //test fails if output is empty
        String outputLower = testLower.checkOutput();
        Assertions.assertTrue(!outputLower.isEmpty());

        //determine if output is as specified by Frontend
        //should return all songs as no filter was specified
        Assertions.assertTrue(outputLower.contains("Enter the maximum loudness threshold (or press enter to clear filter): ")
                && outputLower.contains("Filtered songs:"));

        //should return all songs as no filter was specified
        Iterator<Song> treeLowerIterator = treeLower.iterator();
        ArrayList<String> songTitlesLower = new ArrayList<>();

        while(treeLowerIterator.hasNext()){
            songTitlesLower.add(treeLowerIterator.next().getTitle());
        }

        //if any of the songs in the tree are not in the output, test fails
        for(String title : songTitlesLower){
            Assertions.assertTrue(outputLower.contains(title));
        }

        //close scanner
        testScannerLower.close();
    }

    /**
     * This integration test determines if the program's output is correct
     * and as expected when the user wants to run D, getting the
     * top 5 most danceable songs. This method tests if this works correctly
     * even when a filter or year range is set before user inputs D.
     */
    @Test
    public void IntegrationTest4(){
        //test if D works - danceable songs

        //test d with no filters
        //instantiate TextUITester object with user just wanting top 5 most danceable
        TextUITester tester = new TextUITester("L\nsongs.csv\nD\n\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScanner = new Scanner(System.in);
        IterableRedBlackTree<Song> tree = new IterableRedBlackTree<>();
        Backend backend = new Backend(tree);
        Frontend frontend = new Frontend(testScanner, backend);

        //run program
        frontend.runCommandLoop();

        //test fails if output is empty
        String output = tester.checkOutput();
        Assertions.assertTrue(!output.isEmpty());

        //determine if output contains output specified by Frontend when user
        //inputs D without filter/range
        Assertions.assertTrue(output.contains("Top five most danceable songs:"));

        //thus it returns the 5 most danceable songs out of the entire songs list
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Bad Liar");
        expected.add("Drip (feat. Migos)");
        expected.add("Anaconda");
        expected.add("Come Get It Bae");
        expected.add("WTF (Where They From)");

        //if any of the expected songs were not output, test fails
        for(String title : expected){
            Assertions.assertTrue(output.contains(title));
        }
        //close scanner
        testScanner.close();

        //add years then test d
        //instantiate TextUITester object with user input that sets the range to songs released
        //in 2010, then calls topFive
        //should return top 5 danceable songs released in 2010
        TextUITester testWithYears = new TextUITester("L\nsongs.csv\nG\n2010\n2010\nD\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerWithYears = new Scanner(System.in);
        IterableRedBlackTree<Song> treeWithYears = new IterableRedBlackTree<>();
        Backend backendWithYears = new Backend(treeWithYears);
        Frontend frontendWithYears = new Frontend(testScannerWithYears, backendWithYears);

        //run program
        frontendWithYears.runCommandLoop();

        //test fails if output is empty
        String outputWithYears = testWithYears.checkOutput();
        Assertions.assertTrue(!outputWithYears.isEmpty());

        //determine if output contains output specified by Frontend when user
        //inputs D with a range
        Assertions.assertTrue(outputWithYears.contains("Top five most danceable songs:"));

        //output should contain the top 5 danceable songs from songs
        //released in 2010
        expected.clear();
        expected.add("Telephone");
        expected.add("Your Love Is My Drug");
        expected.add("The Time (Dirty Bit)");
        expected.add("Sexy Bitch (feat. Akon)");
        expected.add("Loca");

        //test fails if any expected songs were not output
        for(String title : expected){
            Assertions.assertTrue(outputWithYears.contains(title));
        }

        //close scanner
        testScannerWithYears.close();

        //add filter then test d
        //instantiate TextUITester object with user input when user sets a filter of -10
        // then asks for top 5 most danceable songs
        TextUITester testWithFilter = new TextUITester("L\nsongs.csv\nF\n-10\nD\nQ");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerWithFilter = new Scanner(System.in);
        IterableRedBlackTree<Song> treeWithFilter = new IterableRedBlackTree<>();
        Backend backendWithFilter = new Backend(treeWithFilter);
        Frontend frontendWithFilter = new Frontend(testScannerWithFilter, backendWithFilter);

        //run program
        frontendWithFilter.runCommandLoop();

        //test fails if output is empty
        String outputWithFilter = testWithFilter.checkOutput();
        Assertions.assertTrue(!outputWithFilter.isEmpty());

        //ensure output contains output specified by Frontend when user
        //wants top 5 danceable songs after a filter is placed
        Assertions.assertTrue(outputWithFilter.contains("Top five most danceable songs:"));

        //expected songs to be returned are the top 5 most danceable songs
        //from songs that are below the -10 loudness threshold
        expected.clear();
        expected.add("Behind Your Back");
        expected.add("What Do You Mean? - Acoustic");
        expected.add("Influence");
        expected.add("Love Yourself");
        expected.add("Love Yourself");

        //if any expected songs are not outputted, test fails
        for(String title : expected){
            Assertions.assertTrue(outputWithFilter.contains(title));
        }

        //close scanner
        testScannerWithFilter.close();

        //test lowercase d

        //instantiate TextUITester with user input when user inputs lowercase letters
        TextUITester testLower = new TextUITester("l\nsongs.csv\nd\n\nq");
        //instantiate scanner, tree, backend, frontend
        Scanner testScannerLower = new Scanner(System.in);
        IterableRedBlackTree<Song> treeLower = new IterableRedBlackTree<>();
        Backend backendLower = new Backend(treeLower);
        Frontend frontendLower = new Frontend(testScannerLower, backendLower);

        //run program
        frontendLower.runCommandLoop();

        //test fails if output is empty
        String outputLower = testLower.checkOutput();
        Assertions.assertTrue(!outputLower.isEmpty());

        //determine if output contains output specified by frontend when user
        //wants most danceable songs without a filter/range
        Assertions.assertTrue(outputLower.contains("Top five most danceable songs:"));

        //should return top 5 danceable songs out of all the songs,
        // same as first test in this method
        expected.clear();
        expected.add("Bad Liar");
        expected.add("Drip (feat. Migos)");
        expected.add("Anaconda");
        expected.add("Come Get It Bae");
        expected.add("WTF (Where They From)");

        //if any expected songs are not output, test fails
        for(String title : expected){
            Assertions.assertTrue(outputLower.contains(title));
        }

        //close scanner
        testScannerLower.close();
    }
}
