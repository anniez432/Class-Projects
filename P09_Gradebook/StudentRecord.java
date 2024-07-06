//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P09 Binary Gradebook StudentRecord File
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
// Online Sources: P09 Specification and JavaDocs
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class models student course records.
 */
public class StudentRecord implements Comparable<StudentRecord> {

  // data fields
  public final String email; // student's email address
  public final String name; // student's name
  private double grade; // student's grade

  // constructors

  public StudentRecord(String name, String email, double grade) {
    // throw IllegalArgumentExceptions if requirements aren't met
    if (name == null || name.isBlank() || email == null || email.isBlank()) {
      throw new IllegalArgumentException("Invalid name or email.");
    }

    if (grade < 0.0 || grade > 100.0)
      throw new IllegalArgumentException("Invalid grade.");

    // assigns determined values to data fields
    this.email = email;
    this.name = name;
    this.grade = grade;
  }

  // methods

  /**
   * Returns the student's current grade.
   * 
   * @return this.grade The current grade
   */
  public double getGrade() {
    return this.grade;
  }

  /**
   * Updates the student's current grade.
   * 
   * @param grade The new grade value
   */
  public void setGrade(double grade) {
    this.grade = grade;
  }

  /**
   * Returns a String representation of this StudentRecord.
   * 
   * @return The String in the format: name (email): grade
   */
  public String toString() {
    return this.name + " (" + this.email + "): " + this.grade;
  }

  /**
   * Compares this StudentRecord to other StudentRecord passed as input. Compared with respect to
   * the alphabetical order of the students' emails.
   * 
   * @param other The other StudentRecord to be compared with.
   * @return 0 if the emails are equal, negative value if this email is alphabetically less than the
   *         other email, and a positive value if this email is alphabetically greater.
   */
  @Override
  public int compareTo(StudentRecord other) {
    return this.email.compareTo(other.email);
  }

  /**
   * Returns true if the given object's email matches the email of this StudentRecord.
   * 
   * @param o The Object to be compared with
   * @return true if the two have equal emails, false if not.
   */
  public boolean equals(Object o) {

    if (o instanceof StudentRecord) {
      return ((StudentRecord) o).email.equals(this.email);
    }

    return false;
  }


}

