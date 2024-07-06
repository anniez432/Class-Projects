//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P09 Binary Gradebook PassingGradeIterator File
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
import java.util.NoSuchElementException;

/**
 * Iterator for traversing the records in a Gradebook in increasing order, while also skipping over
 * StudentRecords who don't have a passing grade.
 */
public class PassingGradeIterator extends GradebookIterator {

  // data fields
  // reference to current StudentRecord with a passing grade
  private StudentRecord next;

  // passing grade
  private double passingGrade;

  // constructor
  public PassingGradeIterator(Gradebook gradebook) {
    super(gradebook);
    this.passingGrade = gradebook.PASSING_GRADE;
    // advances iterator to first student record
    advanceToNextPassingGrade();
  }

  // methods
  /**
   * Advances iterator to next StudentRecord with a passing grade.
   */
  private void advanceToNextPassingGrade() {
    // while there is a next element
    while (super.hasNext()) {
      StudentRecord nextStudent = super.next();
      // if the next student has a passing grade, set next to that student
      if (nextStudent.getGrade() >= this.passingGrade) {
        this.next = nextStudent;
        return;
      }
    }

    // reset next null if the next student doesn't have a passing grade
    next = null;

  }

  /**
   * Determines if the iteration has more elements
   * 
   * @return true if the iteration has more elements, false if not
   */
  @Override
  public boolean hasNext() {
    return next != null;
  }

  /**
   * Returns the next StudentRecord object with a passing grade in the iteration and advances the
   * iteration.
   * 
   * @return The next StudentRecord with a passing grade
   * @throws NoSuchElementException if there are no more StudentRecord objects with a passing grade
   */
  @Override
  public StudentRecord next() {
    // if there is no next passing student, throw an exception
    if (hasNext() == false)
      throw new NoSuchElementException("No more passing grades");

    // otherwise, return this element and advance to next student
    else {
      StudentRecord temp = next;
      advanceToNextPassingGrade();
      return temp;
    }

  }
}
