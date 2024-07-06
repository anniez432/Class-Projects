
//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P09 Binary Gradebook Gradebook File
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
 * Models a gradebook for a specific course used to store student records.
 */
public class Gradebook implements Iterable<StudentRecord> {

  // data fields
  public final String course; // The name of the course
  public final double PASSING_GRADE; // Min. passing grade for this course
  private BSTNode<StudentRecord> root; // root node of the BST
  private int size; // total number of StudentRecords in the Gradebook
  private boolean passingGradeIteratorEnabled;
  // whether the passing grade iterator is enabled

  // constructor

  public Gradebook(String course, double passingGrade) {
    // throw an exception if the course name is invalid
    if (course == null || course.isBlank()) {
      throw new IllegalArgumentException("Course name is invalid.");
    }

    // throw an exception if the passingGrade is invalid
    if (passingGrade < 0.0 || passingGrade > 100.0) {
      throw new IllegalArgumentException("Grade is invalid.");
    }
    this.course = course;
    this.PASSING_GRADE = passingGrade;
  }

  // methods

  /**
   * Finds a StudentRecord given the email address
   * 
   * @param email Email address of a student
   * @return the StudentRecord associated with the email argument, null if there's no match
   */
  public StudentRecord lookup(String email) {
    // creates a dummy student object to pass into helper method
    StudentRecord dummyRecord = new StudentRecord("fakeName", email, 100.0);
    return lookupHelper(dummyRecord, root);
  }

  /**
   * Adds a new StudentRecord to the GradeBook, updating the root to a BSTNode.
   * 
   * @param record The StudentRecord to be added
   */
  public void addStudent(StudentRecord record) {
    // add record to tree
    root = addStudentHelper(record, root);
    // updates size accordingly
    size++;
  }


  /**
   * Recursive helper method which looks for a given StudentRecord given in the BST rooted at node.
   * 
   * @param target The StudentRecord to search for in the subtree
   * @param node   The root of a subtree of this BST
   * @return StudentRecord that matches, null if there's no match
   */
  protected static StudentRecord lookupHelper(StudentRecord target, BSTNode<StudentRecord> node) {
    // base case:

    // if node is null
    if (node == null)
      return null;

    // if node == target
    if (node.getData().equals(target) || node.getData().compareTo(target) == 0) {
      return node.getData();
    }

    // recursive calls:

    // if target < node, search left
    if (target.compareTo(node.getData()) < 0)
      return lookupHelper(target, node.getLeft());

    // if target > node, search right
    else if (target.compareTo(node.getData()) > 0)
      return lookupHelper(target, node.getRight());

    else
      return null;

  }

  /**
   * The helper method for adding a StudentRecord.
   * 
   * @param record The StudentRecord to be added.
   * @param node   The subtree that the record is added to.
   * @return The new root after adding to the tree.
   * @throws IllegalStateException if the subtree rooted at node has a duplicate record.
   */
  protected static BSTNode<StudentRecord> addStudentHelper(StudentRecord record,
      BSTNode<StudentRecord> node) {

    // base case:
    if (node == null)
      return new BSTNode<StudentRecord>(record);

    // recursive calls:

    // if record's email < node's record email, recursively call helper to
    // check the left child
    if (record.email.compareTo(node.getData().email) < 0)
      node.setLeft(addStudentHelper(record, node.getLeft()));
    // if record's email > node's record email, recursively call helper to
    // check the right child
    else if (record.email.compareTo(node.getData().email) > 0)
      node.setRight(addStudentHelper(record, node.getRight()));
    // if record's email == node's record email, throw an exception
    else if (record.email.compareTo(node.getData().email) == 0)
      throw new IllegalStateException("Duplicate record exists.");


    return node;

  }

  /**
   * Returns the size of the Gradebook
   * 
   * @return this.size The total number of StudentRecord objects stored in the Gradebook.
   */
  public int size() {
    return this.size;
  }

  /**
   * Returns true if this BST has an identical layout (all subtrees equal) to the given tree.
   *
   * @author Ashley Samuelson
   * @see BSTNode#equals(Object)
   * @param node tree to compare this Gradebook to
   * @return true if the given tree looks identical to the root of this Gradebook
   */
  public boolean equalBST(BSTNode<StudentRecord> node) {
    return root == node || (root != null && root.equals(node));
  }


  /**
   * Returns a String representation of the contents of this Gradebook in increasing order.
   * 
   * @return The String representation of the Gradebook
   */
  public String toString() {
    return toStringHelper(root);
  }

  /**
   * Returns the String representation starting with the given node.
   * 
   * @param node The given node.
   * @return The String representation
   */
  protected static String toStringHelper(BSTNode<StudentRecord> node) {

    // base case: node is null, returns empty string;
    if (node == null)
      return "";

    // recursive calls

    // in-order traversal: left subtree, self node, right subtree

    return toStringHelper(node.getLeft()) + node.getData() + "\n" + toStringHelper(node.getRight());

  }

  /**
   * Returns a String representation of the structure of this BST.
   * 
   * @return String representation
   */
  public String prettyString() {

    return prettyStringHelper(root, 0);

  }

  /**
   * Returns a decreasing-order String representation of the structure of this subtree, indented by
   * four spaces for each level of depth in the larger tree.
   *
   * @author Ashley Samuelson
   * @param node  current subtree within the larger tree
   * @param depth depth of the current node within the larger tree
   * @return a String representation of the structure of this subtree
   */
  protected static String prettyStringHelper(BSTNode<StudentRecord> node, int depth) {
    if (node == null) {
      return "";
    }
    String indent = " ".repeat(depth);
    return prettyStringHelper(node.getRight(), depth + 4) + indent + node.getData().name + "\n"
        + prettyStringHelper(node.getLeft(), depth + 4);
  }

  /**
   * Returns the StudentRecord with the lexicographically smallest email in the BST, or null if
   * Gradebook is empty.
   * 
   * @return
   */
  protected StudentRecord getMin() {
    if (!isEmpty()) {
      return getMinHelper(root);
    }
    return null;
  }

  /**
   * Returns smallest StudentRecord in subtree rooted at node.
   * 
   * @param node Root of the subtree to search
   * @return
   */
  protected static StudentRecord getMinHelper(BSTNode<StudentRecord> node) {
    // base case - empty Gradebook or null node
    if (node == null) {
      return null;
    }
    // recursive calls

    while (node.getLeft() != null) {
      return getMinHelper(node.getLeft());
    }

    return node.getData();

  }

  /**
   * Checks whether this Gradebook is empty.
   * 
   * @return true if it is empty, false if not
   */
  public boolean isEmpty() {
    if (this.size == 0 && root == null)
      return true;
    return false;
  }

  /**
   * Deletes a StudentRecord from the Gradebook given their email.
   * 
   * @param email The email that matches the StudentRecord
   */
  public void removeStudent(String email) {
    StudentRecord student = new StudentRecord("student", email, 100.0);
    removeStudentHelper(student, root);
    if (student != null && root != null)
      size--;
  }

  /**
   * Deletes the matching StudentRecord with toDrop if it's found in the tree.
   * 
   * @param toDrop The StudentRecord to drop
   * @param node   The root of the subtree to remove the student from
   * @return The new root of the subtree after removing the StudentRecord
   * @throws NoSuchElementException if there's no matching StudentRecord in the subtree
   */
  protected static BSTNode<StudentRecord> removeStudentHelper(StudentRecord toDrop,
      BSTNode<StudentRecord> node) {
    // base case

    if (node == null)
      throw new NoSuchElementException("There's no matching StudentRecord.");

    // if StudentRecord to be found is less than given node, go through left subtree
    if (toDrop.compareTo(node.getData()) < 0) {
      node.setLeft(removeStudentHelper(toDrop, node.getLeft()));
    }
    // if StudentRecord is greater, go through right subtree
    else if (toDrop.compareTo(node.getData()) > 0) {
      node.setRight(removeStudentHelper(toDrop, node.getRight()));
    }
    // match is found
    else if (toDrop.compareTo(node.getData()) == 0) {
      // no children
      if (node.getLeft() == null && node.getRight() == null) {
        node.setData(null);
        return node;
      }
      // one child - left
      else if (node.getLeft() != null && node.getRight() == null) {
        node.setData(node.getLeft().getData());
        node.setLeft(null);
      }
      // one child - right
      else if (node.getRight() != null && node.getLeft() == null) {

        node.setData(node.getRight().getData());
        node.setRight(null);

      }


      // 2 children
      else {
        // successor of the node to be removed is the most
        // min value in the right subtree
        BSTNode<StudentRecord> successor = new BSTNode<>(getMinHelper(node.getRight()));
        // re-set the node's data
        node.setData(successor.getData());
        // remove the successor
        removeStudentHelper(successor.getData(), node.getRight());

      }
    }

    return node;

  }

  /**
   * Returns the successor of a target StudentRecord (the smallest value in the BST that's larger
   * than the target)
   * 
   * @param target The StudentRecord to find the successor of
   * @return The successor, or null if there's no successor.
   */
  protected StudentRecord successor(StudentRecord target) {
    return successorHelper(target, root);
  }

  /**
   * Returns the successor of a target StudentRecord (the smallest value in the subtree that's
   * larger than the target) within the subtree.
   * 
   * @param target The StudentRecord to find the successor of
   * @param node   Subtree to search for a successor in
   * @return THe successor, or null if none exists
   */
  protected static StudentRecord successorHelper(StudentRecord target,
      BSTNode<StudentRecord> node) {
    // null node returns null
    if (node == null)
      return null;
    // target is greater than or equal to root node - successor is in right subtree
    else if (target.compareTo(node.getData()) >= 0 && node.getRight() != null) {
      // successor is smallest value in right subtree
      return successorHelper(target, node.getRight());
    }

    // target data is less than root
    else if (target.compareTo(node.getData()) < 0) {

      // a. at least one node in left subtree that is larger than target
      if (node.getLeft() != null) {
        StudentRecord successor = successorHelper(target, node.getLeft());
        if (successor != null) {
          return successor;
        } else {
          if (target.compareTo(node.getData()) < 0) {
            return node.getData();
          }
        }
      }
      // b. no nodes larger than target in left subtree
      else {
        return node.getData();
      }
    }

    // of none of those conditions are met, there is no successor
    return null;
  }

  /**
   * Searches for StudentRecord associated with provided email and checks whether it has a passing
   * grade for the course.
   * 
   * @param email The email of the StudentRecord to find
   * @return String indicating whever the student has a passing or failing grade
   */
  public String checkPassingCourse(String email) {
    // if there is no match with the email, return no match found
    if (lookup(email) == null)
      return "No match found.";

    // matching record is found
    else {
      StudentRecord matchingRecord = lookup(email);
      if (matchingRecord.getGrade() >= this.PASSING_GRADE)
        return matchingRecord.toString() + ": PASS";
      else
        return matchingRecord.toString() + ": FAIL";
    }
  }

  /**
   * Enables the passing grade iterator.
   */
  public void enablePassingGradeIterator() {
    this.passingGradeIteratorEnabled = true;
  }

  /**
   * Disables the passing grade iterator.
   */
  public void disablePassingGradeIterator() {
    this.passingGradeIteratorEnabled = false;
  }

  /**
   * Returns an iterator over the StudentRecords in increasing order.
   * 
   * @return PassingGradeIterator if passingGradeIterator is enabled, GradebookIterator if not.
   */
  @Override
  public Iterator<StudentRecord> iterator() {
    if (this.passingGradeIteratorEnabled) {
      return new PassingGradeIterator(this);
    } else {
      return new GradebookIterator(this);
    }
  }

}
