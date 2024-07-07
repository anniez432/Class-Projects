//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Sorted Task List
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
// Persons:         TA help with logic problems and debugging 
// Online Sources:  (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////
import java.util.NoSuchElementException;

public class SortedTaskList extends TaskList{

  /**
   * Adds a specific task to this sorted list of tasks (in the increasing order).
   * More explicitly, this method finds the correct location to insert this task into the list,
   * according to the priority of the existing tasks in the list, and adds it ther
   * @param aTask element to be added to this list
   * @throws NullPointerException if aTask is null
   * @throws IllegalArgumentException with a descriptive error message if this list already
   * contains a match with this task (with respect to the Object.equals() method). Duplicate items
   * are not allowed in this list.
   * CITE: TA help with logic problems and debugging errors in this method
   */
  @Override
  public void add(Task aTask) throws NullPointerException{
    if ( aTask == null ) throw new NullPointerException("Null task");
    if (contains(aTask)) throw new IllegalArgumentException("Duplicate object");

    if (isEmpty() || aTask.compareTo(get(size() - 1)) >= 0) {
      super.add(aTask);
    }
    else if(aTask.compareTo(get(0)) < 0 ){
      super.addFirst(aTask);
    }
    else {
        int index = 0;
        while (index < size() && aTask.compareTo(get(index)) > 0) {
          index++;
        }
        super.add(index, aTask);
      }
  }

  /**
   * Adds a task to the front of the list IF AND ONLY IF it is
   * less than any other task in the list
   *
   * @param aTask task to be added to the head of this sorted list
   * @throws NullPointerException if aTask is null
   * @throws IllegalStateException with a descriptive error message
   * if aTask cannot be added to the head of this list, meaning
   * that the list is not empty and aTask is greater that the first task stored in this list.
   *
   */

  @Override
  public void addFirst(Task aTask) throws NullPointerException{
    if (aTask == null) throw new NullPointerException("null argument");

    if(!isEmpty() && aTask.compareTo(get(0)) > 0) {
      throw new IllegalStateException("cannot add to head of list");
    }

    super.addFirst(aTask);
  }

  /**
   * Adds aTask to the given index position within this sorted list IF AND ONLY IF index the correct
   * position to aTask to be inserted in this sorted list.
   * @param index index at which the specified element is to be inserted
   * @param aTask element to be added to this list
   * @throws NullPointerException if aTask is null
   * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size())
   * @throws IllegalStateException with a descriptive error message if index is not a correct
   * position at which aTask can be added to this sorted list (i.e. aTask is greater than the task
   * at index+1 (if any), or aTask is less than the task at index-1 (if any).
   */
  @Override
  public void add(int index, Task aTask) throws NullPointerException,
      java.lang.IndexOutOfBoundsException{
    if (aTask == null) throw new NullPointerException("Task is null");
    if (index < 0 || index > size()) throw new IndexOutOfBoundsException("Out of range");

    if (index == 0) {
      addFirst(aTask);
    }
    else if (index == size()) {
      add(aTask);
    }
    else {
      if (aTask.compareTo(get(index)) > 0 || aTask.compareTo(get(index - 1)) < 0) {
        throw new IllegalStateException("Cannot add task at the specified index");
      }
      super.add(index, aTask);
    }
  }

  /**
   * Returns the task at index zero in this sorted task list
   *  @return get(0) the task at index zero in this sorted task list
   * @throws NoSuchElementException with a descriptive error message if this list is empty
   */
  public Task peekBest(){
    if(isEmpty()) throw new NoSuchElementException("list is empty");

    return get(0);
  }


  /**
   * Removes and returns the task at index zero in this sorted task list
   * @return the task that was at index zero within this sorted task list
   * @throws NoSuchElementException with a descriptive error message if this list is empty
   */
  public Task removeBest(){
    if ( isEmpty() ) throw new NoSuchElementException("list is empty");

    return remove(0);
  }

}
