//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    PackingTester for P06 Assignment
// Course:   CS 300 Spring 2024
//
// Author:   Rebecca Tran
// Email:    rmtran2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Annie Zhao
// Partner Email:   azhao37@wisc.edu
// Partner Lecturer's Name: Hobbes LeGault
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   X Write-up states that pair programming is allowed for this assignment.
//   X We have both read and understand the course Pair Programming Policy.
//   X We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;

/**
 * Class used for testing the methods in the Packing class.
 */
public class PackingTester {
  /**
   * Tester method for the Packing.rushedPacking() method base cases.
   * It should test at least the following scenarios:
   * - There are no items left to pack in the suitcase
   * - There are items left to pack, but none of them fit
   * @return true if all tests pass, false otherwise
   */
  public static boolean rushedPackingBaseTest() {
    // create items that WON'T fit to try to pack into the suitcase
    Item shirt = new Item("shirt", 100, 100);
    Item pants = new Item("pants", 200, 10);
    Item hat = new Item("hat", 150, 10);

    // Test case 1: There are items to pack, but none of them fit.
    ArrayList<Item> itemsToPack = new ArrayList<>();
    itemsToPack.add(shirt);
    itemsToPack.add(pants);
    itemsToPack.add(hat);
    Suitcase suitcase1 = new Suitcase(50, 25, itemsToPack);
    suitcase1 = Packing.rushedPacking(suitcase1);
    // Check if no items are packed. None should be as they don't fit
    if (suitcase1.numItemsPacked() != 0) {
      return false; }
    if (suitcase1.numItemsUnpacked() != 3) { // the items should remain in the numUnpacked list
      return false; }
    if (!suitcase1.getPackedItems().isEmpty()){ // checking lists. there should be no packed items
      return false; }
    if (suitcase1.getUnpackedItems().isEmpty()){ // there should be items in the unpacked list
      return false; }

    // Test case 2: There are no items to pack in the suitcase
    ArrayList<Item> items2Pack = new ArrayList<>();
    Suitcase suitcase2 = new Suitcase(100, 50, items2Pack);
    suitcase2 = Packing.rushedPacking(suitcase2);
    if (suitcase2.numItemsUnpacked() != 0){ // check if there are correctly no items to pack
      return false; }
    if (suitcase2.numItemsPacked() != 0 ) { // check if there are correctly no items packed
      return false; }
    if (!suitcase2.getPackedItems().isEmpty()){ // there should be no items in the packed list
      return false; }
    if (!suitcase2.getUnpackedItems().isEmpty()){ // there should be no items in the unpacked list
      return false; }

    // return true if ALL tests pass
    return true;
  }

  /**
   * Tester method for the Packing.rushedPacking() method recursive cases.
   * It should test at least the following scenarios:
   * - All the items remaining can fit in the suitcase
   * - At least one item remaining cannot fit in the suitcase
   * @return true if all tests pass, false otherwise
   */
  public static boolean rushedPackingRecursiveTest() {

    // create items that will ALL fit into suitcase
    Item shirt = new Item("shirt", 10, 5);
    Item pants = new Item("pants", 5, 20);
    Item hat = new Item("hat", 3, 1);
    ArrayList<Item> itemsToPack = new ArrayList<>();
    itemsToPack.add(shirt);
    itemsToPack.add(pants);
    itemsToPack.add(hat);

    // Test case 1: ALL unpacked items can fit into the suitcase and should be packed
    Suitcase suitcase1 = new Suitcase(50, 30, itemsToPack);
    suitcase1 = Packing.rushedPacking(suitcase1);

    if (suitcase1.numItemsPacked() != 3) return false; // there should be 3 packed items
    if (suitcase1.numItemsUnpacked() != 0) return false; // there should be 0 unpacked items
    if (!suitcase1.getUnpackedItems().isEmpty()) return false; // unpacked list should be empty
    if (suitcase1.getPackedItems().isEmpty()) return false; //packed list should not be empty

    // checking if all items were packed in the CORRECT order
    if (!suitcase1.getPackedItems().get(0).equals(shirt)) return false;
    if (!suitcase1.getPackedItems().get(1).equals(pants)) return false;
    if (!suitcase1.getPackedItems().get(2).equals(hat)) return false;

    //Test case 2: all items fit except 1.
    Item shirt2 = new Item("shirt", 3, 2);
    Item pants2 = new Item("pants", 10, 30);
    Item hat2 = new Item("hat", 2, 1);
    ArrayList<Item> items2Pack = new ArrayList<>();
    items2Pack.add(shirt2);
    items2Pack.add(pants2);
    items2Pack.add(hat2);
    Suitcase suitcase2 = new Suitcase(10, 5, items2Pack);
    suitcase2 = Packing.rushedPacking(suitcase2);

    if (suitcase2.numItemsPacked() != 2) return false; // there should be 2 packed items
    if (suitcase2.numItemsUnpacked() != 1) return false; // there should be 1 unpacked item
    if (suitcase2.getUnpackedItems().isEmpty()) return false; // unpacked list should not be empty
    if (suitcase2.getPackedItems().isEmpty()) return false; // packed list should not be empty

    // check if the items were packed in the correct order and if the packed/unpacked lists were
    // updated correctly
    if (!suitcase2.getPackedItems().get(0).equals(shirt2)) return false;
    if (!suitcase2.getPackedItems().get(1).equals(hat2)) return false;
    if (!suitcase2.getUnpackedItems().get(0).equals(pants2)) return false;

    //Test case 3: only the last item can be packed into the suitcase. 
    Item shirt3 = new Item("shirt", 14, 4);
    Item pants3 = new Item("pants", 10, 14);
    Item hat3 = new Item("hat", 3, 1);
    ArrayList<Item> itemsToPack3 = new ArrayList<>();
    itemsToPack3.add(shirt3);
    itemsToPack3.add(pants3);
    itemsToPack3.add(hat3);
    Suitcase suitcase3 = new Suitcase(12, 5, itemsToPack3);
    suitcase3 = Packing.rushedPacking(suitcase3);
    if (suitcase3.numItemsPacked() != 1) return false; // there should be 1 packed item
    if (suitcase3.numItemsUnpacked() != 2) return false; // there should be 2 unpacked items
    if (suitcase3.getUnpackedItems().isEmpty()) return false; // unpacked list shouldn't be empty
    if (suitcase3.getPackedItems().isEmpty()) return false; // packed list shouldn't be empty

    // check if the items were packed in correct order or left in unpacked list correctly
    if (!suitcase3.getPackedItems().get(0).equals(hat3)) return false;
    if (!suitcase3.getUnpackedItems().get(0).equals(shirt3)) return false;
    if (!suitcase3.getUnpackedItems().get(1).equals(pants3)) return false;


    return true; // return true if all tests pass
  }

  /**
   * Tester method for the Packing.greedyPacking() method base cases.
   * It should test at least the following scenarios:
   * - There are no items left to pack in the suitcase
   * - There are items left to pack, but none of them fit
   * @return true if all tests pass, false otherwise
   */
  public static boolean greedyPackingBaseTest() {

    //Test case 1: there are no items to pack in the suitcase
    ArrayList<Item> items = new ArrayList<>();
    //creates a suitcase that will have with no items to pack
    Suitcase suitcase = new Suitcase(20, 20, items);
    suitcase = Packing.greedyPacking(suitcase);

    //unpacked items list should contain nothing
    if(!suitcase.getUnpackedItems().isEmpty()) return false;
    //packed items list should contain nothing
    if(!suitcase.getPackedItems().equals(items)) return false;
    if (suitcase.numItemsUnpacked() != 0){ // check if there are correctly no items to pack
      return false; }
    if (suitcase.numItemsPacked() != 0 ) { // check if there are correctly no items packed
      return false; }

    //Test case 2: items to pack, but none of them fit
    //create a list for this case and add the shirts and pants items to it
    Item pants = new Item("pants", 15, 20);
    Item shirt = new Item("shirt", 12, 15);
    ArrayList<Item> excessItems = new ArrayList<>();
    excessItems.add(pants);
    excessItems.add(shirt);
    //create a new suitcase for this test that fit none of the items
    Suitcase excessSuitcase = new Suitcase(10, 20, excessItems);
    excessSuitcase = Packing.greedyPacking(excessSuitcase);

    // Check if no items are packed. None should be as they don't fit
    if (excessSuitcase.numItemsPacked() != 0) { // none of the items should be packed
      return false; }
    if (excessSuitcase.numItemsUnpacked() != 2) { // the items should remain in the numUnpacked list
      return false; }
    if (!excessSuitcase.getPackedItems().isEmpty()){ //there should be no packed items
      return false; }
    if (excessSuitcase.getUnpackedItems().isEmpty()){ // there should be items in the unpacked list
      return false; }

    // check if the unpacked items list remained as it should
    if (!excessSuitcase.getUnpackedItems().get(0).equals(pants)) return false;
    if (!excessSuitcase.getUnpackedItems().get(1).equals(shirt)) return false;

    return true; //return true once all tests pass
  }

  /**
   * Tester method for the Packing.greedyPacking() method recursive cases.
   * It should test at least the following scenarios:
   * - At least one item is packed out of order (an item with a higher index
   *   is packed before an item with a lower index)
   * - A scenario where the greedy packing method packs more of the suitcase
   *   than the rushed packing (you can use the example given in the writeup)
   * @return true if all tests pass, false otherwise
   */
  public static boolean greedyPackingRecursiveTest() {

    //TEST ONE: At least one item is packed out of order (an item with a higher index
    // is packed before an item with a lower index)

    //pants is at a higher index but should be packed first
    Item shirt = new Item("shirt", 5, 5);
    Item pants = new Item("pants", 7, 7);
    Item hat = new Item("hat", 4, 4);
    ArrayList<Item> itemsUnpacked = new ArrayList<>();
    itemsUnpacked.add(shirt);
    itemsUnpacked.add(pants);
    itemsUnpacked.add(hat);
    Suitcase suitcase = new Suitcase(50, 50, itemsUnpacked);
    suitcase = Packing.greedyPacking(suitcase);

    if (suitcase.numItemsPacked() != 3) return false; //3 items should be packed
    if (suitcase.numItemsUnpacked() != 0) return false; // 0 items should be unpacked
    if (!suitcase.getUnpackedItems().isEmpty()) return false; //unpacked list should be empty

    //checks if the items were added in the correct order
    if (!suitcase.getPackedItems().get(0).equals(pants)) return false;
    if (!suitcase.getPackedItems().get(1).equals(shirt)) return false;
    if (!suitcase.getPackedItems().get(2).equals(hat)) return false;


    //TEST 2: A scenario where the greedy packing method packs more of the suitcase
    //than the rushed packing (you can use the example given in the writeup)

    //A(4x2), B(6x3), C(7x4), D(4x5), E(4x5), F(5x4), G(2x6)
    Item shirtA = new Item("shirt", 4, 2);
    Item shirtB = new Item("shirt", 6, 3);
    Item shirtC = new Item("shirt", 7, 4);
    Item shirtD = new Item("shirt", 4, 5);
    Item shirtE = new Item("shirt", 4, 5);
    Item shirtF = new Item("shirt", 5, 4);
    Item shirtG = new Item("shirt", 2, 6);

    ArrayList<Item> moreThanRushing = new ArrayList<>();
    moreThanRushing.add(shirtA);
    moreThanRushing.add(shirtB);
    moreThanRushing.add(shirtC);
    moreThanRushing.add(shirtD);
    moreThanRushing.add(shirtE);
    moreThanRushing.add(shirtF);
    moreThanRushing.add(shirtG);

    //suitcase example from write-up
    Suitcase moreThanRushingSuitcase = new Suitcase(10, 10, moreThanRushing);
    Suitcase rushedSuitcase = Packing.rushedPacking(moreThanRushingSuitcase);
    moreThanRushingSuitcase = Packing.greedyPacking(moreThanRushingSuitcase);

    // the area of the suitcase using greedy should be more than the area using rushed
    if (moreThanRushingSuitcase.areaPacked() < rushedSuitcase.areaPacked()) return false;

    if (moreThanRushingSuitcase.numItemsPacked() != 4) return false; //should be 4 packed items
    if (moreThanRushingSuitcase.numItemsUnpacked() != 3) return false; //should be 3 unpacked items

    // check if the packed and unpacked lists are updated correctly
    if (!moreThanRushingSuitcase.getPackedItems().get(0).equals(shirtC)) return false;
    if (!moreThanRushingSuitcase.getPackedItems().get(1).equals(shirtD)) return false;
    if (!moreThanRushingSuitcase.getPackedItems().get(2).equals(shirtE)) return false;
    if (!moreThanRushingSuitcase.getPackedItems().get(3).equals(shirtG)) return false;
    if (!moreThanRushingSuitcase.getUnpackedItems().get(0).equals(shirtA)) return false;
    if (!moreThanRushingSuitcase.getUnpackedItems().get(1).equals(shirtB)) return false;
    if (!moreThanRushingSuitcase.getUnpackedItems().get(2).equals(shirtF)) return false;

    return true; // return true once all tests pass
  }

  /**
   * Tester method for the Packing.optimalPacking() method.
   * This tester should test the optimalPacking() method by
   * randomly generating at least TEN (10) different scenarios,
   * and randomly generating at least ONE-HUNDRED (100)
   * different packing solutions for EACH of the scenarios.
   * Each scenario should have at least FIVE (5) random items,
   * and the suitcases should be of size at least 5x5.
   * If any random solution is better than the optimal packing then
   * it is not actually optimal, so the method does not pass the test.
   * You should use the Utilities method to generate random lists of
   * items, and to randomly pack the suitcases.
   * @return true if all tests pass, false otherwise
   */
  public static boolean optimalPackingRandomTest() {

    //generates 10 random lists of items
    for(int i = 0; i < 10; ++i) {

      //creates the random list
      ArrayList<Item> randomList = Utilities.randomItems(5);

      //creates and determines the optimal packed suitcase using the randomItems
      Suitcase optimalSuitcase = new Suitcase(5, 5, randomList);
      optimalSuitcase = Packing.optimalPacking(optimalSuitcase);

      //creates a suitcase to be randomly packed
      Suitcase randomSuitcase = new Suitcase(5, 5, randomList);

      //generates 100 random packing solutions
      for (int j = 0; j < 100; ++j) {

        //randomly packs the suitcase
        randomSuitcase = Utilities.randomlyPack(randomSuitcase);

        //if the random suitcase packs more area than the optimally packed suitcase,
        //then the optimal method is incorrect and the tester returns false
        if (randomSuitcase.areaPacked() > optimalSuitcase.areaPacked()) {
          return false;
        }
      }
    }

    return true;
  }

  public static void main(String[] args) {
    boolean allPass = true;
    String printFormat = "%-29s %s\n";

    boolean rushedBase = rushedPackingBaseTest();
    allPass &= rushedBase;
    System.out.printf(printFormat, "rushedPackingBaseTest():", rushedBase);

    boolean rushedRecur = rushedPackingRecursiveTest();
    allPass &= rushedRecur;
    System.out.printf(printFormat, "rushedPackingRecursiveTest():", rushedRecur);

    boolean greedyBase = greedyPackingBaseTest();
    allPass &= greedyBase;
    System.out.printf(printFormat, "greedyPackingBaseTest():", greedyBase);

    boolean greedyRecur = greedyPackingRecursiveTest();
    allPass &= greedyRecur;
    System.out.printf(printFormat, "greedyPackingRecursiveTest():", greedyRecur);

    boolean optimalRandom = optimalPackingRandomTest();
    allPass &= optimalRandom;
    System.out.printf(printFormat, "optimalPackingRandomTest():", optimalRandom);

    System.out.printf(printFormat, "All tests:", allPass);
  }
}
