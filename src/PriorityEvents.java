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
 * This class implements a single priority queue of events for the CS300 P10 program Priority Events
 * using a min-heap structure maintained in an array
 */
public class PriorityEvents {

  /**
   * An array which contains all the completed Events that have passed through this priority queue;
   * this array has double the capacity of heapData.
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
   * maintained in valid heap order with respect to either Event comparisons or description, per the
   * value of the sortAlphabetically field of this object
   */
  private Event[] heapData;

  /**
   * Indicates whether the events in this priority queue should be arranged in heap order with
   * respect to their timestamps (using Event.compareTo()) or alphabetically by their descriptions
   */
  private static boolean sortAlphabetically = false;

  /**
   * Creates a new priority queue of events, initializing all data fields accordingly
   *
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
  }

  /**
   * Creates a valid min-heap from the provided oversize array of Events For full credit, this
   * method must use the heapify algorithm to create its priority queue.
   *
   * Note: This method is closely related to the learning objectives of the assignment, and so we'll
   * pay special attention to it during manual grading. Be sure to leave comments explaining each
   * algorithmic step you use!
   *
   * @param events the Events to be prioritized
   * @param size   the number of Events in the provided events array, assumed valid
   * @throws IllegalArgumentException if any Event in events has already been completed
   */
  public PriorityEvents(Event[] events, int size) throws IllegalArgumentException {
    for (Event event : events) {
      if (event.isComplete()) {
        throw new IllegalArgumentException();
      }
    }
    this.heapData = Arrays.copyOf(events, events.length);
    this.size = size;
    this.completed = new Event[events.length * 2];
    this.completedSize = 0;

    // Heapify the array to maintain the order
    heapify((size - 2) / 2);

  }

  /**
   * Reports whether this priority queue is maintained according to Event description or timestamp
   *
   * @return true if this priority queue is ordered by description, false if it is ordered by
   * timestamp
   */
  public static boolean isSortedAlphabetically() {
    return sortAlphabetically;
  }

  /**
   * Sets all priority queues to be sorted alphabetically.
   */
  public static void sortAlphabetically() {
    PriorityEvents.sortAlphabetically = true;
  }

  /**
   * Sets all priority queues to be sorted chronoloically (i.e., not alphabetically).
   */
  public static void sortChronologically() {
    PriorityEvents.sortAlphabetically = false;

  }

  /**
   * Reports whether this priority queue currently contains any Events, not counting those in the
   * completed array
   *
   * @return true if this priority queue contains any Events, false otherwise
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Accesses the number of events currently in this priority queue, not counting those in the
   * completed array
   *
   * @return the number of Events in this priority queue
   */
  public int size() {
    return size;
  }

  /**
   * Returns a deep copy of the completed array, and empties out the array
   *
   * @return a deep copy of the contents of the completed array before clearing it out
   */
  public Event[] clearCompletedEvents() {
    Event[] copy = Arrays.copyOf(completed, completedSize);
    completedSize = 0;

    Arrays.fill(completed, null);
    return copy;
  }

  /**
   * For testing purposes; returns a deep copy of the completed array WITHOUT clearing the array
   *
   * @return a deep copy of the contents of the completed array
   */
  protected Event[] getCompletedEvents() {
    return Arrays.copyOf(completed, completedSize);
  }

  /**
   * Accesses the next (according to priority) event without removing it from the queue
   *
   * @return a reference to the next (upcoming or alphabetical) event in the queue
   * @throws NoSuchElementException if the queue is currently empty
   */
  public Event peekNextEvent() throws NoSuchElementException {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return heapData[0];
  }

  /**
   * For testing purposes; accesses a deep copy of the heapData array. It is not necessary to create
   * deep copies of the Events contained in that array.
   *
   * @return a deep copy of the heapData array
   */
  protected Event[] getHeapData() {
    return Arrays.copyOf(heapData, size);
  }

  /**
   * Inserts a new Event into the priority queue in the correct location in O(log N) time -> MUST
   * call one of the percolate helper methods CITE:
   * https://www.geeksforgeeks.org/priority-queue-using-binary-heap/ used this site's diagrams to
   * help write this method
   *
   * @param e the new Event to be added
   * @throws IllegalStateException    if the queue is full
   * @throws IllegalArgumentException if the event is null or the Event is completed
   */
  public void addEvent(Event e) throws IllegalStateException, IllegalArgumentException {
    if (size == heapData.length) {
      throw new IllegalStateException();
    }

    if (e == null || e.isComplete()) {
      throw new IllegalArgumentException();
    }

    // Add the element at the end of the heap and percolate it up to restore the correct ordering
    heapData[size] = e;
    size++;
    percolateUp(size - 1);
  }

  /**
   * Helper method; MUST BE IMPLEMENTED RECURSIVELY
   *
   * Percolates the value at index i of the heapData array toward index 0 according to min-heap
   * protocols, comparing either Event timestamps or descriptions depending on the value of the
   * sortAlphabetically field
   *
   * Note: This method is closely related to the learning objectives of the assignment, and so we'll
   * pay special attention to it during manual grading. Be sure to leave comments explaining each
   * algorithmic step you use!
   *
   * @param i the index of the Event in heapData to be percolated
   */
  protected void percolateUp(int i) {
    // Base case: if the root is reached, stop the percolating.
    // The percolating will stop also if the element is >= its parent
    if (i == 0) {
      return;
    }

    // Gets the parent index using private helper method
    int parentIndex = parent(i);

    // Boolean to store result of compareTo
    boolean swap;

    if (PriorityEvents.isSortedAlphabetically()) {
      swap = heapData[i].getDescription().compareTo(heapData[parentIndex].getDescription()) < 0;
    } else {
      swap = heapData[i].compareTo(heapData[parentIndex]) < 0;
    }

    // Recursive case: continue percolating up
    // Swaps the index with the parent and then continues with the percolation with i = parentIndex
    if (swap) {
      swap(i, parentIndex);
      percolateUp(parentIndex);
    }
  }

  /**
   * Helper method; MUST BE IMPLEMENTED RECURSIVELY
   *
   * Percolates the value at index i of the heapData array away from index 0 according to min-heap
   * protocols, comparing either Event timestamps or descriptions depending on the value of the
   * sortAlphabetically field
   *
   * Note: This method is closely related to the learning objectives of the assignment, and so we'll
   * pay special attention to it during manual grading. Be sure to leave comments explaining each
   * algorithmic step you use!
   *
   * @param i the index of the Event in heapData to be percolated
   */
  protected void percolateDown(int i) {
    // Values of index i's children and the smallest child, which is initialized to i for a min-heap
    int leftChild = left(i);
    int rightChild = right(i);
    int smallest = i;

    // Check if there is a left child
    if (leftChild < size) {
      boolean leftIsSmaller;
      // Compares the left child to the current smallest element
      if (PriorityEvents.isSortedAlphabetically()) {
        leftIsSmaller =
            heapData[leftChild].getDescription().compareTo(heapData[smallest].getDescription()) < 0;
      } else {
        leftIsSmaller = heapData[leftChild].compareTo(heapData[smallest]) < 0;
      }

      // Updates the smallest index if the left child is smaller
      if (leftIsSmaller) {
        smallest = leftChild;
      }
    }

    // Check if there is a right child
    if (rightChild < size) {
      boolean rightIsSmaller;
      // Compares the right child to the current smallest element (could be the left child)
      if (PriorityEvents.isSortedAlphabetically()) {
        rightIsSmaller = heapData[rightChild].getDescription()
            .compareTo(heapData[smallest].getDescription()) < 0;
      } else {
        rightIsSmaller = heapData[rightChild].compareTo(heapData[smallest]) < 0;
      }

      // Updates the smallest index if the right child is smaller
      if (rightIsSmaller) {
        smallest = rightChild;
      }
    }

    // Recursive case: If the smallest element is not the current element, swap and percolate down
    if (smallest != i) {
      swap(i, smallest);
      percolateDown(smallest);
    }
  }

  /**
   * Removes the next (according to priority) Event from the priority queue, marks it as complete,
   * and appends it to the completed array. -> MUST call one of the percolate helper methods CITE:
   * https://www.geeksforgeeks.org/priority-queue-using-binary-heap/ used this site's diagrams to
   * help write this method
   *
   * @throws IllegalStateException if the queue is empty or the completed array is full
   */
  public void completeEvent() throws IllegalStateException {
    if (isEmpty() || completedSize == completed.length) {
      throw new IllegalStateException();
    }

    // Add root of heap (next event) to completed and mark as complete
    Event nextEvent = heapData[0];
    nextEvent.markAsComplete();
    completed[completedSize] = nextEvent;
    completedSize++;

    // Move last element in the heap to the root and percolate down
    heapData[0] = heapData[size - 1];
    heapData[size - 1] = null;
    size--;
    percolateDown(0);
  }

  /**
   * Required helper method for toString, which creates a deep copy of the current queue
   *
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
   *
   * @return a String representation of all events in sorted order
   */
  @Override
  public String toString() {
    PriorityEvents copy = deepCopy();
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < copy.size; i++) {
      result.append(copy.heapData[i].toString()).append("\n");
    }

    return result.toString().trim();
  }

  /**
   * Heap definition of a node's parent
   *
   * @param i index of node
   * @return the index of the parent
   */
  private int parent(int i) {
    return (i - 1) / 2;
  }

  /**
   * Heap definition of a node's left child
   *
   * @param i index of node
   * @return the index of the left child
   */
  private int left(int i) {
    return 2 * i + 1;
  }

  /**
   * Heap definition of a node's left child
   *
   * @param i index of node
   * @return the index of the right child
   */
  private int right(int i) {
    return 2 * i + 2;
  }

  /**
   * Swaps two elements in the heap array at the given indices.
   *
   * @param i index of the first element
   * @param j index of the second element
   */
  private void swap(int i, int j) {
    Event temp = heapData[i];
    heapData[i] = heapData[j];
    heapData[j] = temp;
  }

  /**
   * Heapifies an array starting at the index of the last internal node
   *
   * @param i the index of the last INTERNAL node
   */
  private void heapify(int i) {
    // Base case: if index is the root
    if (i < 0) {
      return;
    }
    percolateDown(i);
    // Recursive case, decrement index and heapify again
    heapify(i - 1);
  }
}
