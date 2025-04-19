//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P10 Priority Events
// Course:   CS 300 Spring 2025
//
// Author:   Erik Larson
// Email:    emlarson23@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         N/A (did not use or reference Hobbes' code in lecture)
// Online Sources:  https://www.geeksforgeeks.org/priority-queue-using-binary-heap/ used the
// diagrams in this website to visualize insert and remove (addEvent and completeEvent). Did not
// use any code within the G4G website.
//
///////////////////////////////////////////////////////////////////////////////


import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * This class implements a single priority queue of events for the CS300 P10 program Priority
 * Events using a min-heap structure maintained in an array
 */
public class PriorityEvents {

  /**
   * An array which contains all the completed Events that have passed through this priority
   * queue; this array has double the capacity of heapData.
   */
  private Event[] completed;

  /**
   * The number of events currently stored in the completed array
   */
  private int completedSize;

  /**
   * The number of events currently stored in the heapData array
   */
  private int size;

  /**
   * An array which maintains the heap structure for our priority queue; data in this array MUST be
   * maintained in valid heap order with respect to either Event comparisons or description, per
   * the value of the sortAlphabetically field of this object
   */
  private Event[] heapData;

  /**
   * Indicates whether the events in this priority queue should be arranged in heap order with
   * respect to their timestamps (using Event.compareTo()) or alphabetically by their descriptions
   */
  private static boolean sortAlphabetically;

  /**
   * Creates a new priority queue of events, initializing all data fields accordingly
   * @param capacity the capacity of the queue to be created; must be > 0
   * @throws IllegalArgumentException if a capacity of 0 or less is provided
   */
  public PriorityEvents(int capacity) throws IllegalArgumentException {
    if (capacity <= 0) {
      throw new IllegalArgumentException();
    }
    heapData = new Event[capacity];
    this.size = 0;
    completed = new Event[capacity * 2];
    sortAlphabetically = false;
  }

  /**
   * Creates a valid min-heap from the provided oversize array of Events
   * For full credit, this method must use the heapify algorithm to create its priority queue.
   *
   * Note: This method is closely related to the learning objectives of the assignment, and so
   * we'll pay special attention to it during manual grading. Be sure to leave comments explaining
   * each algorithmic step you use!
   * @param events the Events to be prioritized
   * @param size the number of Events in the provided events array, assumed valid
   * @throws IllegalArgumentException if any Event in events has already been completed
   */
  public PriorityEvents(Event[] events, int size) throws IllegalArgumentException {

  }

  /**
   * Reports whether this priority queue is maintained according to Event description or timestamp
   * @return true if this priority queue is ordered by description, false if it is ordered by timestamp
   */
  public static boolean isSortedAlphabetically() {
    return sortAlphabetically;
  }

  /**
   * Sets all priority queues to be sorted alphabetically.
   */
  public static void sortAlphabetically() {
    sortAlphabetically = true;
  }

  /**
   * Sets all priority queues to be sorted chronoloically (i.e., not alphabetically).
   */
  public static void sortChronologically() {
    sortAlphabetically = false;
  }

  /**
   * Reports whether this priority queue currently contains any Events, not counting those in the
   * completed array
   * @return true if this priority queue contains any Events, false otherwise
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Accesses the number of events currently in this priority queue, not counting those in the
   * completed array
   * @return the number of Events in this priority queue
   */
  public int size() {
    return heapData.length;
  }

  /**
   * Returns a deep copy of the completed array, and empties out the array
   * @return a deep copy of the contents of the completed array before clearing it out
   */
  public Event[] clearCompletedEvents() {
    Event[] copy = Arrays.copyOf(completed, completedSize);
    completedSize = 0;

    for (int i = 0; i < completed.length; i++) {
      completed[i] = null;
    }
    return copy;
  }

  /**
   * For testing purposes; returns a deep copy of the completed array WITHOUT clearing the array
   * @return a deep copy of the contents of the completed array
   */
  protected Event[] getCompletedEvents() {
    return Arrays.copyOf(completed, completedSize);
  }

  /**
   * Accesses the next (according to priority) event without removing it from the queue
   * @return a reference to the next (upcoming or alphabetical) event in the queue
   * @throws NoSuchElementException if the queue is currently empty
   */
  public Event peekNextEvent() throws NoSuchElementException {
    return new Event("", -1, -1, -1);
  }

  /**
   * For testing purposes; accesses a deep copy of the heapData array. It is not necessary to
   * create deep copies of the Events contained in that array.
   * @return a deep copy of the heapData array
   */
  protected Event[] getHeapData() {
    Event[] copiedHeapData = new Event[size];
    for (int i = 0; i < size; i++) {
      copiedHeapData[i] = heapData[i];
    }
    return copiedHeapData;
  }

  /**
   * Inserts a new Event into the priority queue in the correct location in O(log N) time -> MUST
   * call one of the percolate helper methods
   * CITE:  https://www.geeksforgeeks.org/priority-queue-using-binary-heap/ used this site's
   * diagrams to help write this method
   * @param e the new Event to be added
   * @throws IllegalStateException if the queue is full
   * @throws IllegalArgumentException if the event is null or the Event is completed
   */
  public void addEvent(Event e) throws IllegalStateException, IllegalArgumentException {
    //TODO
  }

  /**
   * Helper method; MUST BE IMPLEMENTED RECURSIVELY
   *
   * Percolates the value at index i of the heapData array toward index 0 according to min-heap
   * protocols, comparing either Event timestamps or descriptions depending on the value of the
   * sortAlphabetically field
   *
   * Note: This method is closely related to the learning objectives of the assignment, and so
   * we'll pay special attention to it during manual grading. Be sure to leave comments explaining
   * each algorithmic step you use!
   * @param i the index of the Event in heapData to be percolated
   */
  protected void percolateUp(int i) {
    //TODO
  }

  /**
   * Helper method; MUST BE IMPLEMENTED RECURSIVELY
   *
   * Percolates the value at index i of the heapData array away from index 0 according to min-heap
   * protocols, comparing either Event timestamps or descriptions depending on the value of the
   * sortAlphabetically field
   *
   * Note: This method is closely related to the learning objectives of the assignment, and so
   * we'll pay special attention to it during manual grading. Be sure to leave comments explaining
   * each algorithmic step you use!
   * @param i the index of the Event in heapData to be percolated
   */
  protected void percolateDown(int i) {
    //TODO
  }

  /**
   * Removes the next (according to priority) Event from the priority queue, marks it as complete,
   * and appends it to the completed array. -> MUST call one of the percolate helper methods
   * CITE:  https://www.geeksforgeeks.org/priority-queue-using-binary-heap/ used this site's
   * diagrams to help write this method
   * @throws IllegalStateException if the queue is empty or the completed array is full
   */
  public void completeEvent() throws IllegalStateException{
    //TODO
  }

  /**
   * Required helper method for toString, which creates a deep copy of the current queue
   * @return a new PriorityEvents queue with a deep copy of the heapData and completed arrays and
   * their corresponding sizes
   */
  protected PriorityEvents deepCopy() {
    PriorityEvents deepCopy = new PriorityEvents(this.heapData.length);

    // Copy sizes
    deepCopy.size = this.size;
    deepCopy.completedSize = this.completedSize;

    // Copy references
    deepCopy.heapData = Arrays.copyOf(this.heapData, this.heapData.length);
    deepCopy.completed = Arrays.copyOf(this.completed, this.completed.length);

    return deepCopy;
  }

  /**
   * Creates a String representation of all events in the queue in sorted order, one on each line
   * (no trailing newline). Must NOT modify the queue - use a deep copy of the queue instead.
   * @return a String representation of all events in sorted order
   */
  @Override
  public String toString() {
    return ""; //TODO
  }

  /**
   * Heap definition of a node's parent
   * @param i index of node
   * @return the index of the parent
   */
  private int parent(int i) { return (i-1)/2; }

  /**
   * Heap definition of a node's left child
   * @param i index of node
   * @return the index of the left child
   */
  private int left(int i) { return 2*i + 1; }

  /**
   * Heap definition of a node's left child
   * @param i index of node
   * @return the index of the right child
   */
  private int right(int i) { return 2*i + 2; }
}
