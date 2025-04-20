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
   * This method runs all sub-testers related to testing adding an Event to the priority queue. You
   * may wish to add additional output for clarity, or additional private tester methods related to
   * adding Events.
   *
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

    { // Tests percolate up
      try {
        PriorityEvents.sortChronologically();
        PriorityEvents events = new PriorityEvents(10);

        Event event2 = new Event("Event 2", 10, 10, 0);
        Event event3 = new Event("Event 3", 11, 10, 0);
        Event event4 = new Event("Event 4", 12, 10, 0);
        Event event5 = new Event("Event 5", 13, 10, 0);
        Event event1 = new Event("Event 1", 9, 10, 0);

        events.addEvent(event3);
        if (!events.peekNextEvent().equals(event3)) { return false; }
        events.addEvent(event4);
        if (!events.peekNextEvent().equals(event3)) { return false; }
        events.addEvent(event2);
        if (!events.peekNextEvent().equals(event2)) { return false; }
        events.addEvent(event5);
        if (!events.peekNextEvent().equals(event2)) { return false; }
        events.addEvent(event1); // Should percolate to the root
        if (!events.peekNextEvent().equals(event1)) { return false; }

        // Test heap size
        Event[] heapData = events.getHeapData();
        if (heapData.length != 5) {
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
        if (queue.isEmpty() || !queue.peekNextEvent().equals(events[1])) {
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
        if (!events.peekNextEvent().equals(eventE)) { return false; }
        events.addEvent(eventD);
        if (!events.peekNextEvent().equals(eventD)) { return false; }
        events.addEvent(eventC);
        if (!events.peekNextEvent().equals(eventC)) { return false; }
        events.addEvent(eventB);
        if (!events.peekNextEvent().equals(eventB)) { return false; }
        events.addEvent(eventA); // Should percolate to the root
        if (events.peekNextEvent() != eventA) {
          return false;
        }

        // Verify heap size
        Event[] heapData = events.getHeapData();
        if (heapData.length != 5) {
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
   *
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

        // Verify that the next event is now at the root, which is NOT events[3]
        if (queue.peekNextEvent().equals(events[3]) || !queue.peekNextEvent().equals(events[1])) {
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

        // Verify the root after consecutive completions
        queue.completeEvent();
        if (!queue.peekNextEvent().equals(events[4])) {
          return false;
        }
        queue.completeEvent();
        if (!queue.peekNextEvent().equals(events[0])) {
          return false;
        }
        queue.completeEvent();
        if (!queue.peekNextEvent().equals(events[2])) {
          return false;
        }
        if (queue.size() != 1) {
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
        if (!queue.peekNextEvent().equals(events[1])) {
          return false;
        }

        queue.completeEvent();
        // Checks the formatting of the heap
        if (!queue.peekNextEvent().equals(events[0])) {
          return false;
        }

        queue.completeEvent(); // Empties the queue


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
    { // Test basic functionality for completeEvent
      try {
        PriorityEvents.sortAlphabetically();
        Event[] events = new Event[5];
        events[0] = new Event("EventC", 10, 10, 0);
        events[1] = new Event("EventB", 9, 12, 0);
        events[2] = new Event("EventE", 11, 11, 0);
        events[3] = new Event("EventA", 8, 5, 0);
        events[4] = new Event("EventD", 10, 5, 0);
        PriorityEvents queue = new PriorityEvents(events, 5);

        // Verify initial state (alphabetically first event should be at root)
        if (queue.isEmpty() || !queue.peekNextEvent().equals(events[3])) {
          return false;
        }

        // Complete the first event
        queue.completeEvent();

        // Verify that the next event is now at the root
        if (queue.peekNextEvent().equals(events[3])) {
          return false; // Should no longer be the same event
        }

        // Verify that the completed event is in the completed array
        Event[] completed = queue.getCompletedEvents();
        if (completed.length != 1 || !completed[0].equals(
            events[3]) || !completed[0].isComplete()) {
          return false;
        }

        // Verify the size has decreased
        Event[] heapData = queue.getHeapData();
        if (heapData.length != 4) {
          return false;
        }

        // Check if heap property is maintained after removal (alphabetical ordering)
        if (!queue.peekNextEvent().equals(events[1])) {
          return false;
        }

        queue.completeEvent();
        // Check if heap property is maintained after removal (alphabetical ordering)
        if (!queue.peekNextEvent().equals(events[4])) {
          return false;
        }

        queue.completeEvent();
        // Check if heap property is maintained after removal (alphabetical ordering)
        if (!queue.peekNextEvent().equals(events[0])) {
          return false;
        }


        // Check size
        if (queue.size() != 1 || !queue.peekNextEvent().equals(events[2])) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    { // Test completeEvent with multiple events (alphabetical)
      try {
        PriorityEvents.sortAlphabetically();
        PriorityEvents queue = new PriorityEvents(5);

        Event event1 = new Event("EventC", 10, 10, 0);
        Event event2 = new Event("EventB", 9, 12, 0);
        Event event3 = new Event("EventA", 8, 5, 0);

        queue.addEvent(event1);
        queue.addEvent(event2);
        queue.addEvent(event3);

        if (!queue.peekNextEvent().equals(event3)) {
          return false;
        }

        // Complete the first event
        queue.completeEvent();

        // Check for valid ordering of the heap
        if (!queue.peekNextEvent().equals(event2)) {
          return false;
        }
        queue.completeEvent();

        if (!queue.peekNextEvent().equals(event1)) {
          return false;
        }
        queue.completeEvent();

        Event[] completed = queue.getCompletedEvents();
        if (completed.length != 3) {
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

    { // Test clearCompletedEvents
      try {
        PriorityEvents.sortAlphabetically();
        PriorityEvents queue = new PriorityEvents(5);

        Event event1 = new Event("EventC", 10, 10, 0);
        Event event2 = new Event("EventA", 9, 12, 0);

        queue.addEvent(event1);
        queue.addEvent(event2);

        queue.completeEvent();

        // Verify completed array has one element
        Event[] completed = queue.getCompletedEvents();
        if (completed.length != 1) {
          return false;
        }

        // Clear completed events
        Event[] clearedEvents = queue.clearCompletedEvents();

        // Verify the returned array has the expected events
        if (clearedEvents.length != 1 || !clearedEvents[0].equals(event2)) {
          return false;
        }

        // Verify the completed array is now empty
        completed = queue.getCompletedEvents();
        if (completed.length != 0) {
          return false;
        }
      } catch (Exception e) {
        return false;
      }
    }

    return true; // All tests passed
  }

  /**
   * Verifies the peekNextEvent() method. You may wish to break this out into smaller sub-testers.
   *
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

        if (queue.peekNextEvent() != events[3])
          return false; // Day 8
        queue.completeEvent();

        if (queue.peekNextEvent() != events[1])
          return false; // Day 9
        queue.completeEvent();

        if (queue.peekNextEvent() != events[4])
          return false; // Day 10, earlier hour
        queue.completeEvent();

        if (queue.peekNextEvent() != events[0])
          return false; // Day 10
        queue.completeEvent();

        if (queue.peekNextEvent() != events[2])
          return false; // Day 11
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

        if (queue.peekNextEvent() != e4)
          return false; // Day 8
        queue.completeEvent();

        if (queue.peekNextEvent() != e2)
          return false; // Day 9
        queue.completeEvent();

        if (queue.peekNextEvent() != e5)
          return false; // Day 10, earlier hour
        queue.completeEvent();

        if (queue.peekNextEvent() != e1)
          return false; // Day 10
        queue.completeEvent();

        if (queue.peekNextEvent() != e3)
          return false; // Day 11
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

        if (queue.peekNextEvent() != events[0])
          return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != events[1])
          return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != events[2])
          return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != events[3])
          return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != events[4])
          return false;
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

        if (queue.peekNextEvent() != e1)
          return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != e2)
          return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != e3)
          return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != e4)
          return false;
        queue.completeEvent();

        if (queue.peekNextEvent() != e5)
          return false;
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
   *
   * @return true if all tests pass; false otherwise
   */
  public static boolean testHeapify() {
    { // Tests that heapify will throw an exception if an event is already completed
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
    { // Test heapify with 0 elements
      try {
        PriorityEvents.sortChronologically();
        Event[] events = new Event[0];
        PriorityEvents queue = new PriorityEvents(events, 0);

        if (!queue.isEmpty()) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }

    { // Test heapify with 1 element
      try {
        PriorityEvents.sortChronologically();
        Event[] events = new Event[1];
        events[0] = new Event("Solo", 5, 5, 0);

        PriorityEvents queue = new PriorityEvents(events, 1);
        if (queue.isEmpty() || queue.size() != 1 || queue.peekNextEvent() != events[0]) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }

    { // Test valid heapify with multiple elements chronologically
      try {
        PriorityEvents.sortChronologically();
        Event[] events = new Event[4];
        events[0] = new Event("Event1", 10, 12, 0);
        events[1] = new Event("Event2", 8, 11, 0);
        events[2] = new Event("Event3", 9, 9, 30);
        events[3] = new Event("Event4", 10, 8, 0);

        PriorityEvents queue = new PriorityEvents(events, 4);

        // Check that the heapify was in the correct order
        if (queue.peekNextEvent() != events[1])
          return false;
        queue.completeEvent();
        if (queue.peekNextEvent() != events[2])
          return false;
        queue.completeEvent();
        if (queue.peekNextEvent() != events[3])
          return false;
        queue.completeEvent();
        if (queue.peekNextEvent() != events[0])
          return false;

      } catch (Exception e) {
        return false;
      }
    }

    { // Test valid heapify with multiple elements alphabetically
      try {
        PriorityEvents.sortAlphabetically();
        Event[] events = new Event[4];
        events[0] = new Event("D Event", 10, 12, 0);
        events[1] = new Event("A Event", 8, 11, 0);
        events[2] = new Event("C Event", 9, 9, 30);
        events[3] = new Event("B Event", 10, 8, 0);

        PriorityEvents queue = new PriorityEvents(events, 4);

        // Check that the heapify was in the correct order
        if (queue.peekNextEvent() != events[1])
          return false;
        queue.completeEvent();
        if (queue.peekNextEvent() != events[3])
          return false;
        queue.completeEvent();
        if (queue.peekNextEvent() != events[2])
          return false;
        queue.completeEvent();
        if (queue.peekNextEvent() != events[0])
          return false;

      } catch (Exception e) {
        return false;
      }
    }

    return true;
  }

  public static void main(String[] args) {
    System.out.println("ADD: " + testAddEvent());
    System.out.println("COMPLETE: " + testCompleteEvent());
    System.out.println("PEEK: " + testPeek());
    System.out.println("HEAPIFY: " + testHeapify());
  }

}
