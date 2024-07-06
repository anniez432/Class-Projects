//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P08 Markov Model MyQueue
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
 * A class that creates queues for the MarkovModel
 * 
 */
public class MyQueue<T> implements QueueADT<T> {

  // data fields
  private LinkedNode<T> front; // least-recently added
  private LinkedNode<T> back; // most-recently added
  private int size; // number of values currently in queue

  // methods

  /**
   * Ensures the size of the queue is maintained at the desired length.
   * 
   * @param size The desired size of the queue
   * 
   */
  public void maintainSize(int size) {
    // end method if the size is within desired length
    if (this.size < size)
      return;

    // dequeue queue's items until size is as desired
    while (this.size > size) {
      dequeue();
    }
  }

  /**
   * Returns an ArrayList representation of the queue
   * 
   * @return list The ArrayList
   * 
   */
  public ArrayList<T> getList() {
    // create a new ArrayList
    ArrayList<T> list = new ArrayList<>();

    LinkedNode<T> currNode = front;

    // add the nodes' data to the list
    for (int i = 0; i < size; ++i) {
      list.add(currNode.getData());
      currNode = currNode.getNext();
    }

    return list;
  }

  /**
   * Returns the desired string format of the queue
   * 
   * @return toReturn The string output
   * 
   */
  @Override
  public String toString() {
    LinkedNode<T> currNode = front;
    String toReturn = "";

    // add the nodes' data to the string
    while (currNode != null) {
      toReturn += currNode.getData();
      currNode = currNode.getNext();
    }

    return toReturn;
  }


  /**
   * Adds values to the queue
   * 
   * @param value The desired value to be added.
   * 
   */
  @Override
  public void enqueue(T value) {
    LinkedNode<T> toAdd = new LinkedNode<>(value);

    // the new value is added as the front and back values of the
    // queue if the queue is empty
    if (isEmpty()) {
      front = toAdd;
      back = toAdd;
    }
    // otherwise the new value is the back of the queue
    else {
      back.setNext(toAdd);
      back = toAdd;
    }
    size++;
  }

  /**
   * Removes and returns the item at the front of the queue.
   * 
   *
   * @return dequeuedData The data that was removed.
   * 
   */
  @Override
  public T dequeue() {
    // return null if the queue is empty
    if (isEmpty())
      return null;

    T dequeuedData = front.getData();
    // remove value
    front = front.getNext();

    // if the front value is null after removal, queue is empty
    // so back value should be null as well.

    if (front == null)
      back = null;
    size--;

    return dequeuedData;
  }

  /**
   * Returns the value at the front of the queue
   * 
   * @return front.getData()
   * 
   */
  @Override
  public T peek() {
    // return null if the queue is empty
    if (isEmpty())
      return null;
    return front.getData();
  }

  /**
   * Determines if the queue is empty.
   * 
   * @return size == 0
   * 
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the size of the queue
   * 
   * @return this.size
   * 
   */
  @Override
  public int size() {
    return this.size;
  }

}
