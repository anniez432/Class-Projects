////////////////FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
//Title:    Wardrobe Manager Tester
//Course:   CS 300 Spring 2024
//
//Author:   Annie Zhao
//Email:    azhao37@wisc.edu
//Lecturer: Hobbes LeGault
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//Partner Name:    N/A
//Partner Email:   N/A
//Partner Lecturer's Name: N/A
//
//VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//___ Write-up states that pair programming is allowed for this assignment.
//___ We have both read and understand the course Pair Programming Policy.
//___ We have registered our team prior to the team registration deadline.
//
////////////////////////ASSISTANCE/HELP CITATIONS ////////////////////////////
//
//Persons:         None
//Online Sources:  The project specifications document attached to the 
//					assignment in Canvas:
//					https://canvas.wisc.edu/courses/398763/assignments/2161027 
//				   
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Arrays;

/**
 * This class tests the methods in WardrobeManager. If the tests are passed, the
 * methods work as expected. 
 */
public class WardrobeManagerTester {
  
  //// containsClothing()
  
	/**
	 * Checks whether an object exists in an empty wardrobe/
	 * *
	 * @return false if the conditions tested are not what are expected; true
	 * 			if all expectations are correct
	 */
  public static boolean testContainsEmpty() {
	  
	//Initializing the test variables
	String[][] empty = new String[10][];
	String[][] emptyCopy = Arrays.copyOf(empty, empty.length);
	
	int size = 0;
	boolean expected = false;
	
	//Calling the method being tested
	boolean actual = WardrobeManager.containsClothing("black t-shirt", 
			"hanes", empty, size);
	 
	//Verifying if the expected and actual return values match
	if(expected != actual) return false;

	//Verifying that the array was not changed
	if (!Arrays.deepEquals(empty, emptyCopy)) return false;
	
    return true;
  }
  
  /**
   * PROVIDED: example test method for verifying whether an item is already in the wardrobe.
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testContainsTrue() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String[][] wardrobe = {{"black t-shirt", "Hanes", "never"}, 
    		{"dark blue jeans", "Levi", "never"},
        null, null, null};
    String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
    int size = 2;
    boolean expected = true;
    boolean actual = WardrobeManager.containsClothing("black t-shirt", 
    		"Hanes", wardrobe, size);
    
    // (2) verify that the expected return value and the actual return value match
    if (expected != actual) return false;
    
    // (3) another test method call, this time with case difference (that should still match!)
    actual = WardrobeManager.containsClothing("dark blue jeans", 
    		"LEVI", wardrobe, size);
    if (expected != actual) return false;
    
    // (4) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
    
    // (5) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  
  /**
   * Checks if the object exists in the wardrobe
   * 
   * @return false if the expected conditions are not met; true if all are met
   */
  public static boolean testContainsFalse() {
	  
	  //Initializing test variables
	  String[][] wardrobe = {{"black t-shirt", "Hanes", "never"}, 
			  {"dark blue jeans", "Levi", "never"},
		        null, null, null};
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  int size = 2;
	  boolean expected = false;
	  
	  //Calling the method
	  boolean actual = WardrobeManager.containsClothing("white t-shirt", 
			  "Hanes", wardrobe, size);
	  
	  if (expected != actual) return false;
		    
	  //Calling the method again to ensure case differences do not affect the result
	  actual = WardrobeManager.containsClothing("dark blue jeans", 
			  "HANES", wardrobe, size);
	  if (expected != actual) return false;
	  
	  //Checking that the array was not changed
	  if (!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	    
	    
    return true;
  }
  
  //// addClothing()
  
  /**
   * PROVIDED: example test method for adding a new clothing item to an EMPTY oversize array.
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testAddToEmpty() {
    // (1) set up the test variables and call the method we are testing
    String[][] empty = new String[10][];
    int size = 0;
    int expected = 1;
    int actual = WardrobeManager.addClothing("green crop top", "H&M", empty, size);
    
    // (2) verify the expected return value
    if (expected != actual) return false;

    // (3) verify that the provided array was updated correctly
    if (empty[0] == null) return false;
    if (!empty[0][0].equalsIgnoreCase("green crop top") || 
    		!empty[0][1].equalsIgnoreCase("H&M") ||
        !empty[0][2].equals("never")) return false;
    
    // (4) verify that NOTHING ELSE was changed unexpectedly
    for (int i=1; i<empty.length; i++) {
      if (empty[i] != null) return false;
    }
    
    // (5) if all of those checks pass, NOW we can say that we passed the test
    return true;
  }
  
  /**
   * Checks if the method addClothing() can add an object to a non-empty wardrobe.
   * 
   * @return false if the resulting conditions are not the same as expected;
   *  			true if expectations are met. 
   */
  public static boolean testAddNonEmpty() {
	  
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[]{"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[]{"black t-shirt", "pacsun", "never"};
	  
	  int size = 2;
	  int expected = 3;
	  
	  //Calling the method
	  int actual = WardrobeManager.addClothing("navy t-shirt", "hanes", wardrobe, size);
	  
	  if(expected != actual) {
		  return false;
	  }
	  
	  //Checking that the object was added
	  if (wardrobe[2] == null) return false;
	  
	  //Checking that the correct object was added
	  if (!wardrobe[2][0].equalsIgnoreCase("navy t-shirt") || 
			  !wardrobe[2][1].equalsIgnoreCase("hanes") ||
	        !wardrobe[2][2].equalsIgnoreCase("never")) return false;
	    
    return true;
  }
  
  /**
   * Checks if the method will add an object already in the wardrobe
   * 
   * @return false if expectations are not met; true if they are all met
   */
  public static boolean testAddDuplicate() {
	  
	  //Initializing the test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "pacsun", "never"};
	  
	  int size = 2;
	  int expected = 2;
	  
	  //Calling the method
	  int actual = WardrobeManager.addClothing("white t-shirt", 
			  "hanes", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	 
    return true;
  }
  
  /**
   * Checks if the method will add an object to a full wardrobe
   * 
   * @return false if any expectations are not met; true if all are met
   */
  public static boolean testAddToFull() {
	  
	  //Initializing the test variables
	  String[][] wardrobe = new String[2][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "pacsun", "never"};
	  
	  int size = 2;
	  int expected = 2;
	  
	  //Calling the method
	  int actual = WardrobeManager.addClothing("navy t-shirt", 
			  "levi", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
    return true;
  }
  
  //// indexOfClothing()
  
  /**
   * Checks if the method will determine the index of an object in an 
   * 	empty wardrobe.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testIndexOfEmpty() {
	  //Initializing the test variables
	  String[][] wardrobe = new String[10][];
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  
	  int size = 0;
	  int expected = -1;
	  
	  //Calling the method
	  int actual = WardrobeManager.indexOfClothing("white t-shirt", 
			  "hanes", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking that the wardrobe was not edited
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
	  
    return true; 
  }
  
  /**
   * Checks if the method will determine the index of an object as either
   * 	the first or last index in the wardrobe.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testIndexOfFirstLast() {
	  
	  //Initializing of test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "pacsun", "never"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  
	  int size = 2;
	  int expected = 0;
	  
	  //Calling the method to verify if the first index object is identified
	  int actual = WardrobeManager.indexOfClothing("white t-shirt", 
			  "hanes", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Verifying if the last index object is identified
	  expected = 1;
	  actual = WardrobeManager.indexOfClothing("black t-shirt", 
			  "pacsun", wardrobeCopy, size);
	  if(expected != actual) return false;
	  
	  //Checking that the wardrobe was not edited
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true;
  }
  
  /**
   * Checks if the index of an object in the middle of the wardrobe
   * 	will be determined.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testIndexOfMiddle() {
	  
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "pacsun", "never"};
	  wardrobe[2] = new String[] {"navy t-shirt", "hanes", "never"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  
	  int size = 2;
	  int expected = 1;
	  
	  //Calling the method
	  int actual = WardrobeManager.indexOfClothing("black t-shirt", 
			  "pacsun", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Verifying that the wardrobe was not edited
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true;
  }
  
  /**
   * Checks if an index value of an object not in the wardrobe will
   * 	be determined.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testIndexOfNoMatch() {
	  
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "pacsun", "never"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  int size = 2;
	  int expected = -1;
	  
	  //Calling the method
	  int actual = WardrobeManager.indexOfClothing("pink t-shirt", 
			  "pacsun", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking that the wardrobe was not edited
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true;
  }
  
  //// wearClothing()
  
  /**
   * Checks whether an object in the wardrobe has been worn before.
   * 
   * @return false if any expectations are not met, true if they all are
   */
  public static boolean testWearClothingTrue() {
	  
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "2023-01-05"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  wardrobe[2] = new String[] {"pink t-shirt", "hanes", "2023-01-05"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	 
	  int size = 3;
	  boolean expected = true;
	  
	  //Calling the method
	  boolean actual = WardrobeManager.wearClothing("white t-shirt", "hanes", 
			  "2024-01-08", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe was changed
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true; 
  }
  
  /**
   * Checks if an object's worn date matches the objects in the wardrobe
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testWearClothingNoMatch() {
	  
	  //Initializing the test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "2023-01-05"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  wardrobe[2] = new String[] {"pink t-shirt", "hanes", "never"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	 
	  int size = 3;
	  boolean expected = false;
	  
	  //Calling the method
	  boolean actual = WardrobeManager.wearClothing("white t-shirt", "levi", 
			  "2023-07-05", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe was changed
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true; 
  }
  
  //// getBrandCount()
  
  /**
   * Checks if the method determines the amount of times a brand
   * 	is present in the wardrobe.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testBrandCountAllMatch() {
	  
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  wardrobe[2] = new String[] {"pink t-shirt", "hanes", "never"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  int size = 3;
	  int expected = 3;
	  
	  //Calling the method
	  int actual = WardrobeManager.getBrandCount("hanes", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe was edited
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true;
  }
  
  /**
   * Checks if the method determines the amount of times a brand
   * 	is present in the wardrobe, when there are other brands as well.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testBrandCountSomeMatch() {
	  
	  //Initializing the test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  wardrobe[2] = new String[] {"pink t-shirt", "pacsun", "never"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  int size = 3;
	  int expected = 2;
	  //Calling the method
	  int actual = WardrobeManager.getBrandCount("hanes", wardrobe, size);
	  if(expected != actual) return false;
	  
	  //Calling the method with another brand name 
	  expected = 1;
	  actual = WardrobeManager.getBrandCount("pacsun", wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe was changed
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true;
  }
  
  /**
   * Checks if the method determines the amount of times a brand
   * 	is present in the wardrobe.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testBrandCountNoMatch() {
	  
	  //Initializing the test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  wardrobe[2] = new String[] {"pink t-shirt", "pacsun", "never"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  int size = 3;
	  int expected = 0;
	  
	  //Calling the method
	  int actual = WardrobeManager.getBrandCount("levi", wardrobe, size);
	  if(expected != actual) return false;
	
	  //Checking if the wardrobe was changed
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true;
  }
  
  ////getNumUnwornClothes()
  
  /**
   * Checks if the method determines the amount of objects in the wardrobe
   * 	have not been worn.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testUnwornCountAllMatch() {
	  
	  //Initializing the test variables
	  String[][] wardrobe = {{"black t-shirt", "Hanes", "never"}, 
		        {"grey UW hoodie", "gildan", "never"},
		        {"dark blue jeans", "Levi", "never"},
		        {"green cabled sweater", "handmade", "never"}, null};
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  
	  int size = 4;
	  int expected = 4;
	  
	  //Calling the method
	  int actual = WardrobeManager.getNumUnwornClothes(wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe was changed
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
    return true;
  }
  
  /**
   * Checks if the method determines the amount of clothes in the
   * 	wardrobe that have not been worn/
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testUnwornCountSomeMatch() {
	  
	  //Initializing test variables
	  String[][] wardrobe = {{"black t-shirt", "Hanes", "2023-01-30"}, 
		        {"grey UW hoodie", "gildan", "never"},
		        {"dark blue jeans", "Levi", "never"},
		        {"green cabled sweater", "handmade", "never"}, null};
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  
	  int size = 4;
	  int expected = 3;
	  
	  //Calling the method
	  int actual = WardrobeManager.getNumUnwornClothes(wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe has changed
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
	  return true; 
  }
  
  /**
   * Checks if the method determines the amount of times objects\ in the
   * 	wardrobe have not been worn.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testUnwornCountNoMatch() {
	  
	  //Initializing test variables
	  String[][] wardrobe = {{"black t-shirt", "Hanes", "2022-03-04"}, 
		        {"grey UW hoodie", "gildan", "2021-02-19"},
		        {"dark blue jeans", "Levi", "2024-01-23"},
		        {"green cabled sweater", "handmade", "2022-09-12"}, null};
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  
	  int size = 4;
	  int expected = 0;
	  
	  //Calling the method
	  int actual = WardrobeManager.getNumUnwornClothes(wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe changed
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
    return true; // TODO
  }
  
  //// getMostRecentlyWorn()
  
  /**
   * PROVIDED: example test method for verifying that the most recently worn item is found correctly.
   * Note that this tester is not comprehensive; if you wish to verify additional behavior you are
   * welcome to add additional tester methods (please specify such methods to be PRIVATE).
   * 
   * @return false if any of the conditions we are verifying are not what we expect; true ONLY if
   *         all of our expectations are correct
   */
  public static boolean testMostRecentlyWorn() {
    // (1) set up the test variables and call the method we are testing - EXACT MATCH
    String[][] wardrobe = {{"black t-shirt", "Hanes", "2022-01-29"}, 
        {"grey UW hoodie", "gildan", "2024-03-15"},
        {"dark blue jeans", "Levi", "2024-01-29"},
        {"green cabled sweater", "handmade", "never"}, null};
    String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
    int size = 4;
    int expected = 1;
    int actual = WardrobeManager.getMostRecentlyWorn(wardrobe, size);
    
    // (2) verify that the expected return value and the actual return value match
    if (expected != actual) return false;
    
    // (3) since this method should not modify the array, let's check it against our copy:
    if (!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
    
    // (4) if all of those checks pass, NOW we can say we passed the test
    return true;
  }
  
  //// removeClothingAtIndex()
  
  /**
   * Checks if the method removes the object that is located
   * 	at the last index.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testRemoveLastItem() {
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  
	  int size = 2;
	  int expected = 1;
	  //Calling the method
	  int actual = WardrobeManager.removeClothingAtIndex(size - 1, wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking that the first element was not changed
	  if(wardrobe[0] == null) return false;
	  
	  if(!wardrobe[0][0].equalsIgnoreCase("white t-shirt") || 
			  !wardrobe[0][1].equalsIgnoreCase("hanes") ||
			  !wardrobe[0][2].equalsIgnoreCase("never")) return false;
		  
	  
    return true; 
  }
  
  /**
   * Checks if the method removes the object located at the first index.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testRemoveFirstItem() {
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  
	  int size = 2;
	  int expected = 1;
	  
	  //Calling the method
	  int actual = WardrobeManager.removeClothingAtIndex(0, wardrobe, size);
	  
	  if(expected != actual) return false;
	  return true;
  }
  
  /**
   * Checks if the method will remove an object at an invalid index in
   * 	the wardrobe.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testRemoveBadIndex() {
	  
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  
	  int size = 2;
	  int expected = 2;
	  
	  //Calling the method
	  int actual = WardrobeManager.removeClothingAtIndex(size, wardrobe, size);
	  
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe was changed
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	  
	  return true; 
  }
  
  //// removeAllUnworn()
  
  /**
   * Checks if the method removes unworn objects from a wardrobe
   * 	in which there are none.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testRemoveUnwornNoMatch() {
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "2023-01-20"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "2024-1-1"};
	  wardrobe[2] = new String[] {"pink t-shirt", "pacsun", "2023-09-28"};
	  String[][] wardrobeCopy = Arrays.copyOf(wardrobe, wardrobe.length);
	  
	  int size = 3;
	  int expected = 3;
	  
	  //Calling the method
	  int actual = WardrobeManager.removeAllUnworn(wardrobe, size);
	  if(expected != actual) return false;
	  
	  //Checking if the wardrobe was changes
	  if(!Arrays.deepEquals(wardrobe, wardrobeCopy)) return false;
	 
    return true;
  }
  
  /**
   * Checks if the method removes the objects in the wardrobe that
   * 	have never been worn.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testRemoveUnwornSomeMatch() {
	  
	  //Initializing the test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "2024-1-1"};
	  wardrobe[2] = new String[] {"pink t-shirt", "pacsun", "never"};
	  
	  int size = 3;
	  int expected = 1;
	  
	  //Calling the method
	  int actual = WardrobeManager.removeAllUnworn(wardrobe, size);
	  if(expected != actual) return false;
	 
    return true; 
  }
  
  /**
   * Checks if the method removes all objects in a wardrobe that 
   * 	have never been worn before.
   * 
   * @return false if any expectations are not met; true if they all are
   */
  public static boolean testRemoveUnwornAllMatch() {
	  //Initializing test variables
	  String[][] wardrobe = new String[10][];
	  wardrobe[0] = new String[] {"white t-shirt", "hanes", "never"};
	  wardrobe[1] = new String[] {"black t-shirt", "hanes", "never"};
	  wardrobe[2] = new String[] {"pink t-shirt", "pacsun", "never"};
	  
	  int size = 3;
	  int expected = 0;
	  
	  //Calling the method
	  int actual = WardrobeManager.removeAllUnworn(wardrobe, size);
	  if(expected != actual) return false;
	 
    return true;
  }

  /**
   * PROVIDED: calls all tester methods and displays the results of the tests.
   * 
   * All tests are called in the order they were provided in this file. The output of this method
   * will NOT be graded so you may modify it however you wish.
   * 
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("CONTAINS:\n  "+(testContainsEmpty()?"pass":"FAIL")+", "+
        (testContainsTrue()?"pass":"FAIL")+", "+(testContainsFalse()?"pass":"FAIL"));
    System.out.println("ADD:\n  "+(testAddToEmpty()?"pass":"FAIL")+", "+(testAddNonEmpty()?"pass":"FAIL")+
        ", "+(testAddDuplicate()?"pass":"FAIL")+", "+(testAddToFull()?"pass":"FAIL"));
    System.out.println("INDEX OF:\n  "+(testIndexOfEmpty()?"pass":"FAIL")+", "+(testIndexOfFirstLast()?"pass":"FAIL")+
        ", "+(testIndexOfMiddle()?"pass":"FAIL")+", "+(testIndexOfNoMatch()?"pass":"FAIL"));
    System.out.println("WEAR:\n  "+(testWearClothingTrue()?"pass":"FAIL")+", "+(testWearClothingNoMatch()?"pass":"FAIL"));
    System.out.println("BRAND COUNT:\n  "+(testBrandCountAllMatch()?"pass":"FAIL")+", "+
        (testBrandCountSomeMatch()?"pass":"FAIL")+", "+(testBrandCountNoMatch()?"pass":"FAIL"));
    System.out.println("UNWORN COUNT:\n  "+(testUnwornCountAllMatch()?"pass":"FAIL")+", "+
        (testUnwornCountSomeMatch()?"pass":"FAIL")+", "+(testUnwornCountNoMatch()?"pass":"FAIL"));
    System.out.println("MOST RECENTLY WORN:\n  "+(testMostRecentlyWorn()?"pass":"FAIL"));
    System.out.println("REMOVE BY INDEX:\n  "+(testRemoveLastItem()?"pass":"FAIL")+", "+
        (testRemoveFirstItem()?"pass":"FAIL")+", "+(testRemoveBadIndex()?"pass":"FAIL"));
    System.out.println("REMOVE UNWORN:\n  "+(testRemoveUnwornNoMatch()?"pass":"FAIL")+", "+
        (testRemoveUnwornSomeMatch()?"pass":"FAIL")+", "+(testRemoveUnwornAllMatch()?"pass":"FAIL"));
  }

}
