//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Task List
// Course:   CS 300 Spring 2024
//
// Author:   Rebecca Tran
// Email:    rmtran2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Annie Zhao
// Partner Email: azhao37@wisc.edu
// Partner Lecturer's Name: Hobbes Legault
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X_ Write-up states that pair programming is allowed for this assignment.
//   _X_ We have both read and understand the course Pair Programming Policy.
//   _X_ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:  Hobbes Legault, Lecture code provided outline and logic
// Online Sources:
//
///////////////////////////////////////////////////////////////////////////////
public class TaskList extends Object implements ListADT<Task>{

  /**
   * Reference to the first node in this list (with respect to the forward direction)
   */
  private LinkedNode<Task> head;

  /**
   * Reference to the last node in this list (with respect to the forward direction)
   */
  private LinkedNode<Task> tail;

  /**
   * Total number of Task objects stored in this list
   */
  private int size;


  /**
   * Checks if the list is empty
   * @return true if the list is empty or false otherwise
   */
  @Override
  public boolean isEmpty() {
    return head == null && tail == null && size == 0;
  }

  /**
   * Returns the size of this list
   * @return the number of items in the list
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Removes all of the elements from this list. The list will be empty after this call returns.
   * CITE: Hobbes code from lecture helped to implement logic
   */
  @Override
  public void clear() {
    //start from head and iterate through each node
    LinkedNode<Task> current = head;
    while (current != null) {
      LinkedNode<Task> nextNode = current.getNext(); //save reference to next node before unlinking
      current.setNext(null); //unlink current node
      current.setPrev(null);
      current = nextNode; //move to next node
    }

    //clear head, tail, and size
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * Adds newElement to the end (tail) of this list
   * @param newElement element to be added to this list
   * CITE: Hobbes lecture code helped implement logic for this method
   */
  @Override
  public void add(Task newElement) {
    LinkedNode<Task> toAdd = new LinkedNode<>(newElement);

    // case 1: add to empty list
    if (this.isEmpty()) {
      this.head = toAdd;
      this.tail = toAdd;
    } else {
      LinkedNode<Task> curr = this.tail;
      // update current last node's next and prev
      this.tail.setNext(toAdd);
      //update the new node's prev
      toAdd.setPrev(curr);
      // THEN update tail reference
      this.tail = toAdd;
    }
    this.size++;
  }

  /**
   * Adds new Element to the head of this list
   * @param newElement element to be added to this list
   * @throws NullPointerException if newElement is null
   */
  @Override
  public void addFirst(Task newElement) {
    if (newElement == null) throw new NullPointerException("null element");

    LinkedNode<Task> newNode = new LinkedNode<>(newElement);

    if (isEmpty()) {
      head = newNode;
      tail = newNode;
    }
    else {
      head.setPrev(newNode);
      newNode.setNext(head);
      head = newNode;
    }
    size++;
  }

  /**
   * Adds newElement at the given position index within this list
   * CITE: Code help and outline from Hobbes lecture
   * @param index      index at which the specified element is to be inserted
   * @param newElement element to be added to this list
   * @throws NullPointerException if newElement is null
   * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size())
   * CITE: Hobbes lecture code helped implement logic in this method
   */
  @Override
  public void add(int index, Task newElement) {
    if (newElement == null)
      throw new NullPointerException("Can't add null element");
    if (index < 0 || index > size)
      throw new IndexOutOfBoundsException("Invalid index");

    // (1) add at the end - index == size
    if (index == size) {
      LinkedNode<Task> temp = tail;
      this.add(newElement);// also handles adding to an empty list
      tail.setPrev(temp);
    }
    else {
      LinkedNode<Task> toAdd = new LinkedNode<Task>(newElement);
      // (2) add at the beginning - index == 0
      if (index == 0) {
        // set the next (and/or prev) of your new node FIRST
        toAdd.setNext(head);
        // THEN update any references for the list
        head = toAdd;
      }
      // (3) add in the middle
      else {
        LinkedNode<Task> temp = head;
        // stop one index BEFORE where we want to add our node
        for (int i = 0; i < index - 1; i++) temp = temp.getNext();
        toAdd.setNext(temp.getNext());
        if (temp.getNext() != null)
          temp.getNext().setPrev(toAdd); // Update next node's previous reference if next node is not null
        toAdd.setPrev(temp);
        temp.setNext(toAdd);
      }
      size++;
    }
  }


  /**
   * Returns the element at the specified position in this list.
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  @Override
  public Task get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index is out of range");
    }
    LinkedNode<Task> current = head;
    for (int i = 0; i < index; i++) {
      current = current.getNext();
    }
    return current.getData();
  }

  /**
   * Returns true if this list contains the specified element toFind. More formally, returns true
   * if and only if this list contains at least one element e such that toFind.equals(e) == true.
   * @param toFind element to find
   * @return true if this list contains at least one match with toFind
   */
  @Override
  public boolean contains(Task toFind) {
    LinkedNode<Task> current = head;
    while (current != null){
      if ( toFind.equals(current.getData()) ){
        return true;
      }
      current = current.getNext();
    }
    return false;
  }

  /**
   * Removes the element at the specified position in this list.
   * CITE: outline and help from lecture code
   * @param index the index of the element to be removed
   * @return the element that was removed from the list
   * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index ≥ size())
   * CITE: Hobbes lecture code helped implement logic in this method
   */
  @Override
  public Task remove(int index) {
    if (index<0 || index >= size) throw new IndexOutOfBoundsException("Invalid index");

    Task toReturn = get(index);

    //remove the ONLY thing
    if (size == 1) {
      head = null;
      tail = null;
    }
    //remove the FIRST thing
    else if (index == 0) {
      // need to update the first reference
      head = head.getNext();
    }
    //remove the LAST thing
    else if (index == size - 1) {
      // need to update the last reference
      tail = tail.getPrev();
      if (tail != null)
        tail.setNext(null); // Update the next reference of the new last node if it's not null
    }
    else {
      LinkedNode<Task> temp = head;
      for (int i = 0; i < index; i++) {
        temp = temp.getNext();
      }
      toReturn = temp.getData();
      LinkedNode<Task> prevNode = temp.getPrev();
      LinkedNode<Task> nextNode = temp.getNext();
      prevNode.setNext(nextNode);
      if (nextNode != null)
        nextNode.setPrev(prevNode); //update the previous reference of the next node if not null
    }
    size--;

    return toReturn;
  }

  /**
   * Returns a String representation of the contents of this list traversed in the forward direction
   * separated by a newline.
   *
   * @return a String representation of the connected nodes making this linked list traversed
   * starting from the head of the list
   */
  protected String traverseForward(){
    String result = "";
    LinkedNode<Task> current = head;
    while (current != null){
      result += current.getData() + "\n";
      current = current.getNext();
    }

    return result;
  }

  /**
   * Returns a String representation of the contents of this list traversed in the backward
   * direction separated by a newline.
   *
   * @return a String representation of the connected nodes making this linked list traversed
   * starting from the tail of the list
   */
  protected String traverseBackward(){
    String result = "";
    LinkedNode<Task> current = tail;
    while( current!= null ){
      result += current.getData() + "\n";
      current = current.getPrev();
    }

    return result;
  }

  /**
   * Returns a String representation of this task list traversed in the specified direction.
   *
   * @param forward indicates the traversal direction: true for forward, false for backward
   * @return a String representation of the tasks stored in this task list, separated by a newline
   */
  public String traverse(boolean forward) {
    if (forward) {
      return traverseForward();
    } else {
      return traverseBackward();
    }
  }

}
