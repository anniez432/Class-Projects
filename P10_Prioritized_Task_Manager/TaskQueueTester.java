//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P10 TaskQueue Tester
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
// Persons: Garrick Fuller helped me better understand what the
// autograder outputted while debugging my dequeue() tester.
//
///////////////////////////////////////////////////////////////////////////////
import java.util.NoSuchElementException;

/**
 * Tester methods to check the correctness of methods for the Prioritized Task Manager assignment.
 */
public class TaskQueueTester {

  public static void main(String[] args) {
    System.out.println(testCompareToTime());

    System.out.println(testCompareToTitle());

    System.out.println(testCompareToLevel());

    System.out.println("Enqueue: " + testEnqueue());

    System.out.println("Dequeue: " + testDequeue());

    System.out.println("Peek: " + testPeek());

    System.out.println("Reprioritize: " + testReprioritize());

  }

  /**
   * Tests correctness of compareTo() method implementation when the criteria parameter is TIME
   * 
   * @return true if all test cases passes, false if not.
   */
  public static boolean testCompareToTime() {
    Task task10 = new Task("title", "homework", 10, PriorityLevel.OPTIONAL);
    Task task10_2 = new Task("title", "homework", 10, PriorityLevel.OPTIONAL);

    // if the two are not the same, return false
    if (task10.compareTo(task10_2, CompareCriteria.TIME) != 0)
      return false;

    Task task5 = new Task("title", "homework", 5, PriorityLevel.OPTIONAL);

    // task5 should have lower priority -> negative
    if (task5.compareTo(task10, CompareCriteria.TIME) >= 0)
      return false;

    // task10 should have higher priority -> positive
    if (task10.compareTo(task5, CompareCriteria.TIME) <= 0)
      return false;

    Task task15 = new Task("title", "homework", 15, PriorityLevel.OPTIONAL);

    // task15 should have higher priority -> positive
    if (task15.compareTo(task10, CompareCriteria.TIME) <= 0)
      return false;

    // task 10 should have lower priority -> negative
    if (task10.compareTo(task15, CompareCriteria.TIME) >= 0)
      return false;


    return true;
  }

  /**
   * Tests correctness of Task compareTo() method implementation when criteria parameter is TITLE.
   * 
   * @return true if implementation passes all test cases, false if not
   */
  public static boolean testCompareToTitle() {
    Task aTask = new Task("atask", "homework", 10, PriorityLevel.OPTIONAL);
    Task ATask = new Task("Atask", "homework", 10, PriorityLevel.OPTIONAL);

    // ATask > atask -> positive number
    if (ATask.compareTo(aTask, CompareCriteria.TITLE) <= 0)
      return false;

    // aTask < ATask -> negative number
    if (aTask.compareTo(ATask, CompareCriteria.TITLE) >= 0)
      return false;

    // same title as aTask
    Task aTask_2 = new Task("atask", "math", 5, PriorityLevel.HIGH);

    // aTask = aTask
    if (aTask.compareTo(aTask_2, CompareCriteria.TITLE) != 0)
      return false;

    Task bTask = new Task("btask", "homework", 10, PriorityLevel.OPTIONAL);

    // a task > b task -> positive number
    if (aTask.compareTo(bTask, CompareCriteria.TITLE) <= 0)
      return false;

    // b task < a task -> negative number
    if (bTask.compareTo(aTask, CompareCriteria.TITLE) >= 0)
      return false;


    return true;
  }

  /**
   * Tests correctness of Task compareTo() method implementation when the criteria parameter is
   * LEVEl
   * 
   * @return true if the implementation passes all test cases, false if not.
   */
  public static boolean testCompareToLevel() {
    // optional = optional -> 0
    Task optional = new Task("task", "homework", 10, PriorityLevel.OPTIONAL);
    Task optional2 = new Task("task2", "math", 5, PriorityLevel.OPTIONAL);

    if (optional.compareTo(optional2, CompareCriteria.LEVEL) != 0)
      return false;

    // optional < low -> -1
    Task low = new Task("task", "homework", 10, PriorityLevel.LOW);

    if (optional.compareTo(low, CompareCriteria.LEVEL) >= 0)
      return false;

    // optional < medium -> -1
    Task medium = new Task("task", "homework", 10, PriorityLevel.MEDIUM);

    if (optional.compareTo(medium, CompareCriteria.LEVEL) >= 0)
      return false;

    // optional < high -> -1
    Task high = new Task("task", "homework", 10, PriorityLevel.HIGH);

    if (optional.compareTo(high, CompareCriteria.LEVEL) >= 0)
      return false;

    // optional < urgent -> -1
    Task urgent = new Task("task", "homework", 10, PriorityLevel.URGENT);

    if (optional.compareTo(urgent, CompareCriteria.LEVEL) >= 0)
      return false;

    // low = low -> 0
    Task low2 = new Task("task2", "math", 5, PriorityLevel.LOW);

    if (low.compareTo(low2, CompareCriteria.LEVEL) != 0)
      return false;

    // low > optional -> 1
    if (low.compareTo(optional, CompareCriteria.LEVEL) <= 0)
      return false;

    // low < medium -> -1
    if (low.compareTo(medium, CompareCriteria.LEVEL) >= 0)
      return false;

    // low < high -> -1
    if (low.compareTo(high, CompareCriteria.LEVEL) >= 0)
      return false;

    // low < urgent -> -1
    if (low.compareTo(urgent, CompareCriteria.LEVEL) >= 0)
      return false;

    // medium = medium -> 0
    Task medium2 = new Task("task2", "math", 5, PriorityLevel.MEDIUM);

    if (medium.compareTo(medium2, CompareCriteria.LEVEL) != 0)
      return false;

    // medium > low -> 1
    if (medium.compareTo(low, CompareCriteria.LEVEL) <= 0)
      return false;

    // medium > optional -> 1
    if (medium.compareTo(optional, CompareCriteria.LEVEL) <= 0)
      return false;

    // medium < high -> -1
    if (medium.compareTo(high, CompareCriteria.LEVEL) >= 0)
      return false;

    // medium < urgent -> -1
    if (medium.compareTo(urgent, CompareCriteria.LEVEL) >= 0)
      return false;

    // high = high -> 0
    Task high2 = new Task("task2", "math", 5, PriorityLevel.HIGH);

    if (high.compareTo(high2, CompareCriteria.LEVEL) != 0)
      return false;

    // high > medium -> 1
    if (high.compareTo(medium, CompareCriteria.LEVEL) <= 0)
      return false;

    // high > low -> 1
    if (high.compareTo(low, CompareCriteria.LEVEL) <= 0)
      return false;

    // high > optional -> 1
    if (high.compareTo(optional, CompareCriteria.LEVEL) <= 0)
      return false;

    // high < urgent -> -1
    if (high.compareTo(urgent, CompareCriteria.LEVEL) >= 0)
      return false;

    // urgent = urgent -> 0
    Task urgent2 = new Task("task2", "math", 5, PriorityLevel.URGENT);

    if (urgent.compareTo(urgent2, CompareCriteria.LEVEL) != 0)
      return false;

    // urgent > high -> 1
    if (urgent.compareTo(high, CompareCriteria.LEVEL) <= 0)
      return false;

    // urgent > medium -> 1
    if (urgent.compareTo(medium, CompareCriteria.LEVEL) <= 0)
      return false;

    // urgent > low -> 1
    if (urgent.compareTo(low, CompareCriteria.LEVEL) <= 0)
      return false;

    // urgent > optional -> 1
    if (urgent.compareTo(optional, CompareCriteria.LEVEL) <= 0)
      return false;


    return true;
  }

  /**
   * Tests correctness of TaskQueue enqueue() method implementation.
   * 
   * @return true if implementation passes all test cases, false if not
   */
  public static boolean testEnqueue() {
    // add to empty
    TaskQueue queue = new TaskQueue(5, CompareCriteria.LEVEL);

    if (queue.size() != 0)
      return false;

    Task task1 = new Task("atask", "homework", 5, PriorityLevel.URGENT);

    queue.enqueue(task1);

    // check size
    if (queue.size() != 1)
      return false;

    // add completed-should throw IllegalArgumentException

    Task completed = new Task("atask", "homework", 5, PriorityLevel.URGENT);
    completed.markCompleted();

    try {
      queue.enqueue(completed);
      return false;
    } catch (IllegalArgumentException e) {
      if (e.getMessage() == null || e.getMessage().isBlank())
        return false;
      System.out.println(e.getMessage());
    }

    // add another should be valid
    TaskQueue queueLevel = new TaskQueue(5, CompareCriteria.LEVEL);
    Task task2 = new Task("btask", "homework", 10, PriorityLevel.MEDIUM);

    queueLevel.enqueue(task1);
    queueLevel.enqueue(task2);

    // check size
    if (queueLevel.size() != 2)
      return false;

    // one child:
    // when comparing by priority level: task1, task2
    Task[] expected = new Task[2];
    expected[0] = task1;
    expected[1] = task2;

    for (int i = 0; i < queueLevel.size(); ++i) {
      if (!queueLevel.getHeapData()[i].equals(expected[i]))
        return false;
    }

    /*
     * if (!Arrays.deepEquals(queueLevel.getHeapData(), expected)) return false;
     */

    // comparing by title: atask, btask
    expected[0] = task2;
    expected[1] = task1;

    TaskQueue queueTitle = new TaskQueue(5, CompareCriteria.TITLE);
    queueTitle.enqueue(task1);
    queueTitle.enqueue(task2);

    expected[0] = task1;
    expected[1] = task2;

    for (int i = 0; i < queueTitle.size(); ++i) {
      if (!queueTitle.getHeapData()[i].equals(expected[i]))
        return false;
    }

    /*
     * if (!Arrays.deepEquals(queueTitle.getHeapData(), expected)) return false;
     * 
     */
    // comparing by time: task2, task1
    TaskQueue queueTime = new TaskQueue(5, CompareCriteria.TIME);
    queueTime.enqueue(task1);
    queueTime.enqueue(task2);


    expected[0] = task2;
    expected[1] = task1;

    for (int i = 0; i < queueTime.size(); ++i) {
      if (!queueTime.getHeapData()[i].equals(expected[i]))
        return false;
    }
    /*
     * if (!Arrays.deepEquals(queueTime.getHeapData(), expected)) return false;
     */
    // two children:
    Task task3 = new Task("ctask", "homework", 15, PriorityLevel.HIGH);
    queueLevel.enqueue(task3);
    queueTitle.enqueue(task3);
    queueTime.enqueue(task3);


    // comparing by title: atask, btask, ctask
    expected = new Task[3];
    expected[0] = task1;
    expected[1] = task2;
    expected[2] = task3;

    Task[] expected2 = new Task[3];

    for (int i = 0; i < queueTitle.size(); ++i) {
      if (!queueTitle.getHeapData()[i].equals(expected[i]))
        return false;
    }
    /*
     * if (!Arrays.deepEquals(queueTitle.getHeapData(), expected)) return false;
     */
    // comparing by time: task3, task1, task2
    expected[0] = task3;
    expected[1] = task1;
    expected[2] = task2;

    for (int i = 0; i < queueTime.size(); ++i) {
      if (!queueTime.getHeapData()[i].equals(expected[i]))
        return false;
    }

    /*
     * if (!Arrays.deepEquals(queueTime.getHeapData(), expected) &&
     * !Arrays.deepEquals(queueTime.getHeapData(), expected2)) return false;
     * 
     */

    // comparing by priority: task1, task2, task3
    expected[0] = task1;
    expected[1] = task2;
    expected[2] = task3;

    for (int i = 0; i < queueLevel.size(); ++i) {
      if (!queueLevel.getHeapData()[i].equals(expected[i]))
        return false;
    }

    /*
     * if (!Arrays.deepEquals(queueLevel.getHeapData(), expected) &&
     * !Arrays.deepEquals(queueLevel.getHeapData(), expected2)) return false;
     */



    // add another level: one root -> 2 children -> left child has one child
    Task task4 = new Task("dtask", "homework", 20, PriorityLevel.OPTIONAL);
    queueLevel.enqueue(task4);
    queueTitle.enqueue(task4);
    queueTime.enqueue(task4);

    // comparing by title: atask, btask, ctask, dtask
    expected = new Task[4];
    expected[0] = task1;
    expected[1] = task2;
    expected[2] = task3;
    expected[3] = task4;

    for (int i = 0; i < queueTitle.size(); ++i) {
      if (!queueTitle.getHeapData()[i].equals(expected[i]))
        return false;
    }

    /*
     * if (!Arrays.deepEquals(queueTitle.getHeapData(), expected)) return false;
     * 
     */
    // comparing by time: task4, task3, task2, task1
    expected[0] = task4;
    expected[1] = task3;
    expected[2] = task2;
    expected[3] = task1;

    for (int i = 0; i < queueTime.size(); ++i) {
      if (!queueTime.getHeapData()[i].equals(expected[i]))
        return false;
    }
    /*
     * if (!Arrays.deepEquals(queueTime.getHeapData(), expected)) return false;
     */

    // comparing by level: task1, task2, task3, task4

    expected2 = new Task[4];

    expected[0] = task1;
    expected[1] = task2;
    expected[2] = task3;
    expected[3] = task4;

    for (int i = 0; i < queueLevel.size(); ++i) {
      if (!queueLevel.getHeapData()[i].equals(expected[i]))
        return false;
    }
    /*
     * 
     * if (!Arrays.deepEquals(queueLevel.getHeapData(), expected) &&
     * !Arrays.deepEquals(queueLevel.getHeapData(), expected2)) return false;
     * 
     */

    // test adding to a full priority queue
    TaskQueue fullQueue = new TaskQueue(3, CompareCriteria.LEVEL);
    fullQueue.enqueue(task1);
    fullQueue.enqueue(task2);
    fullQueue.enqueue(task3);

    try {
      fullQueue.enqueue(task4);
      return false;
    } catch (IllegalStateException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
      System.out.println(e.getMessage());
    }


    return true;
  }

  /**
   * Tests correctness of TaskQueue dequeue() method implementation.
   * 
   * @return true if implementation passes all test cases, false if not
   */
  public static boolean testDequeue() {
    // empty queue
    TaskQueue empty = new TaskQueue(5, CompareCriteria.LEVEL);

    try {
      empty.dequeue();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
      System.out.println(e.getMessage());
    }

    Task task1 = new Task("atask", "homework", 5, PriorityLevel.URGENT);
    Task task2 = new Task("btask", "homework", 10, PriorityLevel.HIGH);
    Task task3 = new Task("ctask", "homework", 15, PriorityLevel.MEDIUM);
    Task task4 = new Task("dtask", "homework", 20, PriorityLevel.LOW);
    Task task5 = new Task("etask", "homework", 25, PriorityLevel.OPTIONAL);
    Task task6 = new Task("ftask", "homework", 30, PriorityLevel.OPTIONAL);

    // dequeue correctly by level - task1, task2, task3, task4
    TaskQueue queue = new TaskQueue(10, CompareCriteria.LEVEL);
    queue.enqueue(task1);

    if (queue.size() != 1)
      return false;

    if (queue.dequeue() != task1)
      return false;

    queue.enqueue(task1);
    queue.enqueue(task2);
    queue.enqueue(task3);
    queue.enqueue(task4);
    queue.enqueue(task5);
    queue.enqueue(task6);

    if (queue.size() != 6)
      return false;

    Task[] expected = new Task[] {task1, task2, task3, task4, task5, task6};

    // if queueLevel isn't as expected, return false;
    System.out.println("here");
    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i]))
        return false;
    }

    /*
     * if (!Arrays.deepEquals(queueLevel.getHeapData(), expected)) return false;
     */

    // dequeue should return task1

    if (!queue.dequeue().equals(task1))
      return false;

    if (queue.size() != 5)
      return false;


    // queueLevel - task2, task6, task3, task4, task5
    expected = new Task[] {task2, task6, task3, task4, task5};

    Task[] expected2 = new Task[] {task2, task4, task3, task6, task5};

    System.out.println("here");
    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i])
          && !queue.getHeapData()[i].equals(expected2[i]))
        return false;
    }
    // dequeue 5 times
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();

    System.out.println(queue.isEmpty());
    if (!queue.isEmpty())
      return false;

    /*
     * if (!Arrays.deepEquals(queueLevel.getHeapData(), expected)) return false;
     */

    // dequeue correctly by title - task1, task2, task3, task4
    queue.reprioritize(CompareCriteria.TITLE);
    queue.enqueue(task1);
    queue.enqueue(task2);
    queue.enqueue(task3);
    queue.enqueue(task4);
    queue.enqueue(task5);
    queue.enqueue(task6);

    if (queue.size() != 6)
      return false;

    expected = new Task[] {task1, task2, task3, task4, task5, task6};

    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i]))
        return false;
    }
    /*
     * if (!Arrays.deepEquals(queueTitle.getHeapData(), expected)) return false;
     */

    // dequeue should return task1
    expected = new Task[] {task2, task4, task3, task6, task5};
    expected2 = new Task[] {task4, task3, task2, task6, task5};

    if (!queue.dequeue().equals(task1))
      return false;

    if (queue.size() != 5)
      return false;


    for (int i = 0; i < queue.size(); ++i) {
      System.out.println(queue.getHeapData()[i] + " ");
    }


    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i])
          && !queue.getHeapData()[i].equals(expected2[i]))
        return false;
    }

    // dequeue rest - 5 more times

    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();

    // check if empty
    System.out.println(queue.isEmpty());
    if (!queue.isEmpty())
      return false;

    /*
     * if (!Arrays.deepEquals(queueTitle.getHeapData(), expected)) return false;
     */

    // dequeue correctly by time - task5, task4, task3, task2, task1

    queue.reprioritize(CompareCriteria.TIME);
    queue.enqueue(task1);
    queue.enqueue(task2);
    queue.enqueue(task3);
    queue.enqueue(task4);
    queue.enqueue(task5);
    queue.enqueue(task6);

    if (queue.size() != 6)
      return false;

    expected = new Task[] {task6, task4, task5, task1, task3, task2};


    for (int i = 0; i < queue.size(); ++i) {
      System.out.println(queue.getHeapData()[i] + " ");
    }


    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i]))

        return false;
    }
    /*
     * if (!Arrays.deepEquals(queueTime.getHeapData(), expected)) return false;
     */

    // dequeue should return task6
    expected = new Task[] {task5, task4, task2, task1, task3};
    expected2 = new Task[] {task3, task4, task2, task1, task5};

    if (!queue.dequeue().equals(task6))
      return false;


    if (queue.size() != 5)
      return false;

    System.out.println("here");
    for (int i = 0; i < queue.size(); ++i) {
      System.out.println(queue.getHeapData()[i] + " ");
    }

    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i])
          && !queue.getHeapData()[i].equals(expected2[i]))
        return false;
    }

    // dequeue 5 times
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();
    queue.dequeue();

    System.out.println(queue.isEmpty());
    if (!queue.isEmpty())
      return false;
    /*
     * if (!Arrays.deepEquals(queueTime.getHeapData(), expected)) return false;
     */
    return true;
  }

  /**
   * Tests correctness of TaskQueue peek() method implementation.
   * 
   * @return true if implementation passes all test cases, false if not
   */
  public static boolean testPeek() {
    // empty queue
    TaskQueue empty = new TaskQueue(5, CompareCriteria.LEVEL);

    try {
      empty.peekBest();
      return false;
    } catch (NoSuchElementException e) {
      if (e.getMessage() == null || e.getMessage().isBlank()) {
        return false;
      }
      System.out.println(e.getMessage());
    }


    // test by level
    // one-item
    TaskQueue queueLevel = new TaskQueue(5, CompareCriteria.LEVEL);
    Task task1 = new Task("atask", "homework", 5, PriorityLevel.URGENT);
    queueLevel.enqueue(task1);

    if (queueLevel.size() != 1)
      return false;

    if (!queueLevel.peekBest().equals(task1))
      return false;

    if (queueLevel.size() != 1)
      return false;

    // multiple items - should return task1
    Task task2 = new Task("btask", "homework", 10, PriorityLevel.HIGH);
    Task task3 = new Task("ctask", "homework", 15, PriorityLevel.MEDIUM);

    queueLevel.enqueue(task2);
    queueLevel.enqueue(task3);

    if (queueLevel.size() != 3)
      return false;

    if (!queueLevel.peekBest().equals(task1))
      return false;

    if (queueLevel.size() != 3)
      return false;

    // test by title
    // one-item
    TaskQueue queueTitle = new TaskQueue(5, CompareCriteria.TITLE);
    queueTitle.enqueue(task1);

    if (queueTitle.size() != 1)
      return false;

    if (!queueTitle.peekBest().equals(task1))
      return false;

    if (queueTitle.size() != 1)
      return false;

    // multiple items - should return task 1
    queueTitle.enqueue(task2);
    queueTitle.enqueue(task3);

    if (queueTitle.size() != 3)
      return false;

    if (!queueTitle.peekBest().equals(task1))
      return false;

    if (queueTitle.size() != 3)
      return false;

    // test by time
    TaskQueue queueTime = new TaskQueue(5, CompareCriteria.TIME);
    queueTime.enqueue(task1);

    if (queueTime.size() != 1)
      return false;

    if (!queueTime.peekBest().equals(task1))
      return false;

    if (queueTime.size() != 1)
      return false;

    // multiple items - should return task3
    queueTime.enqueue(task2);
    queueTime.enqueue(task3);

    if (queueTime.size() != 3)
      return false;

    if (!queueTime.peekBest().equals(task3))
      return false;

    if (queueTime.size() != 3)
      return false;

    return true;
  }

  /**
   * Tests correctness of TaskQueue reprioritize() method implementation.
   * 
   * @return true if implementation passes all test cases, false if not
   */
  public static boolean testReprioritize() {
    // start with correct queue prioritized by level
    TaskQueue queue = new TaskQueue(5, CompareCriteria.LEVEL);
    Task task1 = new Task("atask", "homework", 5, PriorityLevel.URGENT);
    Task task2 = new Task("btask", "homework", 10, PriorityLevel.MEDIUM);
    Task task3 = new Task("ctask", "homework", 15, PriorityLevel.HIGH);

    queue.enqueue(task1);
    queue.enqueue(task2);
    queue.enqueue(task3);

    // should be task1, task3, task2 or task1, task2, task3

    Task[] expected = {task1, task2, task3};

    /*
     * System.out.println("here"); if (!Arrays.deepEquals(queue.getHeapData(), expected) &&
     * !Arrays.deepEquals(queue.getHeapData(), expected2)) return false;
     */
    // reprioritize to title
    queue.reprioritize(CompareCriteria.TITLE);

    // should be task1, task2, task3
    expected = new Task[] {task1, task2, task3};

    System.out.println("here");

    if (queue.size() != 3)
      return false;

    System.out.println("here");

    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i]))
        return false;
    }

    System.out.println("here");

    /*
     * if (!Arrays.deepEquals(queue.getHeapData(), expected)) return false;
     */

    // reprioritize to time
    queue.reprioritize(CompareCriteria.TIME);

    // should be task3, task2, task1
    expected = new Task[] {task3, task2, task1};

    System.out.println("here");

    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i]))
        return false;
    }

    /*
     * if (!Arrays.deepEquals(queue.getHeapData(), expected)) return false;
     */

    // back to level
    // back to task1, task3, task2 or task1, task2, task3
    queue.reprioritize(CompareCriteria.LEVEL);

    expected = new Task[] {task1, task2, task3};

    System.out.println("here");

    for (int i = 0; i < queue.size(); ++i) {
      if (!queue.getHeapData()[i].equals(expected[i]))
        return false;
    }

    /*
     * if (!Arrays.deepEquals(queue.getHeapData(), expected)) return false;
     */
    return true;
  }
}
