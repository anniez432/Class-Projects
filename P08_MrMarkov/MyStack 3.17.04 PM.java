//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P08 Markov Model MyStack
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
import java.util.Collections;

/**
 * A generic singly-linked stack implementation, which contains some additional methods to
 * facilitate the workings of the Markov Model.
 * 
 */
public class MyStack<T> implements StackADT<T> {

  // data fields
  private LinkedNode<T> top; // reference to LinkedNode currently at top of stack

  // methods

  /**
   * Returns an ArrayList representation of the stack
   * 
   * @return stackList The ArrayList
   * 
   */
  public ArrayList<T> getList() {
    // Create the list to be returned
    ArrayList<T> stackList = new ArrayList<>();

    // pointer
    LinkedNode<T> currNode = top;

    // add each node's values to the list
    while (currNode != null) {
      stackList.add(currNode.getData());
      currNode = currNode.getNext();
    }

    return stackList;

  }

  /**
   * Shuffles the order of the items in the stack.
   * 
   */
  public void shuffle() {
    // create an ArrayList representation of the stack
    ArrayList<T> listToShuffle = getList();

    // shuffle the ArrayList
    Collections.shuffle(listToShuffle);;

    LinkedNode<T> top2 = null;// top of the stack's new order

    // replace stack's ordering
    for (int i = 0; i < listToShuffle.size(); ++i) {
      // the node to add to the stack next
      LinkedNode<T> toAdd = new LinkedNode<>(listToShuffle.get(i));
      toAdd.setNext(top2);
      top2 = toAdd;
    }

    top = top2; // replace the top of the stack
  }

  /**
   * Adds a new value to the stack.
   * 
   * @param value The value being added
   */
  @Override
  public void push(T value) {
    // create a node for the value being added
    LinkedNode<T> toAdd = new LinkedNode<>(value);

    // if the stack is empty, the new node becomes the top of the stack
    if (isEmpty()) {
      this.top = toAdd;
    } else {
      toAdd.setNext(top);
      top = toAdd;
    }
  }


  /**
   * Removes and returns the value at the top of the stack.
   * 
   * @return removedData The removed value
   * 
   */
  @Override
  public T pop() {
    // return null if the stack is empty
    if (isEmpty())
      return null;

    // obtain the data of the node before removing the node
    T removedData = top.getData();
    top = top.getNext();

    return removedData;
  }

  /**
   * Returns the value at the top of the stack.
   * 
   * @return top.getData() The value in the top node.
   */
  @Override
  public T peek() {
    // return null if the stack is empty
    if (isEmpty())
      return null;

    return top.getData();
  }

  /**
   * Determines if the stack is empty.
   * 
   * @return top == null
   */
  @Override
  public boolean isEmpty() {
    return top == null;
  }

}
