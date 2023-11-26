package edu.ntnu.stud.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/** This file runs JUnit tests on the TrainDepartureRegister class to test the methods. */
class TrainDepartureRegisterTest {
  TrainDepartureRegister tdr = new TrainDepartureRegister();
  TrainDeparture trainDepartureNumber1;
  TrainDeparture trainDepartureNumber2;
  TrainDeparture trainDepartureNumber3;
  TrainDeparture trainDepartureNumber4;

  /** Initialise the train departures before each test. */
  @BeforeEach
  @DisplayName("Before each: Initialise train departures")
  public void setUp() {
    tdr.addTrainDeparture(LocalTime.of(10, 30), "test", 1, "bergen", 1, 3);
    tdr.addTrainDeparture(LocalTime.of(12, 30), "test", 2, "OSLO", 0, 2);
    tdr.addTrainDeparture(LocalTime.of(15, 30), "test", 3, "BergeN", 0, -1);
    tdr.addTrainDeparture(LocalTime.of(11, 30), "test", 4, "OsLo", 1, 3);
  }

  /** Collection of tests for the utilities */
  @Nested
  @DisplayName("The Utility Methods")
  class TestUtilities {
    /** Adding a train departure with valid attribute values. */
    @Test
    @DisplayName("Add train departure")
    void addTrainDepartureWithValidInputs() {
      assertTrue(tdr.checkIfDepartureIdExists(1));
    }

    /** Testing the sorting of the register and the toString method. */
    @Test
    @DisplayName("Test the sorting and toString method")
    void sortTrainDepartureRegisterAndReturnUsingToString() {
      ArrayList<TrainDeparture> sortedList =
          tdr.sortTheTrainDepartureRegister();
      assertEquals(
          "["
              + trainDepartureNumber1
              + ", "
              + trainDepartureNumber4
              + ", "
              + trainDepartureNumber2
              + ", "
              + trainDepartureNumber3
              + "]",
          sortedList.toString());
    }

    /** Testing whether the train departures are removed before the time given. */
    @Test
    @DisplayName("Test the removal of train departures")
    void removeTrainDepartureBeforeTime() {
      tdr.removeTrainDepartureBeforeTime(LocalTime.of(11, 0));
      assertFalse(tdr.checkIfDepartureIdExists(1));
    }

    /** Testing the error handling of the method that removes train departures. */
    @Test
    @DisplayName("Error handling of removeTrainDepartureBeforeTime")
    void removeTrainDepartureBeforeTimeErrorHandling() {
      assertThrows(NullPointerException.class, () -> tdr.removeTrainDepartureBeforeTime(null));
    }

    /** Testing whether a train departure exists in the register. */
    @Test
    @DisplayName("Check if train departure exists in the register")
    void checkIfExists() {
      assertTrue(tdr.checkIfDepartureIdExists(1));
      assertFalse(tdr.checkIfDepartureIdExists(9));
    }

    /**
     * Testing the error handling of the method that checks if a train departure exists in the
     * register.
     */
    @Test
    @DisplayName("Error handling of checkIfExists")
    void checkIfExistsErrorHandling() {
      assertThrows(IllegalArgumentException.class, () -> tdr.checkIfDepartureIdExists(-1));
    }
  }

  /** Collection of tests for the getters. */
  @Nested
  @DisplayName("Testing the getters")
  class TestGetters {


    /** Testing the filtering of the train departures based on the train ID. */
    @Test
    @DisplayName("Get train departure based on train ID")
    void getTrainDepartureBasedOnDepartureId() {
      assertEquals(
          trainDepartureNumber1.toString(), tdr.returnTrainDepartureBasedOnId(1));
    }

    /**
     * Testing the error handling of the method that filters the train departures based on the train
     * ID.
     */
    @Test
    @DisplayName("Error handling of getTrainDepartureBasedOnDepartureId")
    void getTrainDepartureBasedOnDepartureIdErrorHandling() {
      assertThrows(IllegalArgumentException.class, () -> tdr.returnTrainDepartureBasedOnId(-1));
    }

    /** Testing the filtering of the train departures based on the destination. */
    @Test
    @DisplayName("Get train departure based on destination")
    void getTrainDepartureBasedOnDestination() {
      String outcome = tdr.returnTrainDeparturesBasedOnDestination("Bergen").toString();
      assertEquals("[" + trainDepartureNumber1 + ", " + trainDepartureNumber3 + "]", outcome);
    }

    /**
     * Testing the error handling of the method that filters the train departures based on the
     * destination.
     */
    @Test
    @DisplayName("Error handling of getTrainDepartureBasedOnDestination")
    void getTrainDepartureBasedOnDestinationErrorHandling() {
      assertThrows(NullPointerException.class, () -> tdr.returnTrainDeparturesBasedOnDestination(null));
      assertThrows(IllegalArgumentException.class, () -> tdr.returnTrainDeparturesBasedOnDestination(""));
      assertThrows(
          IllegalArgumentException.class, () -> tdr.returnTrainDeparturesBasedOnDestination("   "));
    }
  }

  /** Collection of tests for the setters. */
  @Nested
  @DisplayName("Testing the setters")
  class TestSetters {
    /** Testing the setting of the delay for a train departure. */
    @Test
    @DisplayName("Set delay for a train departure")
    void setDelayThroughRegister() {
      tdr.assignDelay(2, 2);
      assertEquals("12:32", trainDepartureNumber2.getDelayedTimeFormatted());
    }

    /** Error handling of the method that sets the delay for a train departure. */
    @Test
    @DisplayName("Error handling of setDelayThroughRegister")
    void setDelayThroughRegisterErrorHandling() {
      assertThrows(IllegalArgumentException.class, () -> tdr.assignDelay(-1, 2));
      assertThrows(IllegalArgumentException.class, () -> tdr.assignDelay(2, -2));
    }

    /** Testing the setting of the track number for a train departure. */
    @Test
    @DisplayName("Set track number for a train departure")
    void setTrackNumberThroughRegister() {
      tdr.assignTrack(2, 2);
      assertEquals(2, trainDepartureNumber2.getTrack());
    }

    /** Error handling of the method that sets the track number for a train departure. */
    @Test
    @DisplayName("Error handling of setTrackNumberThroughRegister")
    void setTrackNumberThroughRegisterErrorHandling() {
      assertThrows(IllegalArgumentException.class, () -> tdr.assignTrack(-1, 2));
      assertThrows(IllegalArgumentException.class, () -> tdr.assignTrack(2, -2));
      assertThrows(IllegalArgumentException.class, () -> tdr.assignTrack(2, 16));
    }
  }
}
