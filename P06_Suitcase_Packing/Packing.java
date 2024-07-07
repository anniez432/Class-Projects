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
// Persons:    Rob K., CS300 TA assisted us with the optimalPacking method
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;

/**
 * Class used for packing a 2D suitcase with items using various strategies.
 */
public class Packing {

  /**
   * Helper method for the rushedPacking method.
   * Tries to pack each item in the items list in-order.
   * If an item can fit, it must be packed. Otherwise, it should be skipped.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase object
   * @param index The integer value of the item that the method is
   *              currently trying to pack
   * @return a Suitcase representing the outcome of a strategy in which
   * the items
   * were attempted to be packed in-order.
   */
  private static Suitcase rushedPackingHelper(Suitcase suitcase, int index){
    //BASE CASE: if the index greater than the amount of items in
    // unpackedItems, return suitcase

    if(index >= suitcase.getUnpackedItems().size()){
      return suitcase;
    }

    //keep track of which item is being checked
    Item currentItem = suitcase.getUnpackedItems().get(index);

    if ( suitcase.canPackItem (currentItem) ) {

      //pack the item
      suitcase = suitcase.packItem(currentItem);
      return rushedPackingHelper(suitcase, index);

    } else {
      //call this method again for the next index in suitcase
      return rushedPackingHelper(suitcase, index + 1);
    }

  }


  /**
   * Tries to pack each item in the items list in-order.
   * If an item can fit, it must be packed. Otherwise, it should
   * be skipped.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase object
   * @return a Suitcase representing the outcome of a strategy in which
   * the items
   * were attempted to be packed in-order.
   */
  public static Suitcase rushedPacking (Suitcase suitcase) {

    //calls the private helper method
    return rushedPackingHelper(suitcase, 0);

}


  /**
   * Packs items by greedily packing the largest item which
   * can currently be packed.
   * Must be a recursive method.
   *
   * @param suitcase current Suitcase object
   * @return a Suitcase representing the outcome of a greedy
   * strategy in which at each
   * point the largest item that can fit is packed.
   */
  public static Suitcase greedyPacking(Suitcase suitcase){
    //BASE CASE: if there are no items left to be packed,
    // return the suitcase
    if (suitcase.numItemsUnpacked()==0){
      return suitcase;
    }
      //variables to keep track of the largest item and its area
      Item largestItem = null;
      int largestItemArea = 0;
     //loops through every item in unpackedItems, assigning
    // largestItem with the largest unpacked
    //item that CAN be packed. skips over items that can't be packed
    for (Item items : suitcase.getUnpackedItems()) {
      int itemArea = items.height * items.width;
      //if the largest item can be packed, assign it to the variable
      if (largestItemArea < itemArea) {
        if(suitcase.canPackItem(items)) {
          largestItem = items;
          largestItemArea = itemArea;
        }
      }
    }
    //pack the largest item that can be packed. recursively call
    // the method to pack the next largest
    //item that can be packed.
    if ( largestItem != null && suitcase.canPackItem(largestItem)) {
      suitcase = suitcase.packItem(largestItem);
      return greedyPacking(suitcase); //recursive call
    }
    //if there are item left but can't be packed, return the suitcase.
    return suitcase;
  }


  /**
   * Finds the optimal packing of items by trying all packing orders.
   * Must be a recursive method.
   * CITE: TA Rob assisted us through the logic of this method.
   *
   * @param suitcase current Suitcase
   * @return a Suitcase representing the optimal outcome.
   */
  public static Suitcase optimalPacking(Suitcase suitcase) {


    //BASE CASE: there are no items left to be packed
    if (suitcase.numItemsUnpacked() == 0){
      return suitcase;
    }

    //sets our best scenario to the suitcase we have
    Suitcase bestSuitcase = suitcase;

    //iteratse through all the unpacked items
    for (Item item : suitcase.getUnpackedItems()) {
      //if the item can be packed, recursively call the method again
      // to create new permutations
      if(suitcase.canPackItem(item)) {
        // creates a temporary suitcase by packing the current permutation
        // item into it with a recursive call. temp will represent
        // the best suitcase permutation found after considering
        // the current item
        Suitcase temp = optimalPacking(suitcase.packItem(item));
        //once we reach this statement, set the best suitcase with the
        // next one if it's better
        if (temp.areaPacked() > bestSuitcase.areaPacked()){
          bestSuitcase = temp; }
      }
    }
    // after the recursive call returns, the method continues its iteration
    // over the remaining unpacked items
    return bestSuitcase;
  }

}

