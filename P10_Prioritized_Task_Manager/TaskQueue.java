//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P10 TaskQueue
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
// Online: P10 Specification
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Arrays;
import java.util.NoSuchElementException;

public class TaskQueue {

  // data fields

  // oversized array that holds all of tasks in the heap
  private Task[] heapData;

  // number of items in TaskQueue
  private int size;

  // criteria used to determine how to prioritize Tasks in the queue
  private CompareCriteria priorityCriteria;


  // constructor
  public TaskQueue(int capacity, CompareCriteria priorityCriteria) {
    if (capacity <= 0)
      throw new IllegalArgumentException("Capacity is non-positive.");

    this.heapData = new Task[capacity];
    this.size = 0;
    this.priorityCriteria = priorityCriteria;
  }

  // methods

  /**
   * Gets the criteria used to prioritize tasks in this TaskQueue.
   * 
   * @return priorityCriteria The prioritization criteria of this TaskQueue.
   */
  public CompareCriteria getPriorityCriteria() {
    return priorityCriteria;
  }

  /**
   * Determines if a TaskQueue is empty
   * 
   * @return true if it is empty, false if not
   */
  public boolean isEmpty() {
    // if size == 0, return true is empty
    // if size != 0, return false is not empty
    return size == 0;
  }

  /**
   * Determines the size of TaskQueue.
   * 
   * @return this.size The number of Tasks in the TaskQueue
   */
  public int size() {
    return this.size;
  }

  /**
   * Gets Task in TaskQueue with the highest priority without removing it. Task with the highest
   * priority may differ based on current priority criteria.
   * 
   * @return
   * @throws NoSuchElementException if the TaskQueue is empty.
   */
  public Task peekBest() {
    if (this.isEmpty())
      throw new NoSuchElementException("TaskQueue is empty.");

    // highest priority should be the root node
    return heapData[0];

  }

  /**
   * Adds the newTask to this priority queue
   * 
   * @param newTask The task to add
   * @throws IllegalArgumentException if the Task is already completed
   * @throws IllegalStateException    if the priority queue is full
   */
  public void enqueue(Task newTask) {


    // throw exception if priority queue is full
    if (this.size() == heapData.length - 1) {
      throw new IllegalStateException("Priority queue is full.");
    }

    // throw exception if Task is completed
    if (newTask.isCompleted())
      throw new IllegalArgumentException("Task is completed");

    heapData[this.size()] = newTask;

    // need to maintain the max-heap
    percolateUp(this.size());

    size++;

  }

  /**
   * Fixes one heap violation by moving it up the heap.
   * 
   * @param index The index of the element where the violation may be.
   */
  protected void percolateUp(int index) {

    while (index > 0) {
      int parentIndex = parent(index);

      // if the data at the parent index has higher priority, no need to move it up
      if (heapData[index].compareTo(heapData[parentIndex], this.priorityCriteria) <= 0)
        return;
      // otherwise, swap the two data points
      else {
        swap(index, parentIndex);
        index = parentIndex;
      }

    }

  }



  /**
   * Gets and removes the Task that has the highest priority.
   * 
   * @return The Task in this queue with the highest priority.
   * @throws NoSuchElementException if the TaskQueue is empty.
   */
  public Task dequeue() {

    // throw an exception if the TaskQueue is empty
    if (isEmpty()) {
      throw new NoSuchElementException("TaskQueue is empty.");
    }

    // replace root value with last leaf node
    Task toReturn = heapData[0];
    heapData[0] = heapData[size() - 1];
    // remove last value
    heapData[size() - 1] = null;
    // correct any heap violations
    size--;
    percolateDown(0);



    return toReturn;

  }

  /**
   * Returns the leftChild's index of given index
   * 
   * @param i The parent index
   * @return The index of the left child
   */
  private int leftChild(int i) {
    return 2 * i + 1;
  }

  /**
   * Returns the rightChild index of given index
   * 
   * @param i The parent index
   * @return The index of the right child
   */
  private int rightChild(int i) {
    return 2 * i + 2;
  }

  /**
   * Returns the parent's index of given index
   * 
   * @param i The child index
   * @return The index of the parent
   */
  private int parent(int i) {
    return (i - 1) / 2;
  }

  /**
   * Swaps the values at the given indices
   * 
   * @param i The first index
   * @param j The second index
   */
  private void swap(int i, int j) {
    Task temp = heapData[i];
    heapData[i] = heapData[j];
    heapData[j] = temp;
  }

  /**
   * Fixes one heap violation by moving it down the heap.
   * 
   * @param index The index of the element where the violation may be.
   */
  protected void percolateDown(int index) {

    // verify that left/right children exist, compare against value at index
    Task leftValue = (leftChild(index) < size()) ? heapData[leftChild(index)] : null;
    Task rightValue = (rightChild(index) < size()) ? heapData[rightChild(index)] : null;

    // if left value is null, it's a leaf node
    if (leftValue == null)
      return;

    // if right value is null
    if (rightValue == null) {
      // left child is a leaf node: check to see if we should swap
      // swap if value at index is smaller than value at left child
      if (heapData[index].compareTo(leftValue, priorityCriteria) < 0) {
        swap(index, leftChild(index));
      }
      // if right value is null, no need to keep going whether or not we swap with left value
      return;
    }

    int compareLeft = heapData[index].compareTo(leftValue, priorityCriteria);
    int compareRight = heapData[index].compareTo(rightValue, priorityCriteria);

    // if this value at index < both children

    if (compareLeft < 0 && compareRight < 0) {
      int swapIndex = rightChild(index);
      // if left value is greater than right, swap with left
      if (leftValue.compareTo(rightValue, priorityCriteria) > 0) {
        swapIndex = leftChild(index);
      }
      // otherwise, swap with right

      swap(index, swapIndex);
      percolateDown(swapIndex);

    }
    // this value < one child's value
    // if value is less than right child, swap with right
    else if (compareLeft > 0 && compareRight <= 0) {
      // swap right
      swap(index, rightChild(index));
      // percolate again
      percolateDown(rightChild(index));
    }
    // if value is less than left child, swap with left
    else if (compareLeft <= 0 && compareRight > 0) {
      swap(index, leftChild(index));
      // percolate again
      percolateDown(leftChild(index));

    } else
      return;

  }

  /**
   * Changes the priority criteria of this priority queue and fixes it so that it's a proper
   * priority queue based on the new criteria
   * 
   * @param priorityCriteria The new criteria that should be used to prioritize the Tasks in the
   *                         queue.
   */
  public void reprioritize(CompareCriteria priorityCriteria) {
    // change this queue's criteria
    this.priorityCriteria = priorityCriteria;

    // fixes it

    // start at last internal node (last node that has children) - parent of the last leaf (index =
    // size -1)
    // parent is (i - 1) / 2 ?
    int lastLeafIndex = size() - 1;
    int lastInternalNodeIndex = parent(lastLeafIndex);

    // find next last internal node, percolate it down, moving backwards towards the root

    while (lastInternalNodeIndex >= 0) {
      // percolate that node down
      percolateDown(lastInternalNodeIndex);
      lastInternalNodeIndex--;

    }

    // making sure that every subtree has the best value at its root

  }


  /**
   * Creates and returns a deep copy of the heap's array of data
   * 
   * @return deepCopy The deep copy of the array holding the heap's data.
   */
  public Task[] getHeapData() {
    return Arrays.copyOf(heapData, size());
  }

}
