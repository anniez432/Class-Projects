//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P08 Markov Model Tester
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
// Online Sources: The P08 Specification and JavaDocs
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;

/**
 * A class that tests the methods in MyStack and MyQueue.
 * 
 */
public class MarkovTester {

  /**
   * Prints the results of the tester methods.
   * 
   * @param args Unused.
   */
  public static void main(String[] args) {
    // print the results of the tests by calling them
    System.out.println(testStackAdd());
    System.out.println(testStackRemove());
    System.out.println(testStackShuffle());
    System.out.println(testPeek());
    System.out.println(testQueueAdd());
    System.out.println(testQueueRemove());
  }

  /**
   * Verify that adding things to the stack correctly increases the stack’s size, and that the
   * ordering of all elements is correct.
   * 
   * @return true if the items were added correctly, false if not
   */
  public static boolean testStackAdd() {

    // create an ArrayList that represents the expected stack
    ArrayList<Integer> expectedStack = new ArrayList<>();

    expectedStack.add(2);
    expectedStack.add(1); // [2, 1]

    // create a stack
    MyStack<Integer> myStack = new MyStack<>();

    // return false if the stack is not empty
    if (!myStack.isEmpty())
      return false;

    myStack.push(1);
    myStack.push(2); // [2, 1]

    // return false if the stack is empty
    if (myStack.isEmpty())
      return false;

    // return false if the stack size is not 2
    if (myStack.getList().size() != 2)
      return false;

    // return false if the stack in ArrayList format does not
    // equal the expected stack
    if (!myStack.getList().equals(expectedStack))
      return false;

    return true;

  }

  /**
   * Verify that removing things from the stack (after adding them) correctly decreases the stack’s
   * size, and that the ordering of all remaining elements is correct. Additionally, verify that a
   * stack that has had elements added to it can become empty again later.
   * 
   * @return true if the items were removed correctly, false if not
   */
  public static boolean testStackRemove() {

    // create an ArrayList representing the expected stack
    ArrayList<Integer> stack = new ArrayList<>();
    stack.add(1); // [1]

    // create a stack
    MyStack<Integer> myStack = new MyStack<>();
    myStack.push(1);
    myStack.push(2);

    // return false if the stack size is not 2
    if (myStack.getList().size() != 2)
      return false;

    myStack.pop(); // [1]

    // return false if the stack is empty
    if (myStack.isEmpty())
      return false;

    // return false if the stack size is not 1
    if (myStack.getList().size() != 1)
      return false;

    // return false if the stack does not match the expected stack
    if (!myStack.getList().equals(stack))
      return false;

    myStack.pop(); // []
    stack.remove(0); // []

    // return false if stack is not empty
    if (!myStack.isEmpty())
      return false;

    // return false if the stack's ArrayList size is not 0
    if (myStack.getList().size() != 0)
      return false;

    // return false if the stack is not equal to what's expected
    if (!myStack.getList().equals(stack))
      return false;

    return true;

  }

  /**
   * verify that calling shuffle() on the stack results in a stack that still contains all of the
   * same elements, but in any order that is different from the original order.
   * 
   * @return true if the items were shuffled correctly, false if not
   */
  public static boolean testStackShuffle() {
    // create an ArrayList representation of a stack
    ArrayList<Integer> stack = new ArrayList<>();
    stack.add(3);
    stack.add(2);
    stack.add(1); // [3, 2, 1]

    // create a stack
    MyStack<Integer> myStack = new MyStack<>();
    myStack.push(1);
    myStack.push(2);
    myStack.push(3); // [3, 2, 1]

    // return false if stack is empty
    if (myStack.isEmpty())
      return false;
    // return false if the stack size is not 3
    if (myStack.getList().size() != 3)
      return false;

    myStack.shuffle();

    // return false if stack is empty
    if (myStack.isEmpty())
      return false;

    // return false if the stack size is not 3
    if (myStack.getList().size() != 3)
      return false;

    // return false if the stack's order is the same as the original order
    if (myStack.getList().equals(stack))
      return false;

    return true;

  }

  /**
   * Verify that calling peek() on both a stack and a queue returns the correct element AND does not
   * make any modifications to the data structure
   * 
   * @return true if peek() works correctly, false if not
   */
  public static boolean testPeek() {
    // creates an expected stack
    ArrayList<Integer> stack = new ArrayList<>();
    stack.add(3);
    stack.add(2);
    stack.add(1); // [3, 2, 1]

    MyStack<Integer> myStack = new MyStack<>();
    myStack.push(1);
    myStack.push(2);
    myStack.push(3); // top of stack [3, 2, 1]

    // return false if stack is empty
    if (myStack.isEmpty())
      return false;

    // return false if top of stack isn't 3
    if (myStack.peek() != 3)
      return false;

    // return false if anything changed from calling peek()
    if (!myStack.getList().equals(stack))
      return false;


    // queue
    ArrayList<Integer> queue = new ArrayList<>();
    queue.add(1);
    queue.add(2);
    queue.add(3); // [1, 2, 3]

    MyQueue<Integer> myQueue = new MyQueue<>();
    myQueue.enqueue(1); // front
    myQueue.enqueue(2);
    myQueue.enqueue(3); // back [1, 2, 3]

    // return false if queue is empty
    if (myQueue.isEmpty())
      return false;

    // return false if front of queue isn't 1
    if (myQueue.peek() != 1)
      return false;

    // return false if anything changed
    if (!myQueue.getList().equals(queue))
      return false;

    return true;
  }


  /**
   * verify that adding things to the queue correctly increases the queue’s size, and that the
   * ordering of all elements is correct
   * 
   * @return true if items were added correctly, false if not
   */

  public static boolean testQueueAdd() {
    // create Integers to be added to the queue
    Integer item1 = 1;
    Integer item2 = 2;
    Integer item3 = 3;

    ArrayList<Integer> expectedQueue = new ArrayList<>();
    expectedQueue.add(item1); // [1]

    // add to empty queue
    MyQueue<Integer> myQueue = new MyQueue<>();
    myQueue.enqueue(item1); // [1]

    // return false if empty
    if (myQueue.isEmpty())
      return false;

    // return false if size != 1;
    if (myQueue.size() != 1)
      return false;

    // return false if the queue's first item isn't item1
    if (myQueue.getList().get(0) != item1)
      return false;

    // return false if incorrect item was added
    if (!myQueue.getList().equals(expectedQueue))
      return false;

    // add to non-empty queue

    expectedQueue.add(item2); // [1, 2]
    myQueue.enqueue(item2); // [1, 2]

    // return false if empty
    if (myQueue.isEmpty())
      return false;

    // return false if size != 1=2;
    if (myQueue.size() != 2)
      return false;

    // return false if first item isn't item1
    if (myQueue.getList().get(0) != item1)
      return false;

    // return false if second item isn't item2
    if (myQueue.getList().get(1) != item2)
      return false;

    // return item if incorrect item was added/ordering is incorrect
    if (!myQueue.getList().equals(expectedQueue))
      return false;

    expectedQueue.add(item3); // [1, 2, 3]
    myQueue.enqueue(item3); // [1, 2, 3]


    // return false if empty
    if (myQueue.isEmpty())
      return false;

    // return false if size != 3;
    if (myQueue.size() != 3)
      return false;

    // return false if first item isn't item1
    if (myQueue.getList().get(0) != item1)
      return false;

    // return false if second item isn't item2
    if (myQueue.getList().get(1) != item2)
      return false;

    // return false if third item isn't item3
    if (myQueue.getList().get(2) != item3)
      return false;

    // return item if incorrect item was added/ordering is incorrect
    if (!myQueue.getList().equals(expectedQueue))
      return false;


    return true;
  }

  /**
   * Verify that removing things from the queue (after adding them) correctly decreases the queue’s
   * size, and that the ordering of all remaining elements is correct. This test should also verify
   * that the custom method maintainSize(int) works as described.
   * 
   * @return true if items are removed correctly, false if not
   */
  public static boolean testQueueRemove() {
    // create expected queue
    ArrayList<Integer> expectedQueue = new ArrayList<>();
    expectedQueue.add(2);
    expectedQueue.add(3); // [2, 3]

    MyQueue<Integer> myQueue = new MyQueue<>();
    myQueue.enqueue(1); // front
    myQueue.enqueue(2);
    myQueue.enqueue(3); // back [1, 2, 3]

    myQueue.dequeue(); // [2, 3]

    // return false if empty
    if (myQueue.isEmpty())
      return false;

    // return false if size !=2;
    if (myQueue.size() != 2)
      return false;

    // return item if incorrect item was added/ordering is incorrect
    if (!myQueue.getList().equals(expectedQueue))
      return false;

    // test maintainSize(int)
    myQueue.maintainSize(1); // should remove 2, leaving [3]
    expectedQueue.remove(0);

    // return false if empty
    if (myQueue.isEmpty())
      return false;

    // return false if size !=1;
    if (myQueue.size() != 1)
      return false;

    // return item if incorrect item was added/ordering is incorrect
    if (!myQueue.getList().equals(expectedQueue))
      return false;

    return true;
  }
}


