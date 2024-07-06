//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P09 Binary Gradebook GradebookIterator File
// Course: CS 300 Spring 2024
//
// Author: Annie Zhao
// Email: azhao37@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: N/A
// Partner Email: N/A
// Partner Lecturer's Name: N/A
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Online Sources: P09 Specification and JavaDocs
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator for traversing the records in a Gradebook in increasing order without skipping any
 * element.
 */
public class GradebookIterator implements Iterator<StudentRecord> {

  // data fields
  // Current StudentRecord reference
  private StudentRecord current;
  // gradebook to iterate over
  private Gradebook gradebook;


  // constructor
  public GradebookIterator(Gradebook gradebook) {
    // initializes current to reference the minimum StudentRecord
    // in the gradebook
    this.gradebook = gradebook;
    this.current = gradebook.getMin();

  }


  /**
   * Determines if the iteraiton has more elements.
   * 
   * @return true if so, false if not.
   */
  @Override
  public boolean hasNext() {
    return current != null;
  }

  /**
   * Determines the next element in the iteration (current StudentRecord from the Gradebook) and
   * advances current pointer to next StudentRecord in the gradebook in increasing order.
   * 
   * @return current The next element in the iteration.
   * @throws NoSuchElementException If the iteration has no more elements (hasNext() is false)
   */
  @Override
  public StudentRecord next() {
    if (current == null) {
      throw new NoSuchElementException("No more elements");
      // set current to successor
      // return current
    } else {
      StudentRecord temp = current;
      current = gradebook.successor(current);
      return temp;
    }


  }


}
