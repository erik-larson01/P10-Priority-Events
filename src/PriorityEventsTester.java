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


import java.util.NoSuchElementException;

/**
 * Tester class for the CS300 P10 Priority Events project. You may add tester methods to this class
 * but they must be declared private; the existing public tester methods may use the output of these
 * private testers to help determine their output (as with testAddEvent or testCompleteEvent).
 */
public class PriorityEventsTester {
  
  /**
   * This method runs all sub-testers related to testing adding an Event to the priority queue.
   * You may wish to add additional output for clarity, or additional private tester methods related
   * to adding Events.
   * @return true if all tests relating to adding an Event to a priority queue pass; false otherwise
   */
  public static boolean testAddEvent() {
    boolean testAdd = true;
    testAdd &= testAddEventChronological();
    testAdd &= testAddEventAlphabetical();
    return testAdd;
  }
  
  private static boolean testAddEventChronological() {
    { // Test basic functionality for constructor with array parameter
      try {
        PriorityEvents.sortChronologically();
        Event[] events = new Event[5];
        events[0] = new Event("Event1", 10, 10, 0);
        events[1] = new Event("Event2", 9, 12, 0);
        events[2] = new Event("Event3", 11, 11, 0);
        events[3] = new Event("Event4", 8, 5, 0);
        events[4] = new Event("Event5", 10, 5, 0);
        PriorityEvents queue = new PriorityEvents(events, 5);


        // Verify the heap is correctly formed (minimum time should be at the root)
        if (queue.isEmpty() || !queue.peekNextEvent().equals(events[3])) {
          return false;
        }

        // Check if heap property is maintained
        Event[] heapData = queue.getHeapData();
        if (queue.isEmpty() || !isValidHeapChronologically(heapData)) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Test with array with completed events
      try {
        PriorityEvents.sortChronologically();
        Event[] events = new Event[5];
        events[0] = new Event("Event1", 10, 10, 0);
        events[1] = new Event("Event2", 9, 12, 0);
        events[0].markAsComplete();


        // Check that peekNextEvent throws NoSuchElementException
        try {
          PriorityEvents queue = new PriorityEvents(events, 5);
          return false; // Should have thrown exception
        } catch (IllegalArgumentException e) {
          // Expected exception
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Tests basic add functionality of the capacity constructor
      try {
        PriorityEvents.sortChronologically();
        PriorityEvents events = new PriorityEvents(5);

        Event event1 = new Event("Event1", 10, 9, 0);
        Event event2 = new Event("Event2", 10, 10, 0);
        Event event3 = new Event("Event3", 9, 10, 0);

        // Add events and verify the heap property
        events.addEvent(event1);
        if (events.isEmpty() || !events.peekNextEvent().equals(event1)) {
          return false;
        }

        events.addEvent(event2);
        if (events.peekNextEvent() != event1) {
          return false;
        }

        events.addEvent(event3);
        if (events.peekNextEvent() != event3) {
          return false;
        }

        // Verify heap data and size
        Event[] heapData = events.getHeapData();
        if (heapData[0] != event3 || heapData.length != 3) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Tests full queue throw
      try {
        PriorityEvents events = new PriorityEvents(2);

        Event event1 = new Event("Event1", 10, 9, 0);
        Event event2 = new Event("Event2", 10, 10, 0);
        Event event3 = new Event("Event3", 9, 10, 0);

        events.addEvent(event1);
        events.addEvent(event2);

        try {
          events.addEvent(event3);
          return false; // Should have thrown exception
        } catch (IllegalStateException e) {
          // Expected exception
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Tests null event
      try {
        PriorityEvents events = new PriorityEvents(5);

        try {
          events.addEvent(null);
          return false; // Should have thrown exception
        } catch (IllegalArgumentException e) {
          // Expected exception
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Tests completed event
      try {
        PriorityEvents events = new PriorityEvents(5);

        Event event = new Event("CompletedEvent", 10, 9, 0);
        event.markAsComplete();

        try {
          events.addEvent(event);
          return false; // Should have thrown exception
        } catch (IllegalArgumentException e) {
          // Expected exception
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Tests percolate up with multiple levels
      try {
        PriorityEvents events = new PriorityEvents(10);

        Event event1 = new Event("Event1", 10, 10, 0);
        Event event2 = new Event("Event2", 11, 10, 0);
        Event event3 = new Event("Event3", 12, 10, 0);
        Event event4 = new Event("Event4", 13, 10, 0);
        Event event5 = new Event("Event5", 9, 10, 0);

        events.addEvent(event1);
        events.addEvent(event2);
        events.addEvent(event3);
        events.addEvent(event4);
        events.addEvent(event5); // Should percolate to the root

        if (events.peekNextEvent() != event5) {
          return false;
        }

        // Test heap size
        Event[] heapData = events.getHeapData();
        if (heapData.length != 5) {
          return false;
        }

        // Check if heap property is maintained across all nodes
        if (heapData[0] != event5) {
          return false;
        }

        // Check if parent/child relationships are maintained
        if (events.isEmpty() || !isValidHeapChronologically(heapData)) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    return true; // All tests passed
  }
  
  private static boolean testAddEventAlphabetical() {
    { // Test basic functionality for constructor with array parameter
      try {
        PriorityEvents.sortAlphabetically();

        // Create an array of events
        Event[] events = new Event[5];
        events[0] = new Event("Z Event", 10, 10, 0);
        events[1] = new Event("A Event", 11, 3, 0);
        events[2] = new Event("B Event", 9, 15, 0);
        events[3] = new Event("C Event", 12, 5, 0);
        events[4] = new Event("D Event", 8, 5, 0);

        // Create priority queue using the constructor
        PriorityEvents queue = new PriorityEvents(events, 5);

        // Verify the heap is correctly formed
        if (!queue.peekNextEvent().equals(events[1])) {
          return false;
        }

        // Check if heap property is maintained
        Event[] heapData = queue.getHeapData();
        if (queue.isEmpty() || !isValidHeapAlphabetically(heapData)) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Tests basic add functionality with alphabetical ordering
      try {
        PriorityEvents.sortAlphabetically();
        PriorityEvents events = new PriorityEvents(5);

        // Create events with alphabetically different descriptions
        Event eventB = new Event("B Event", 10, 9, 0);
        Event eventC = new Event("C Event", 9, 10, 0);
        Event eventA = new Event("A Event", 11, 10, 0);

        // Add events and verify the heap property with alphabetical ordering this time
        events.addEvent(eventB);
        if (events.isEmpty() || events.peekNextEvent() != eventB) {
          return false;
        }

        events.addEvent(eventC);
        if (events.peekNextEvent() != eventB) {
          return false;
        }

        events.addEvent(eventA);
        if (events.peekNextEvent() != eventA) {
          return false;
        }

        // Verify ordering
        Event[] heapData = events.getHeapData();
        if (heapData[0] != eventA || heapData.length != 3) {
          return false;
        }

        // Check if parent/child relationships are maintained
        if (!isValidHeapAlphabetically(heapData)) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Tests percolate up with multiple levels alphabetically
      try {
        PriorityEvents.sortAlphabetically();
        PriorityEvents events = new PriorityEvents(10);

        Event eventE = new Event("E Event", 10, 10, 0);
        Event eventD = new Event("D Event", 11, 10, 0);
        Event eventC = new Event("C Event", 12, 10, 0);
        Event eventB = new Event("B Event", 13, 10, 0);
        Event eventA = new Event("A Event", 14, 10, 0);

        events.addEvent(eventE);
        events.addEvent(eventD);
        events.addEvent(eventC);
        events.addEvent(eventB);
        events.addEvent(eventA); // Should percolate to the root

        if (events.peekNextEvent() != eventA) {
          return false;
        }

        // Verify heap size
        Event[] heapData = events.getHeapData();
        if (heapData.length != 5) {
          return false;
        }

        // Verify root is eventA
        if (heapData[0] != eventA) {
          return false;
        }

        // Check if parent/child relationships are maintained
        if (!isValidHeapAlphabetically(heapData)) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }

    return true; // All tests passed
  }
  
  /**
   * This method runs all sub-testers related to testing marking an Event in the priority queue as
   * completed. You may wish to add additional output for clarity, or additional private tester 
   * methods related to marking Events as completed.
   * @return true if all tests relating to removing an Event from a priority queue pass; false 
   * otherwise
   */
  public static boolean testCompleteEvent() {
    boolean testComplete = true;
    testComplete &= testCompleteEventChronological();
    testComplete &= testCompleteEventAlphabetical();
    return testComplete;
  }
  
  private static boolean testCompleteEventChronological() {
    { // Test basic functionality for completeEvent
      try {
        PriorityEvents.sortChronologically();
        Event[] events = new Event[5];
        events[0] = new Event("Event1", 10, 10, 0);
        events[1] = new Event("Event2", 9, 12, 0);
        events[2] = new Event("Event3", 11, 11, 0);
        events[3] = new Event("Event4", 8, 5, 0);
        events[4] = new Event("Event5", 10, 5, 0);
        PriorityEvents queue = new PriorityEvents(events, 5);

        // Verify initial state (minimum time should be at the root)
        if (queue.isEmpty() || !queue.peekNextEvent().equals(events[3])) {
          return false;
        }

        // Complete the first event
        queue.completeEvent();

        // Verify that the next event is now at the root
        if (queue.peekNextEvent().equals(events[3])) {
          return false;
        }

        // Verify that the completed event is in the completed array
        Event[] completed = queue.getCompletedEvents();
        if (completed.length != 1 || !completed[0].equals(events[3])) {
          return false;
        }

        // Verify the size has decreased
        Event[] heapData = queue.getHeapData();
        if (heapData.length != 4) {
          return false;
        }

        queue.completeEvent();
        // Checks the formatting of the heap
        if (!isValidHeapChronologically(heapData)) {
          return false;
        }
        queue.completeEvent();
        // Checks the formatting of the heap
        if (!isValidHeapChronologically(heapData)) {
          return false;
        }
        queue.completeEvent();

        int size = queue.size();
        if (queue.size() != 1) {
          return false;
        }

        // Checks the formatting of the heap
        if (!isValidHeapChronologically(heapData)) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }

    { // Test completeEvent on empty queue
      try {
        PriorityEvents.sortChronologically();
        PriorityEvents queue = new PriorityEvents(5);

        try {
          queue.completeEvent();
          return false; // Should have thrown exception
        } catch (IllegalStateException e) {
          // Expected exception
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Test completeEvent with full completed array
      try {
        PriorityEvents.sortChronologically();
        Event[] events = new Event[2];
        events[0] = new Event("Event1", 10, 10, 0);
        events[1] = new Event("Event2", 9, 12, 0);

        // Create a queue with a small completed array
        PriorityEvents queue = new PriorityEvents(events, 2);

        for (int i = 0; i < events.length; i++) {
          queue.completeEvent();
        }

        try {
          queue.completeEvent();
          return false; // Should have thrown exception
        } catch (IllegalStateException e) {
          // Expected exception
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Test completeEvent with array constructor
      try {
        PriorityEvents.sortChronologically();

        Event[] events = new Event[2];
        events[0] = new Event("Event1", 10, 10, 0);
        events[1] = new Event("Event2", 9, 12, 0);

        PriorityEvents queue = new PriorityEvents(events, 2);
        Event[] heapData = queue.getHeapData();

        // Checks heap structure
        if (!isValidHeapChronologically(heapData)) {
          return false;
        }

        queue.completeEvent();
        // Checks the formatting of the heap
        if (!isValidHeapChronologically(heapData)) {
          return false;
        }
        queue.completeEvent();
        // Checks the formatting of the heap
        if (!isValidHeapChronologically(heapData)) {
          return false;
        }
        Event[] completed = queue.getCompletedEvents();
        if (completed.length != events.length) {
          return false;
        }
        // Queue should now be empty
        if (!queue.isEmpty()) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    return true; // All tests passed
  }
  
  private static boolean testCompleteEventAlphabetical() {
    return true; // All tests passed
  }
  
  /**
   * Verifies the peekNextEvent() method. You may wish to break this out into smaller sub-testers.
   * @return true if all tests pass; false otherwise
   */
  public static boolean testPeek() {
    { // Test peek with array constructor chronologically
      try {
        PriorityEvents.sortChronologically();
        Event[] events = new Event[5];
        events[0] = new Event("Event1", 10, 10, 0); // Day 10
        events[1] = new Event("Event2", 9, 12, 0);  // Day 9
        events[2] = new Event("Event3", 11, 11, 0); // Day 11
        events[3] = new Event("Event4", 8, 5, 0);   // Day 8 (earliest)
        events[4] = new Event("Event5", 10, 5, 0);  // Day 10

        PriorityEvents queue = new PriorityEvents(events, 5);

        if (queue.peekNextEvent() != events[3]) return false; // Day 8
        queue.completeEvent();

        if (queue.peekNextEvent() != events[1]) return false; // Day 9
        queue.completeEvent();

        if (queue.peekNextEvent() != events[4]) return false; // Day 10, earlier hour
        queue.completeEvent();

        if (queue.peekNextEvent() != events[0]) return false; // Day 10
        queue.completeEvent();

        if (queue.peekNextEvent() != events[2]) return false; // Day 11
        queue.completeEvent();

      } catch (Exception e) {
        return false;
      }
    }

    { // Test peek with addEvent chronologically
      try {
        PriorityEvents.sortChronologically();
        PriorityEvents queue = new PriorityEvents(5);

        Event e1 = new Event("Event1", 10, 10, 0);
        Event e2 = new Event("Event2", 9, 12, 0);
        Event e3 = new Event("Event3", 11, 11, 0);
        Event e4 = new Event("Event4", 8, 5, 0);
        Event e5 = new Event("Event5", 10, 5, 0);

        queue.addEvent(e1);
        queue.addEvent(e2);
        queue.addEvent(e3);
        queue.addEvent(e4);
        queue.addEvent(e5);

        if (queue.peekNextEvent() != e4) return false; // Day 8
        queue.completeEvent();

        if (queue.peekNextEvent() != e2) return false; // Day 9
        queue.completeEvent();

        if (queue.peekNextEvent() != e5) return false; // Day 10, earlier hour
        queue.completeEvent();

        if (queue.peekNextEvent() != e1) return false; // Day 10
        queue.completeEvent();

        if (queue.peekNextEvent() != e3) return false; // Day 11
        queue.completeEvent();

      } catch (Exception e) {
        return false;
      }
    }

    { // Test peek with array constructor alphabetically
      try {
        PriorityEvents.sortAlphabetically();
        Event[] events = new Event[5];
        events[0] = new Event("Event1", 10, 10, 0);
        events[1] = new Event("Event2", 9, 12, 0);
        events[2] = new Event("Event3", 11, 11, 0);
        events[3] = new Event("Event4", 8, 5, 0);
        events[4] = new Event("Event5", 10, 5, 0);

        PriorityEvents queue = new PriorityEvents(events, 5);

        if (queue.peekNextEvent() != events[0]) return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != events[1]) return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != events[2]) return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != events[3]) return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != events[4]) return false;
        queue.completeEvent();

      } catch (Exception e) {
        return false;
      }
    }

    { // Test peek with addEvent alphabetically
      try {
        PriorityEvents.sortAlphabetically();
        PriorityEvents queue = new PriorityEvents(5);

        Event e1 = new Event("Event1", 10, 10, 0);
        Event e2 = new Event("Event2", 9, 12, 0);
        Event e3 = new Event("Event3", 11, 11, 0);
        Event e4 = new Event("Event4", 8, 5, 0);
        Event e5 = new Event("Event5", 10, 5, 0);

        queue.addEvent(e1);
        queue.addEvent(e2);
        queue.addEvent(e3);
        queue.addEvent(e4);
        queue.addEvent(e5);

        if (queue.peekNextEvent() != e1) return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != e2) return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != e3) return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != e4) return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != e5) return false;
        queue.completeEvent();

      } catch (Exception e) {
        return false;
      }
    }

    { // Test peek throws on empty queue
      try {
        PriorityEvents.sortAlphabetically();
        PriorityEvents queue = new PriorityEvents(5);
        queue.peekNextEvent(); // should throw
        return false;
      } catch (NoSuchElementException expected) {
        // pass
      } catch (Exception e) {
        return false;
      }
    }

    return true; // All tests passed
  }
  
  /**
   * Verifies the overloaded PriorityEvents constructor that creates a valid heap from an input
   * array of values. You may wish to break this out into smaller sub-testers.
   * @return true if all tests pass; false otherwise
   */
  public static boolean testHeapify() {
    return false; // TODO
  }

  /**
   * Helper method to verify if an array maintains the heap property
   * @param heapData the array to check
   * @return true if the array maintains the heap property, false otherwise
   */
  private static boolean isValidHeapChronologically(Event[] heapData) {
    for (int j = 0; j < heapData.length / 2; j++) {
      int leftChild = 2 * j + 1;
      int rightChild = 2 * j + 2;

      if (leftChild < heapData.length && heapData[leftChild] != null) {
        if (heapData[j].compareTo(heapData[leftChild]) > 0) {
          return false;
        }
      }

      if (rightChild < heapData.length && heapData[rightChild] != null) {
        if (heapData[j].compareTo(heapData[rightChild]) > 0) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Helper method to verify if an array maintains the heap property
   * @param heapData the array to check
   * @return true if the array maintains the heap property, false otherwise
   */
  private static boolean isValidHeapAlphabetically(Event[] heapData) {
    for (int j = 0; j < heapData.length / 2; j++) {
      int leftChild = 2 * j + 1;
      int rightChild = 2 * j + 2;

      if (leftChild < heapData.length && heapData[leftChild] != null) {
        if (heapData[j].getDescription().compareTo(heapData[leftChild].getDescription()) > 0) {
          return false;
        }
      }

      if (rightChild < heapData.length && heapData[rightChild] != null) {
        if (heapData[j].getDescription().compareTo(heapData[rightChild].getDescription()) > 0) {
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.out.println("ADD: "+testAddEvent());
    System.out.println("COMPLETE: "+testCompleteEvent());
    System.out.println("PEEK: "+testPeek());
    System.out.println("HEAPIFY: "+testHeapify());
  }

}
