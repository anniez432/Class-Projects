//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Task Manager
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
// Persons:         (identify each by name and describe how they helped)
// Online Sources:  (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This class models TaskManager objects. A TaskManager has a to do list of incomplete tasks, and a
 * list of completed tasks. This class allows the user to manage the tasks in the to do list.
 */
public class TaskManager {

  /**
   * to do list of incomplete tasks
   */
  protected TaskList toDoList; // list of incomplete tasks

  /**
   * TaskList of completed tasks
   */
  protected TaskList completedTasks; // list of completed tasks

  /**
   * Constructs a TaskManager with empty to-do and completed task lists.
   */
  public TaskManager() {
    this.toDoList = new TaskList();
    this.completedTasks = new TaskList();
  }


  /**
   * Appends a task to the end of the to-do list.
   *
   * @param task The task to be added to the to-do list.
   */
  public void appendTask(Task task) {
    toDoList.add(task);
  }

  /**
   * Moves a specified task to the top (head) of the to-do list.
   *
   * @param indexTaskToMove The index of task to move to the top (head) of the to-do list.
   * @throws IndexOutOfBoundsException if indexTaskToMove is out of bounds with respect to the to-do
   *                                   list valid range of indexes.
   */
  public void movetoTop(int indexTaskToMove) {
    if (indexTaskToMove < 0 || indexTaskToMove >= toDoList.size()) {
      throw new IndexOutOfBoundsException("Index is out of bounds.");
    }
    Task taskToMove = toDoList.remove(indexTaskToMove);
    toDoList.addFirst(taskToMove);
  }

  /**
   * Moves a specified task to the bottom (tail) of the to-do list.
   *
   * @param indexTaskToMove The index of task to move to the bottom (tail) of the to-do list.
   * @throws IndexOutOfBoundsException if indexTaskToMove is out of bounds with respect to the to-do
   *                                   list valid range of indexes.
   */
  public void moveToBottom(int indexTaskToMove) {
    if (indexTaskToMove < 0 || indexTaskToMove >= toDoList.size()) {
      throw new IndexOutOfBoundsException("Index is out of bounds.");
    }
    Task taskToMove = toDoList.remove(indexTaskToMove);
    toDoList.add(taskToMove);

  }

  /**
   * Moves a specified task to a position before another task in the to-do list.
   *
   * @param indexTaskToMove The index of the task to move within the to-do list.
   * @param indexOtherTask  The index of the other task before which the first task will be moved.
   * @return true if the task was successfully moved; false otherwise.
   */
  public boolean moveBeforeOtherTask(int indexTaskToMove, int indexOtherTask) {
    if (indexTaskToMove < 0 || indexTaskToMove >= toDoList.size() || indexOtherTask < 0 ||
        indexOtherTask >= toDoList.size() ){
      return false;
    }
    if (indexTaskToMove < indexOtherTask) {
      Task taskToMove = toDoList.remove(indexTaskToMove);
      toDoList.add(indexOtherTask - 1, taskToMove);
    } else if (indexTaskToMove > indexOtherTask) { //check if the task to move comes after other
      Task taskToMove = toDoList.remove(indexTaskToMove);
      toDoList.add(indexOtherTask, taskToMove);
    } else { //if they're the same don't move
      return true;
    }
    return true;
  }

  /**
   * Moves a specified task to a position after another task in the to-do list.
   *
   * @param indexTaskToMove The index of the task to move.
   * @param indexOtherTask  The index of task after which the first task will be moved.
   * @return true if the task was successfully moved; false otherwise.
   */
  public boolean moveAfterOtherTask(int indexTaskToMove, int indexOtherTask) {
    if (indexTaskToMove < 0 || indexTaskToMove >= toDoList.size() || indexOtherTask < 0 || indexOtherTask >= toDoList.size()) {
      return false;
    }


    if(indexOtherTask == toDoList.size() - 1){
      Task taskToMove = toDoList.remove(indexTaskToMove);
      toDoList.add(taskToMove);
      if(!toDoList.get(toDoList.size() - 1).equals(taskToMove)) return false;
    } else {
      Task taskToMove = toDoList.remove(indexTaskToMove);
      toDoList.add(indexOtherTask + 1, taskToMove);
      if (!toDoList.get(indexOtherTask + 1).equals(taskToMove)) return false;
    }
    return true;

  }

  /**
   * Removes a task from the to-do list based on its index.
   *
   * @param index The index of the task to remove.
   * @return true if the task was successfully removed; false if the index was invalid.
   */
  public boolean removeTask(int index) {
    if (index < 0 || index >= toDoList.size())
      return false;
    Task toRemove = toDoList.remove(index);

    if (!toDoList.contains(toRemove))
      return true;
    else
      return false;

  }

  /**
   * Marks a task in the to-do list as complete and moves it to the completed tasks list, based on
   * its index.
   *
   * @param index The index of the task to mark as complete.
   * @return true if the task was successfully marked as complete and moved; false if the index was
   * invalid.
   */
  public boolean completeTask(int index) {
    if (index < 0 || index >= toDoList.size())
      return false;

    Task completedTask = toDoList.remove(index);
    completedTasks.add(completedTask);

    if (!completedTasks.contains(completedTask) ||
        toDoList.contains(completedTask)) return false;

    return true;
  }

  /**
   * Returns a new TaskList that contains all tasks sorted in the increasing order
   *
   * @return a new TaskList that contains all tasks sorted in the increasing order
   */
  public TaskList sortTasks() {
    SortedTaskList sortedList = new SortedTaskList();
    for (int i = 0; i < toDoList.size(); i++) {
      sortedList.add(toDoList.get(i));
    }
    return sortedList;
  }

}

