//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Task Manager Tester
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
import java.sql.SQLOutput;

/**
 * This class tests the TaskManager, TaskList, and SortedTaskList classes.
 */
public class TaskManagerTester {

  /**
   * Checks the correctness of the implementation of the method compareTo() defined in the Task
   * class. Consider different test scenarios including each of the SortingOrder values: TITLE and
   * PRIORITY
   *
   * Test scenarios: <BR>
   * aTask and anotherTask references any Task objects you can create.<BR>
   * 1. aTask.compareTo(anotherTask) is expected to return 0 if they are equal with respect to the
   * comparison criteria. <BR>
   * 2. aTask.compareTo(aTask) is expected to return 0 <BR>
   * 3. aTask.compareTo(anotherTask) is expected to return a negative integer (less than zero) if
   * aTask is less than another Task with respect to the comparison criteria. <BR>
   * 4.aTask.compareTo(anotherTask) is expected to return a positive integer (greater than zero) if
   * aTask is greater than another Task with respect to the comparison criteria.
   *
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testTaskCompareTo() {
    Task task1 = new Task("1", "buy");
    task1.setSortingOrderByTitle();
    Task task2 = new Task("z", "clean");
    task2.setSortingOrderByTitle();
    Task task3 = new Task("z", "shop", true);
    task3.setSortingOrderByPriorityLevel();
    Task task4 = new Task("a", "clean", false);
    task4.setSortingOrderByPriorityLevel();
    Task taskA = new Task("a", "clean");
    taskA.setSortingOrderByTitle();
    Task taskB = new Task("a", "clean");
    taskB.setSortingOrderByTitle();
    Task taskC = new Task("8", "shop", true);
    taskC.setSortingOrderByPriorityLevel();
    Task taskD = new Task("2", "clean", true);
    taskD.setSortingOrderByPriorityLevel();

    //compare 2 tasks of title sorting with different titles
    if (task1.compareTo(task2) > 0) return false;
    if ( task2.compareTo(task1) < 0 ) return false;

    //compare 2 tasks of title sorting with same title
    if (taskA.compareTo(taskB) != 0) return false;
    if ( taskB.compareTo(taskA) != 0 ) return false;

    //compare 2 tasks of priority sorting with different priority
    if (task3.compareTo(task4) > 0) return false;
    if ( task4.compareTo(task3) < 0 ) return false;

    //compare 2 tasks of priority sorting with same priority
    if (taskC.compareTo(taskD) != 0) return false;
    if ( taskD.compareTo(taskC) != 0 ) return false;

    return true; //return true if all tests pass
  }

  /**
   * Checks the correctness of the implementation of the equals() method defined in the Task class.
   *
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testTaskEquals() {
    Task task1 = new Task("first", "clean", false);
    Task task2 = new Task("first", "clean", true);
    Task taskA = new Task("First", "clean");
    Task taskC = new Task("FIRST", "CLEAN");
    Task task3 = new Task("second", "shop");
    Object task4 = new Object();

    //Check if the equals method works on two equal tasks
    if ( ! task1.equals(task2) ) return false;
    //Check if the equals method works on two unequal tasks
    if ( task1.equals(task3) ) return false;
    //Check if the equals method works with a null and different class task parameter
    if ( task1.equals(task4) ) return false;
    //tests the case insensitivity
    if (!task2.equals(taskC)) return false;
    //tests the case insensitivity
    if (!taskC.equals(taskA)) return false;

    return true;  //return true if all tests pass
  }

  /**
   * Tests the add(), isEmpty(), and size() methods of the TaskList class.
   *
   * Test scenarios: <BR>
   * 1. Create a new empty TaskList and verify that isEmpty() returns true.<BR>
   * 2. Add a few tasks to the end of the TaskList and verify that isEmpty() returns false.<BR>
   * 3. Verify that size() returns the expected size after adding each Task.
   *
   * @return true if all test scenarios pass, false otherwise.
   *
   */
  public static boolean testAddIsEmptySize() {
    TaskList list = new TaskList();

    //test isEmpty on the empty list
    if (!list.isEmpty()) return false;
    if (list.size() != 0) return false;

    Task taskA = new Task("a", "new");
    Task taskB = new Task("b", "this");
    Task taskC = new Task("c", "clear");

    list.add(taskA); //add to an empty list
    if ( list.get(0) != taskA ) return false;
    if ( list.size()!=1 ) return false;
    list.add(taskB); //add to a non-empty list
    if ( list.get(1) != taskB ) return false;
    if ( list.size()!=2 ) return false;
    list.add(taskC);
    if ( list.get(2) != taskC ) return false;
    if ( list.size()!=3 ) return false;

    if ( list.isEmpty() ) return false;

    return true;
  }

  /**
   * Tests the addFirst(), and add(index, element) methods of the TaskList class.
   *
   * Test scenarios: <BR>
   * 1. Test adding elements to an empty TaskList <BR>
   * 2. Test adding elements to the beginning, middle, and end of a non-empty TaskList.
   *
   * @return true if all test scenarios pass, false otherwise.
   *
   */
  public static boolean testAddToTaskList() {
    TaskList list = new TaskList();
    Task task1 = new Task("1", "yes");
    Task task2 = new Task("2", "walk");
    Task task3 = new Task("3", "run");
    Task task4 = new Task("4", "shop");
    Task task5 = new Task("5", "clean");
    Task task6 = new Task("6", "homework");

    //test addfirst
    list.add(task3);
    list.addFirst(task2);
    if (list.get(0) != task2) return false;
    list.addFirst(task1);
    if (list.get(0) != task1) return false;
    if (list.get(1) != task2) return false;
    if (list.get(2) != task3) return false;

    //test add to a specific index
    list.add(0,task4); //add to beginning
    if (list.get(0) != task4) return false;
    if (list.get(1) != task1) return false;
    if (list.get(2) != task2) return false;
    if (list.get(3) != task3) return false;
    if (list.size()!=4) return false;

    list.add(4,task5); //adding to the end
    if (list.get(4) != task5) return false;
    if (list.get(0) != task4) return false;
    if (list.get(1) != task1) return false;
    if (list.get(2) != task2) return false;
    if (list.get(3) != task3) return false;
    if (list.size()!=5) return false;

    list.add(2, task6); //adding to middle
    if (list.get(0) != task4) return false;
    if (list.get(1) != task1) return false;
    if (list.get(2) != task6) return false;
    if (list.get(3) != task2) return false;
    if (list.get(4) != task3) return false;
    if (list.get(5) != task5) return false;
    if (list.size()!=6) return false;

    //test exceptions
    try {
      list.add(-1, task1);
    } catch (IndexOutOfBoundsException e){
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
    }
    try {
      list.add(10, task1);
    } catch (IndexOutOfBoundsException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
    }
    try {
      list.add(1, null);
    } catch (NullPointerException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
    }
    return true;
  }

  /**
   * Tests the contains() method of the TaskList class
   */
  private static boolean testContains(){
    TaskList list = new TaskList();
    Task task1 = new Task("1", "yes");
    Task task2 = new Task("2", "walk");
    Task task3 = new Task("3", "run");
    if ( list.contains(task1) ) return false;

    list.add(task1);
    if ( !list.contains(task1) ) return false;
    list.add(task2);
    if ( !list.contains(task2) ) return false;
    list.add(task3);
    if ( !list.contains(task3) ) return false;

    return true;
  }

  /**
   * Test the traverse() method along with traverseBackward() and traverseForward()
   */
  private static boolean testTraverse(){
    TaskList list = new TaskList();
    Task task1 = new Task("1", "yes");
    Task task2 = new Task("2", "walk");
    Task task3 = new Task("3", "run");
    String empty = list.traverse(true);
    if ( !empty.equals("") ) return false;
    if ( !empty.isEmpty() ) return false;

    list.add(task1);
    list.add(task2);
    list.add(task3);

    String forward = list.traverse(true);
    String backward = list.traverse(false);

    return true;
  }

  /**
   * Tests and remove(index) and clear() methods of the TaskList class.
   *
   * Test scenarios: <BR>
   * 1. Test removing elements from various positions in the TaskList using remove(index). <BR>
   * 2. Test removing elements from an empty TaskList or at invalid indices. <BR>
   * 3. Test clear() method and verify that the TaskList is empty after calling it.
   *
   * @return true if all test scenarios pass, false otherwise.
   *
   */
  public static boolean testRemoveClear() {
    TaskList list = new TaskList();
    Task taskA = new Task("a", "new");
    Task taskB = new Task("b", "this");
    Task taskC = new Task("c", "clear");

    //test clear method
    list.add(taskA);
    list.add(taskB);
    list.add(taskC);

    list.clear();
    if ( !list.isEmpty() ) return false;
    if ( list.size() != 0 ) return false;

    //test remove method
    list.add(taskA);
    list.add(taskB);
    list.add(taskC);
    try{
      list.remove(-1);
    } catch (IndexOutOfBoundsException e){
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
    }
    list.remove(0);
    if (list.get(0) != taskB) return false;
    if (list.get(1) != taskC) return false;
    list.remove(1);
    if (list.get(0) != taskB) return false;
    list.add(taskA);
    list.add(taskC);
    list.remove(1);
    if (list.get(0) != taskB) return false;
    if (list.get(1) != taskC) return false;

    return true; //return true if all tests pass
  }
  /**
   * Tests the add() method of the SortedTaskList class.
   *
   * Test scenarios: <BR>
   * 1. Test adding a task to an empty TaskList <BR>
   * 2. Test adding tasks to a non-empty sorted task list such that the task should be added to the
   * beginning, middle, and end of a non-empty TaskList.
   *
   * @return true if all test scenarios pass, false otherwise.
   *
   */
  public static boolean testAddToSortedTaskList() {
    Task.setSortingOrderByTitle();

    SortedTaskList list = new SortedTaskList();
    Task task1 = new Task("1", "Description 1");
    Task task2 = new Task("2", "Description 2");
    Task task3 = new Task("3", "Description 3");
    Task task4 = new Task("4", "Description 4");

    Task nullTask = null;

    //(1) adding to empty list
    list.add(task3);
    if (list.get(0) != task3) return false;
    if (list.size() != 1) return false;

    //(2) should be added in the front
    list.add(task1);
    if (list.get(0) != task1) return false;
    if (list.get(1) != task3) return false;
    if (list.size() != 2) return false;
    //(3) should be added to the middle
    list.add(task2);
    if (list.get(0) != task1) return false;
    if (list.get(1) != task2) return false;
    if (list.get(2) != task3) return false;
    if (list.size() != 3) return false;
    //(4) should be added to the end
    list.add(task4);
    if (list.get(0) != task1) return false;
    if (list.get(1) != task2) return false;
    if (list.get(2) != task3) return false;
    if (list.get(3) != task4) return false;
    if (list.size() != 4) return false;

    //testing addFirst of sorted test class
    Task task0 = new Task("0", "Description 0");
    Task task5 = new Task("5", "Description 5");
    list.addFirst(task0);
    if (list.get(0) != task0) return false;
    try { //test the illegal state exception
      list.addFirst(task5);
    } catch (IllegalStateException e){
      if (e.getMessage().isEmpty()){
        return false;
      }
    }

    //test add(index, task)
    try { //test the illegal state exception
      list.add(1,task5);
    } catch (IllegalStateException e){
      if (e.getMessage().isEmpty()){
        return false;
      }
    }
    list.add(5,task5);
    if (list.get(5) != task5) return false;

    //test the exceptions
    try { //test the null pointer exception
      list.add(nullTask);
    } catch (NullPointerException e){
      if (e.getMessage().isEmpty()){
        return false;
      }
    }
    try { //test the illegal argument exception
      list.add(task2);
    } catch (IllegalArgumentException e){
      if (e.getMessage().isEmpty()){
        return false;
      }
    }

    return true; //return true if all tests pass
  }


  /**
   * Tests the appendTask() method of the TaskManager class.
   *
   * Test scenarios: <BR>
   * 1. Test appending a task to an empty TaskManager. <BR>
   * 2. Test appending multiple tasks to the TaskManager.
   *
   * @return true if all test scenarios pass, false otherwise.
   *
   */
  public static boolean testAppendTask(){
    TaskManager taskManager = new TaskManager();
    //append to empty taskManager
    Task task1 = new Task("task1", "yes", true);
    taskManager.appendTask(task1);

    if(taskManager.toDoList.size() != 1) return false;
    if(!taskManager.toDoList.get(0).equals(task1)) return false;

    //append multiple tasks
    Task task2 = new Task("task2", "yes", true);
    Task task3 = new Task("task3", "yes", true);

    taskManager.appendTask(task2);
    taskManager.appendTask(task3);

    if(taskManager.toDoList.size() != 3) return false;
    if(!taskManager.toDoList.get(1).equals(task2)) return false;
    if(!taskManager.toDoList.get(2).equals(task3)) return false;

    return true;
  }

  /**
   * Tests the moveToTop(), moveToBottom(), moveBeforeOtherTask(), and moveAfterOtherTask()
   * methods of the TaskManager class.
   *
   * Test scenarios:
   * 1. Test moving tasks to various positions in the toDoList and ensure the move methods have
   * no impact on the contents of the completedTasks list.
   * 2. Test moving tasks relative to other tasks (before/after).
   *
   * @return true if all test scenarios pass, false otherwise.
   *
   */
  public static boolean testMoveTasks() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("task1", "yes", true);
    Task task2 = new Task("task2", "yes", true);
    Task task3 = new Task("task3", "no", true);
    taskManager.appendTask(task1);
    taskManager.appendTask(task2);
    taskManager.appendTask(task3);
    taskManager.completeTask(2);
    //test moving to various positions

    //moveToTop()
    taskManager.movetoTop(1);
    //should be task2, task1
    if(!taskManager.toDoList.get(0).equals(task2)) return false;
    if(!taskManager.toDoList.get(1).equals(task1)) return false;
    if(!taskManager.completedTasks.get(0).equals(task3)) return false;

    //test exception
    try{
      taskManager.movetoTop(2);
      taskManager.movetoTop(-1);
    } catch(IndexOutOfBoundsException e){
      if (e.getMessage() == null || e.getMessage().isBlank()){
        return false;
      }
    }

    //movetoBottom()
    taskManager.moveToBottom(0);
    //should be task1, task2
    if(!taskManager.toDoList.get(0).equals(task1)) return false;
    if(!taskManager.toDoList.get(1).equals(task2)) return false;
    if(!taskManager.completedTasks.get(0).equals(task3)) return false;

    //test exception
    try{
      taskManager.moveToBottom(2);
      taskManager.moveToBottom(-1);
    } catch(IndexOutOfBoundsException e){
      if (e.getMessage() == null || e.getMessage().isBlank()){
        return false;
      }
    }
    //test moving tasks relative to other tasks

    //moveBeforeOtherTask()
    boolean expected = true;
    boolean actual = taskManager.moveBeforeOtherTask(1, 0);
    //should be task2, task1
    if(expected != actual) return false;
    if(!taskManager.toDoList.get(0).equals(task2)) return false;
    if(!taskManager.toDoList.get(1).equals(task1)) return false;

    //moveAfterOtherTask()
    expected = true;
    actual = taskManager.moveAfterOtherTask(0, 1);
    //should be task 1, task2;
    if(expected != actual) return false;
    if(!taskManager.toDoList.get(0).equals(task1)) return false;
    if(!taskManager.toDoList.get(1).equals(task2)) return false;

    if(!taskManager.completedTasks.get(0).equals(task3)) return false;

    return true;
  }

  /**
   * Tests the removeTask() method of the TaskManager class.
   *
   * Test scenarios: <BR>
   * 1. Test removing tasks from various positions in the toDoList and completedTasks lists. <BR>
   * 2 Test removing tasks at invalid indices return false.
   *
   *
   * @return true if all test scenarios pass, false otherwise.
   *
   */
  public static boolean testRemoveTask() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("task1", "Task 1");
    Task task2 = new Task("task2", "Task 2");
    Task task3 = new Task("task3", "Task 3");
    taskManager.appendTask(task1);
    taskManager.appendTask(task2);
    taskManager.appendTask(task3);

    taskManager.removeTask(1); // Removing task2

    // verify that task2 is removed from the to-do list
    if (taskManager.toDoList.size() != 2 ) return false;
    if (taskManager.toDoList.contains(task2)) return false;

    //remove a task at an invalid index
    if ( taskManager.removeTask(5) ) return false;

    // Verify that the state of the task manager hasn't changed after attempting to remove a task
    // at an invalid index
    if (taskManager.toDoList.size() != 2) {
      return false;
    }

    return true;
  }

  /**
   * Tests the completeTask() method of the TaskManager class.
   *
   * Test scenarios: <BR>
   * 1. Test completing tasks from various positions in the toDoList. <BR>
   * 2. Test completing tasks at invalid indices.
   *
   * @return true if all test scenarios pass, false otherwise.
   *
   */
  public static boolean testCompleteTask() {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("task1", "Task 1");
    Task task2 = new Task("task2", "Task 2");
    Task task3 = new Task("task3", "Task 3");

    taskManager.appendTask(task1);
    taskManager.appendTask(task2);
    taskManager.appendTask(task3);
    taskManager.completeTask(1); // Completing task2

    //verify that task2 is moved to the completed tasks list
    if ( taskManager.completedTasks.size() != 1 ) return false;
    if ( !taskManager.completedTasks.contains(task2)) return false;

    //verify that task2 is removed from the to-do list
    if (taskManager.toDoList.size() != 2 ) return false;
    if (taskManager.toDoList.contains(task2)) return false;

    //complete a task at an invalid index
    if ( taskManager.completeTask(5) ) return false; // Attempting to complete task at index 5

    //check state of the task manager
    if (taskManager.toDoList.size() != 2 ) return false;
    if (taskManager.completedTasks.size() != 1) return false;

    return true;
  }

  /**
   * Main method
   *
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("testTaskCompareTo(): " + testTaskCompareTo());
    System.out.println("testTaskEquals(): " + testTaskEquals());
    System.out.println("testAddIsEmptySize(): " + testAddIsEmptySize());
    System.out.println("testAddToTaskList(): " + testAddToTaskList());
    //System.out.println("testContains(): " + testContains());
    //System.out.println("testTraverse(): " + testTraverse());
    System.out.println("testRemoveClear(): " + testRemoveClear());
    System.out.println("testAddToSortedTaskList(): " + testAddToSortedTaskList());
    System.out.println("testAppendTask(): " + testAppendTask());
    System.out.println("testMoveTasks(): " + testMoveTasks());
    System.out.println("testRemoveTask(): " + testRemoveTask());
    System.out.println("testCompleteTask(): " + testCompleteTask());
  }
}
