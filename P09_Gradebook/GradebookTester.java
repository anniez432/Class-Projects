//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P09 Binary Gradebook GradebookTester File
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
 * The class with the tester methods for the Gradebook class.
 */
public class GradebookTester {

  /*
   * public static void main(String[] arg) { System.out.println(constructorTester());
   * 
   * System.out.println(toStringTester());
   * 
   * System.out.println(isEmptySizeAddTester());
   * 
   * System.out.println(lookupTester());
   * 
   * System.out.println(toStringTester());
   * 
   * System.out.println(prettyStringTester());
   * 
   * System.out.println(getMinTester());
   * 
   * System.out.println(removeStudentTester());
   * 
   * System.out.println(successorTester());
   * 
   * System.out.println(iteratorTester());
   * 
   * System.out.println(passingIteratorTester());
   * 
   * 
   * }
   */

  /**
   * Determines if the constructor creates a Gradebook as intended.
   * 
   * @return true if it works as intended, false if not.
   */
  public static boolean constructorTester() {
    // empty BST
    Gradebook gradebook = new Gradebook("Math", 90.0);
    BSTNode<StudentRecord> expectedGradebook = new BSTNode<>(null, null, null);

    // check size
    if (gradebook.size() != 0)
      return false;

    // check course name
    if (!gradebook.course.equals("Math"))
      return false;

    // check passing grade
    if (gradebook.PASSING_GRADE != 90.0)
      return false;

    // test exceptions

    // invalid course name
    try {
      Gradebook invalidCourse = new Gradebook(null, 90.0);
      return false;
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
      System.out.println(e.getMessage());
    }

    // blank course name
    try {
      Gradebook blankCourse = new Gradebook("", 90.0);
      return false;
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
      System.out.println(e.getMessage());
    }

    // too small grade
    try {
      Gradebook invalidGrade = new Gradebook("math", -1.0);
      return false;
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
      System.out.println(e.getMessage());
    }
    // too large grade
    try {
      Gradebook invalidGrade = new Gradebook("math", 100.1);
      return false;
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
      System.out.println(e.getMessage());
    }

    // matching element found at the root
    StudentRecord student1 = new StudentRecord("John", "johndoe@gmail.com", 90.0);
    gradebook.addStudent(student1);
    expectedGradebook.setData(student1);

    if (gradebook.size() != 1)
      return false;
    if (gradebook.lookup("johndoe@gmail.com") == null)
      return false;


    if (!gradebook.equalBST(expectedGradebook))
      return false;


    return true;
  }

  /**
   * Determines if the addStudent() method correctly adds a StudentRecord to the Gradebook.
   * 
   * @return true if it works as intended, false if not.
   */
  public static boolean isEmptySizeAddTester() {
    // empty gradebook
    Gradebook gradebook = new Gradebook("math", 90.0);

    // check if gradebook is empty
    if (gradebook.size() != 0)
      return false;

    StudentRecord student1 = new StudentRecord("John", "john@gmail.com", 80.0);
    BSTNode<StudentRecord> student1Node = new BSTNode<>(student1);

    // add student to empty gradebook
    gradebook.addStudent(student1);

    // return false if size isn't 1
    if (gradebook.size() != 1)
      return false;

    // return false if gradebook doesn't contain student1
    if (gradebook.lookup("john@gmail.com") != student1)
      return false;

    // try to add same student to gradebook, should throw an exception
    try {
      gradebook.addStudent(student1);
      return false;
    } catch (IllegalStateException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }

    return true;
  }

  /**
   * Determines if lookup() correctly returns the correct StudentRecord or null if not.
   * 
   * @return true if it works as intended, false if not.
   */
  public static boolean lookupTester() {
    // empty gradebook
    Gradebook gradebook = new Gradebook("math", 90.0);

    // check if gradebook is empty
    if (gradebook.size() != 0)
      return false;

    // if calling lookup on an empty gradebook
    // doesn't return null, return false
    if (gradebook.lookup("john@gmail.com") != null)
      return false;

    // matching element found at root

    // add one student
    StudentRecord student1 = new StudentRecord("John", "john@gmail.com", 80.0);
    BSTNode<StudentRecord> student1Node = new BSTNode<>(student1);
    gradebook.addStudent(student1);

    // check size
    if (gradebook.size() != 1)
      return false;

    // return false if gradebook doesn't contain student1
    if (gradebook.lookup("john@gmail.com") != student1)
      return false;

    // return false if incorrect email doesn't return null
    if (gradebook.lookup("susie@gmail.com") != null)
      return false;

    if (!gradebook.equalBST(student1Node))
      return false;

    // no matching element found


    // normal cases

    // matching in left - amy is placed on the left side
    StudentRecord student2 = new StudentRecord("Amy", "amy@gmail.com", 70.0);
    BSTNode<StudentRecord> student2Node = new BSTNode<>(student2);
    gradebook.addStudent(student2);

    // check size
    if (gradebook.size() != 2)
      return false;

    // return false if lookup doesn't return right record
    if (gradebook.lookup("john@gmail.com") != student1)
      return false;
    if (gradebook.lookup("amy@gmail.com") != student2)
      return false;

    // return false if incorrect email doesn't return null
    if (gradebook.lookup("susie@gmail.com") != null)
      return false;

    // matching in right - xander is placed on right side
    StudentRecord student3 = new StudentRecord("Xander", "xander@gmail.com", 70.0);
    BSTNode<StudentRecord> student3Node = new BSTNode<>(student3);
    gradebook.addStudent(student3);

    // check size
    if (gradebook.size() != 3)
      return false;

    // return false if lookup doesn't return right record
    if (gradebook.lookup("john@gmail.com") != student1)
      return false;
    if (gradebook.lookup("amy@gmail.com") != student2)
      return false;
    if (gradebook.lookup("xander@gmail.com") != student3)
      return false;

    // return false if incorrect email doesn't return null
    if (gradebook.lookup("susie@gmail.com") != null)
      return false;

    return true;
  }

  /**
   * Determines if Gradebook's toString() function returns the correct String representation of the
   * Gradebook.
   * 
   * @return true if it works as intended, false if not
   */
  public static boolean toStringTester() {
    Gradebook gradebook = new Gradebook("math", 100.0);

    // empty gradebook should return empty string
    if (!gradebook.toString().equals(""))
      return false;

    // add 3 students to the Gradebook
    StudentRecord student1 = new StudentRecord("John", "john@gmail.com", 80.0);
    BSTNode<StudentRecord> student1Node = new BSTNode<>(student1);
    gradebook.addStudent(student1);

    StudentRecord student2 = new StudentRecord("Amy", "amy@gmail.com", 70.0);
    BSTNode<StudentRecord> student2Node = new BSTNode<>(student2);
    gradebook.addStudent(student2);

    StudentRecord student3 = new StudentRecord("Xander", "xander@gmail.com", 70.0);
    BSTNode<StudentRecord> student3Node = new BSTNode<>(student3);
    gradebook.addStudent(student3);

    // check the size
    if (gradebook.size() != 3)
      return false;

    String expectedString = "Amy (amy@gmail.com): 70.0\nJohn (john@gmail.com): "
        + "80.0\nXander (xander@gmail.com): 70.0\n";

    BSTNode<StudentRecord> expectedGradebook = new BSTNode<>(student1, student2Node, student3Node);
    // if gradebook's toString() doesn't return the
    // expectedString, return false

    if (!gradebook.toString().equals(expectedString))
      return false;

    // if gradebook isn't equal to the proper
    // BST representation, return false

    if (!gradebook.equalBST(expectedGradebook))
      return false;


    return true;
  }


  /**
   * Determines if prettyString() creates a correct String representation of the gradebook.
   * 
   * @return true if it works as intended, false if not.
   */
  public static boolean prettyStringTester() {
    Gradebook gradebook = new Gradebook("math", 100.0);

    String expectedString = "";

    if (gradebook.size() != 0)
      return false;

    if (!gradebook.prettyString().equals(expectedString))
      return false;

    // add 3 students to the Gradebook
    StudentRecord john = new StudentRecord("John", "john@gmail.com", 80.0);
    BSTNode<StudentRecord> johnNode = new BSTNode<>(john);
    gradebook.addStudent(john);

    StudentRecord amy = new StudentRecord("Amy", "amy@gmail.com", 70.0);
    BSTNode<StudentRecord> amyNode = new BSTNode<>(amy);
    gradebook.addStudent(amy);

    StudentRecord xander = new StudentRecord("Xander", "xander@gmail.com", 70.0);
    BSTNode<StudentRecord> xanderNode = new BSTNode<>(xander);
    gradebook.addStudent(xander);

    StudentRecord zoe = new StudentRecord("Zoe", "zoe@gmail.com", 100.0);
    BSTNode<StudentRecord> zoeNode = new BSTNode<>(zoe);
    gradebook.addStudent(zoe);

    StudentRecord billy = new StudentRecord("Billy", "billy@gmail.com", 80.0);
    BSTNode<StudentRecord> billyNode = new BSTNode<>(billy);
    gradebook.addStudent(billy);

    StudentRecord allie = new StudentRecord("Allie", "allie@gmail.com", 80.0);
    BSTNode<StudentRecord> allieNode = new BSTNode<>(allie);
    gradebook.addStudent(allie);


    // return false if the gradebook is not the correct size
    if (gradebook.size() != 6)
      return false;

    expectedString = "        Zoe\n    " + "Xander\nJohn\n        Billy\n    Amy\n        Allie\n";

    BSTNode<StudentRecord> expectedGradebook =
        new BSTNode<StudentRecord>(john, new BSTNode<StudentRecord>(amy, allieNode, billyNode),
            new BSTNode<StudentRecord>(xander, null, zoeNode));

    // return false if the gradebook BST is structured as expected
    if (!gradebook.equalBST(expectedGradebook))
      return false;


    if (!gradebook.prettyString().equals(expectedString))
      return false;

    return true;
  }

  /**
   * Determines if the getMin() method correctly returns the minimum StudentRecord in the Gradebook.
   * 
   * @return true if it works as intended, false if not.
   */
  public static boolean getMinTester() {
    // check empty BST
    Gradebook gradebook = new Gradebook("math", 100.0);

    if (gradebook.size() != 0)
      return false;

    BSTNode<StudentRecord> emptyBST = new BSTNode<>(null, null, null);


    // return false if empty gradebook doesn't return null min
    if (gradebook.getMin() != null)
      return false;

    // check with one item
    StudentRecord john = new StudentRecord("John", "john@gmail.com", 80.0);
    BSTNode<StudentRecord> johnNode = new BSTNode<>(john);
    gradebook.addStudent(john);

    if (gradebook.size() != 1)
      return false;

    BSTNode<StudentRecord> oneItemBST = new BSTNode<>(john, null, null);

    if (!gradebook.equalBST(oneItemBST))
      return false;

    // return false if empty gradebook doesn't return correct min
    if (gradebook.getMin() != john)
      return false;

    // check with large BST
    StudentRecord amy = new StudentRecord("Amy", "amy@gmail.com", 70.0);
    BSTNode<StudentRecord> amyNode = new BSTNode<>(amy);
    gradebook.addStudent(amy);

    StudentRecord xander = new StudentRecord("Xander", "xander@gmail.com", 70.0);
    BSTNode<StudentRecord> xanderNode = new BSTNode<>(xander);
    gradebook.addStudent(xander);

    StudentRecord zoe = new StudentRecord("Zoe", "zoe@gmail.com", 100.0);
    BSTNode<StudentRecord> zoeNode = new BSTNode<>(zoe);
    gradebook.addStudent(zoe);

    StudentRecord billy = new StudentRecord("Billy", "billy@gmail.com", 80.0);
    BSTNode<StudentRecord> billyNode = new BSTNode<>(billy);
    gradebook.addStudent(billy);

    StudentRecord allie = new StudentRecord("Allie", "allie@gmail.com", 80.0);
    BSTNode<StudentRecord> allieNode = new BSTNode<>(allie);
    gradebook.addStudent(allie);


    // return false if the gradebook is not the correct size
    if (gradebook.size() != 6)
      return false;

    BSTNode<StudentRecord> largeBST =
        new BSTNode<StudentRecord>(john, new BSTNode<StudentRecord>(amy, allieNode, billyNode),
            new BSTNode<StudentRecord>(xander, null, zoeNode));

    if (!gradebook.equalBST(largeBST))
      return false;

    // return false if empty gradebook doesn't return correct min
    if (gradebook.getMin() != allie)
      return false;

    return true;
  }

  /**
   * Determines if the removeStudent() method correctly removes the provided StudentRecord
   * 
   * @return true if it works as intended, false if not.
   */
  public static boolean removeStudentTester() {
    // on an empty Gradebook
    Gradebook gradebook = new Gradebook("math", 100.0);

    // check size
    if (gradebook.size() != 0)
      return false;

    // should throw an exception
    try {
      gradebook.removeStudent("john@gmail.com");
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }

    // on a one-item Gradebook
    StudentRecord john = new StudentRecord("John", "john@gmail.com", 80.0);
    gradebook.addStudent(john);

    // check size
    if (gradebook.size() != 1)
      return false;

    // try to remove with incorrect email
    try {
      gradebook.removeStudent("amy@gmail.com");
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }

    // remove with correct email
    gradebook.removeStudent("john@gmail.com");

    // check size
    if (gradebook.size() != 0)
      return false;


    // BST with 1 child - left
    gradebook = new Gradebook("math", 100.0);
    john = new StudentRecord("John", "john@gmail.com", 80.0);
    gradebook.addStudent(john);

    StudentRecord amy = new StudentRecord("Amy", "amy@gmail.com", 70.0);
    BSTNode<StudentRecord> amyNode = new BSTNode<>(amy);
    gradebook.addStudent(amy);

    // check size
    if (gradebook.size() != 2)
      return false;

    // try to remove with incorrect email
    try {
      gradebook.removeStudent("annie@gmail.com");
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }

    // remove root of 1 child

    gradebook.removeStudent("john@gmail.com");

    // check only amy is left
    BSTNode<StudentRecord> expected = new BSTNode<>(amy, null, null);

    if (!gradebook.equalBST(expected))
      return false;

    // remove left child

    gradebook.addStudent(john);
    gradebook.removeStudent("amy@gmail.com");

    // check size
    if (gradebook.size() != 1)
      return false;

    expected = new BSTNode<>(john, null, null);
    // check only john is left
    if (!gradebook.equalBST(expected))
      return false;

    // BST with 1 child - right
    StudentRecord xander = new StudentRecord("Xander", "xander@gmail.com", 70.0);
    BSTNode<StudentRecord> xanderNode = new BSTNode<>(xander);
    gradebook.addStudent(xander);

    // check size
    if (gradebook.size() != 2)
      return false;

    expected = new BSTNode<>(john, null, xanderNode);
    if (!gradebook.equalBST(expected))
      return false;

    // remove right child - xander
    gradebook.removeStudent("xander@gmail.com");

    if (gradebook.size() != 1)
      return false;

    // check only john is left
    expected = new BSTNode<>(john, null, null);

    gradebook = new Gradebook("math", 100.0);
    gradebook.addStudent(john);
    gradebook.addStudent(xander);

    // remove root of 1 child
    gradebook.removeStudent("john@gmail.com");

    expected = new BSTNode<>(xander, null, null);

    // check that only xander is left
    if (!gradebook.equalBST(expected))
      return false;

    // check size
    if (gradebook.size() != 1)
      return false;

    // clear tree
    gradebook.removeStudent("xander@gmail.com");

    // large BST with root with 2 children
    StudentRecord zoe = new StudentRecord("Zoe", "zoe@gmail.com", 100.0);
    BSTNode<StudentRecord> zoeNode = new BSTNode<>(zoe);


    StudentRecord billy = new StudentRecord("Billy", "billy@gmail.com", 80.0);
    BSTNode<StudentRecord> billyNode = new BSTNode<>(billy);


    StudentRecord allie = new StudentRecord("Allie", "allie@gmail.com", 80.0);
    BSTNode<StudentRecord> allieNode = new BSTNode<>(allie);


    gradebook = new Gradebook("math", 100.0);
    gradebook.addStudent(john);
    gradebook.addStudent(amy);
    gradebook.addStudent(xander);
    gradebook.addStudent(zoe);
    gradebook.addStudent(billy);
    gradebook.addStudent(allie);

    // remove john
    gradebook.removeStudent("john@gmail.com");

    // check size
    if (gradebook.size() != 5)
      return false;

    // check that john was replaced by xander & xander's original place is removed
    expected =
        new BSTNode<>(xander, new BSTNode<StudentRecord>(amy, allieNode, billyNode), zoeNode);


    if (!gradebook.equalBST(expected))
      return false;


    return true;
  }


  /**
   * Determines if the successor() method correctly returns the correct StudentRecord.
   * 
   * @return true if it works as intended, false if not.
   */
  public static boolean successorTester() {
    // empty gradebook should return null
    Gradebook gradebook = new Gradebook("math", 100.0);

    StudentRecord john = new StudentRecord("John", "john@gmail.com", 80.0);
    // check size
    if (gradebook.size() != 0)
      return false;

    // check return value
    if (gradebook.successor(john) != null)
      return false;

    // then add john
    // when searching for successor, should return
    // null as there is no successor
    gradebook.addStudent(john);

    // check size
    if (gradebook.size() != 1)
      return false;

    // check return value
    if (gradebook.successor(john) != null)
      return false;

    // add amy

    StudentRecord amy = new StudentRecord("Amy", "amy@gmail.com", 70.0);
    gradebook.addStudent(amy);

    // check size
    if (gradebook.size() != 2)
      return false;

    // when searching for john's successor, should return null
    if (gradebook.successor(john) != null)
      return false;

    // when searching for amy's successor, should return john
    if (gradebook.successor(amy) != john)
      return false;


    // add xander
    StudentRecord xander = new StudentRecord("Xander", "xander@gmail.com", 70.0);
    gradebook.addStudent(xander);

    // check size
    if (gradebook.size() != 3)
      return false;

    // when searching for john's successor, should return xander
    if (gradebook.successor(john) != xander)
      return false;

    // when searching for amy's successor, should return john
    if (gradebook.successor(amy) != john)
      return false;

    // xander's successor - > null
    if (gradebook.successor(xander) != null)
      return false;


    // add zoe
    StudentRecord zoe = new StudentRecord("Zoe", "zoe@gmail.com", 100.0);
    gradebook.addStudent(zoe);
    // check size
    if (gradebook.size() != 4)
      return false;

    // john's successor -> xander
    if (gradebook.successor(john) != xander)
      return false;

    // xander's successor -> zoe
    if (gradebook.successor(xander) != zoe)
      return false;

    // zoe's successor -> null
    if (gradebook.successor(zoe) != null)
      return false;

    // amy's successor -> john
    if (gradebook.successor(amy) != john)
      return false;

    // add billy
    StudentRecord billy = new StudentRecord("Billy", "billy@gmail.com", 80.0);
    gradebook.addStudent(billy);
    // check size
    if (gradebook.size() != 5)
      return false;

    // amy's successor -> billy
    if (gradebook.successor(amy) != billy)
      return false;
    // john's successor -> xander
    if (gradebook.successor(john) != xander)
      return false;
    // xander's successor -> zoe
    if (gradebook.successor(xander) != zoe)
      return false;
    // zoe's successor -> null
    if (gradebook.successor(zoe) != null)
      return false;

    // billy's successor -> john
    if (gradebook.successor(billy) != john)
      return false;

    // add allie, amy's successor should still be billy
    StudentRecord allie = new StudentRecord("Allie", "allie@gmail.com", 80.0);
    gradebook.addStudent(allie);
    // amy's successor -> billy
    if (gradebook.successor(amy) != billy)
      return false;
    // john's successor -> xander
    if (gradebook.successor(john) != xander)
      return false;
    // xander's successor -> zoe
    if (gradebook.successor(xander) != zoe)
      return false;
    // zoe's successor -> null
    if (gradebook.successor(zoe) != null)
      return false;
    // billy's successor -> john
    if (gradebook.successor(billy) != john)
      return false;
    // allie's successor - > amy
    if (gradebook.successor(allie) != amy)
      return false;


    return true;
  }

  /**
   * Determines if the iterator() method in Gradebook correctly returns a GradebookIterator that
   * works correctly.
   * 
   * @return true if it works as intended, false if not.
   */
  public static boolean iteratorTester() {
    // empty gradebook with passingGradeIterator disabled should return
    // a Gradebook Iterator
    Gradebook gradebook = new Gradebook("math", 100.0);

    // check size
    if (gradebook.size() != 0)
      return false;

    // disable passingGrade
    gradebook.disablePassingGradeIterator();
    // create iterator
    Iterator<StudentRecord> gradebookIterator = gradebook.iterator();
    GradebookIterator expected = new GradebookIterator(gradebook);

    // check if their has nexts are the same
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    // next() should throw an exception
    try {
      gradebookIterator.next();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }


    // one item gradebook
    StudentRecord john = new StudentRecord("John", "john@gmail.com", 80.0);
    gradebook.addStudent(john);

    // check size
    if (gradebook.size() != 1)
      return false;
    // re-call iterator
    gradebookIterator = gradebook.iterator();
    expected = new GradebookIterator(gradebook);

    // check if their has nexts are the same
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    // next() should return current(john)
    if (gradebookIterator.next() != john)
      return false;


    // two-item gradebook
    StudentRecord amy = new StudentRecord("Amy", "amy@gmail.com", 70.0);
    gradebook.addStudent(amy);
    // check size
    if (gradebook.size() != 2)
      return false;
    // re-call iterator
    gradebookIterator = gradebook.iterator();
    expected = new GradebookIterator(gradebook);
    // check if their has nexts are the same
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    // next() should return current(amy)
    if (gradebookIterator.next() != amy)
      return false;

    System.out.println(gradebookIterator.hasNext());

    // next() again should return john
    if (gradebookIterator.next() != john)
      return false;


    // large gradebook
    StudentRecord xander = new StudentRecord("Xander", "xander@gmail.com", 70.0);
    gradebook.addStudent(xander);

    StudentRecord zoe = new StudentRecord("Zoe", "zoe@gmail.com", 100.0);
    gradebook.addStudent(zoe);


    StudentRecord billy = new StudentRecord("Billy", "billy@gmail.com", 80.0);
    gradebook.addStudent(billy);


    StudentRecord allie = new StudentRecord("Allie", "allie@gmail.com", 80.0);
    gradebook.addStudent(allie);

    // check size
    if (gradebook.size() != 6)
      return false;
    // re-call iterator
    gradebookIterator = gradebook.iterator();
    expected = new GradebookIterator(gradebook);

    // check if their has nexts are the same
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    // next() should return allie
    if (gradebookIterator.next() != allie)
      return false;

    // next() again should return amy
    if (gradebookIterator.next() != amy)
      return false;


    return true;
  }

  /**
   * Determines if iterator() correctly creates a PassingGradeIterator.
   * 
   * @return true if it works as intended, false if not
   */
  public static boolean passingIteratorTester() {
    // empty Gradebook with enabled passingIterator should return a
    // passingIterator
    Gradebook gradebook = new Gradebook("math", 60.0);

    // check size
    if (gradebook.size() != 0)
      return false;

    // enable passingGrade
    gradebook.enablePassingGradeIterator();;
    // create iterator
    Iterator<StudentRecord> gradebookIterator = gradebook.iterator();
    PassingGradeIterator expected = new PassingGradeIterator(gradebook);

    // check if their has nexts are the same
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    // next() should throw an exception
    try {
      gradebookIterator.next();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }

    // one-item gradebook with passing student
    gradebook = new Gradebook("math", 60.0);

    StudentRecord john = new StudentRecord("John", "john@gmail.com", 80.0);
    gradebook.addStudent(john);

    gradebook.enablePassingGradeIterator();
    gradebookIterator = gradebook.iterator();
    expected = new PassingGradeIterator(gradebook);


    // check if their has nexts are the same
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;


    if (gradebookIterator.hasNext() != true)
      return false;

    // next() should return john
    if (gradebookIterator.next() != john)
      return false;

    // one item gradebook without passing student

    gradebook = new Gradebook("math", 90.0);
    gradebook.addStudent(john);

    gradebook.enablePassingGradeIterator();
    gradebookIterator = gradebook.iterator();
    expected = new PassingGradeIterator(gradebook);

    // check if their has nexts are the same - should be false
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    if (gradebookIterator.hasNext() != false)
      return false;

    // next() should throw an exception
    try {
      gradebookIterator.next();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }

    // two-item gradebook with no passing students
    StudentRecord amy = new StudentRecord("Amy", "amy@gmail.com", 70.0);
    gradebook.addStudent(amy);

    gradebook.enablePassingGradeIterator();
    gradebookIterator = gradebook.iterator();
    expected = new PassingGradeIterator(gradebook);

    // check if their has nexts are the same - should be false
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    if (gradebookIterator.hasNext() != false)
      return false;

    // next() should throw an exception
    try {
      gradebookIterator.next();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }


    // two-item with both passing stuents
    gradebook = new Gradebook("math", 50.0);
    gradebook.addStudent(john);
    gradebook.addStudent(amy);

    gradebook.enablePassingGradeIterator();
    gradebookIterator = gradebook.iterator();
    expected = new PassingGradeIterator(gradebook);

    // check if their has nexts are the same - should be true
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    if (gradebookIterator.hasNext() != true)
      return false;

    // next() should return amy
    if (gradebookIterator.next() != amy)
      return false;

    // check if their has nexts are the same - should be true
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    if (gradebookIterator.hasNext() != true)
      return false;

    // next() should return john
    if (gradebookIterator.next() != john)
      return false;


    // two-item with one passing student
    gradebook = new Gradebook("math", 80.0);
    gradebook.addStudent(john);
    gradebook.addStudent(amy);

    gradebook.enablePassingGradeIterator();
    gradebookIterator = gradebook.iterator();
    expected = new PassingGradeIterator(gradebook);

    // check if their has nexts are the same - should be true
    if (gradebookIterator.hasNext() != expected.hasNext())
      return false;

    // next() should return john
    if (gradebookIterator.next() != john)
      return false;

    // next() again should throw an exception
    try {
      gradebookIterator.next();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }

    return true;
  }

}
