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
    { // Tests basic add functionality
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
        for (int i = 0; i < heapData.length / 2; i++) {
          int leftChild = 2 * i + 1;
          int rightChild = 2 * i + 2;

          if (leftChild < heapData.length) {
            if (heapData[i].compareTo(heapData[leftChild]) > 0) {
              return false;
            }
          }

          if (rightChild < heapData.length) {
            if (heapData[i].compareTo(heapData[rightChild]) > 0) {
              return false;
            }
          }
        }
      } catch (Exception e) {
        return false;
      }
    }

    return true; // All tests passed
  }
  
  private static boolean testAddEventAlphabetical() {
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
        for (int i = 0; i < heapData.length / 2; i++) {
          int leftChild = 2 * i + 1;
          int rightChild = 2 * i + 2;

          if (leftChild < heapData.length && heapData[leftChild] != null) {
            if (heapData[i].getDescription().compareTo(heapData[leftChild].getDescription()) > 0) {
              return false;
            }
          }

          if (rightChild < heapData.length && heapData[rightChild] != null) {
            if (heapData[i].getDescription().compareTo(heapData[rightChild].getDescription()) > 0) {
              return false;
            }
          }
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
    return false; // TODO
  }
  
  private static boolean testCompleteEventAlphabetical() {
    return false; // TODO
  }
  
  /**
   * Verifies the peekNextEvent() method. You may wish to break this out into smaller sub-testers.
   * @return true if all tests pass; false otherwise
   */
  public static boolean testPeek() {
    return false; // TODO
  }
  
  /**
   * Verifies the overloaded PriorityEvents constructor that creates a valid heap from an input
   * array of values. You may wish to break this out into smaller sub-testers.
   * @return true if all tests pass; false otherwise
   */
  public static boolean testHeapify() {
    return false; // TODO
  }

  public static void main(String[] args) {
    System.out.println("ADD: "+testAddEvent());
    System.out.println("COMPLETE: "+testCompleteEvent());
    System.out.println("PEEK: "+testPeek());
    System.out.println("HEAPIFY: "+testHeapify());
  }

}
