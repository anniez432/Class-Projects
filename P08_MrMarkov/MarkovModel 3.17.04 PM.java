//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P08 Markov Model
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
// Persons: A peer mentor helped me when the Gradescope returned
// a checker error.
// Online Sources: The P08 Specification and JavaDocs
//
///////////////////////////////////////////////////////////////////////////////
import java.util.HashMap;

/**
 * A class that represents a Markov model for generating random text based on a sample text. The
 * model uses a sliding window approach to analyze the occurrence of characters following a sequence
 * of characters of length k.
 */
public class MarkovModel {

  // data fields
  // AA map of substrings of length windowWidth to stacks
  // continuing the observed characters which follow that substring
  private HashMap<String, MyStack<Character>> model;

  // The current windowWidth number of characters that the model will
  // use to predict the next character. Should always be maintained at
  // length windowWidth using methods from MyQueue.
  private MyQueue<Character> currentQueue;

  // # of characters to consider in a substring
  private int windowWidth;

  // whether to shuffle the stacks during text generation
  private boolean shuffleStacks;

  // constructor
  public MarkovModel(int k, boolean shuffle) {
    this.model = new HashMap<>();
    this.windowWidth = k;
    this.shuffleStacks = shuffle;
  }

  // methods

  /**
   * Reads in the provided text and builds a model, which maps each k-length substring of the text
   * to a stack containing all of the characters that follow that substring in the text.
   * 
   * @param text The text to be processed
   */
  public void processText(String text) {

    for (int i = 0; i < text.length() - windowWidth; ++i) {
      String key = text.substring(i, i + windowWidth);
      char nextChar = text.charAt(i + windowWidth);
      // Updates OR creates a new stack for the
      // given substring and adds next char to it.
      model.computeIfAbsent(key, k -> new MyStack<>()).push(nextChar);

    }
  }

  /**
   * Initializes the current queue with the first k-letter substring from the text, setting the
   * initial state for text generation.
   * 
   * @param text The text from which to derive the initial queue state
   */
  public void initializeQueue(String text) {
    // initializes the queue
    currentQueue = new MyQueue<>();

    // adds the char from the text until the desired length is added
    for (int i = 0; i < windowWidth; ++i) {
      currentQueue.enqueue(text.charAt(i));
    }

  }

  /**
   * Generates text of a specified length based on the model
   * 
   * @param length The desired length of the generated text
   * @param text   The text to use for re-seeding if necessary
   * 
   * @return output The generated text
   */
  public String generateText(int length, String text) {

    // creates the output string
    String output = "";

    // adds the state of the current queue to the output
    output += currentQueue.toString();


    while (output.length() < length) {

      // get current queue state and see if HashMap has entry for it
      // if so, add next character from stack to output string

      // checks if stack at current queue state is empty and exists
      if (model.containsKey(currentQueue.toString())
          && !model.get(currentQueue.toString()).isEmpty()) {

        // determine next character from its stack
        char nextChar = model.get(currentQueue.toString()).peek();

        // add to output string
        output += nextChar;

        // shuffle stack corresponding to currentQueue state if true
        if (shuffleStacks) {
          model.get(currentQueue.toString()).shuffle();
        }

        // add new char into current queue
        currentQueue.enqueue(nextChar);

        // maintain the size of the current queue as length we want
        currentQueue.maintainSize(windowWidth);

      } else {
        // re-seed with text
        processText(text);
        initializeQueue(text);
        // add new line
        output += "/n";
      }
    }

    return output;
  }
}
