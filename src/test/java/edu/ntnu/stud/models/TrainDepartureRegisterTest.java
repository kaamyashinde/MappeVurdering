package edu.ntnu.stud.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
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
  @DisplayName("Before each: Add train departures to the register")
  public void setUp() {
    tdr.addTrainDeparture(LocalTime.of(10, 30), "test", 1, "Bergen", 1, 3);
    tdr.addTrainDeparture(LocalTime.of(12, 30), "test", 2, "Oslo", 0, 2);
    tdr.addTrainDeparture(LocalTime.of(15, 30), "test", 3, "Bergen", 0, -1);
    tdr.addTrainDeparture(LocalTime.of(11, 30), "test", 4, "Oslo", 1, 3);
  }

  @BeforeEach
  @DisplayName("Before each: Initialise train departure instances for testing ")
  public void initialiseDepartures() {
    trainDepartureNumber1 = new TrainDeparture(LocalTime.of(10, 30), "test", 1, "Bergen", 1, 3);
    trainDepartureNumber2 = new TrainDeparture(LocalTime.of(12, 30), "test", 2, "Oslo", 0, 2);
    trainDepartureNumber3 = new TrainDeparture(LocalTime.of(15, 30), "test", 3, "Bergen", 0, -1);
    trainDepartureNumber4 = new TrainDeparture(LocalTime.of(11, 30), "test", 4, "Oslo", 1, 3);
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
      ArrayList<TrainDeparture> sortedList = tdr.sortTheTrainDepartureRegister();
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
    void checkIfIdExists() {
      assertTrue(tdr.checkIfDepartureIdExists(1));
      assertFalse(tdr.checkIfDepartureIdExists(9));
    }

    /**
     * Testing the error handling of the method that checks if a train departure exists in the
     * register.
     */
    @Test
    @DisplayName("Error handling of checkIfIdExists")
    void checkIfIdExistsErrorHandling() {
      assertThrows(IllegalArgumentException.class, () -> tdr.checkIfDepartureIdExists(-1));
    }

    /** Testing whether there exist train departure(s) going to a specific destination. */
    @Test
    @DisplayName("Check if destination exists in the register")
    void checkIfDestinationExists() {
      assertTrue(tdr.checkIfDestinationExists("Bergen"));
      assertFalse(tdr.checkIfDestinationExists("Trondheim"));
    }

    /**
     * Testing the error handling of the method that checks if there exist train departure(s) going
     * to a specific destination.
     */
    @Test
    @DisplayName("Error handling of checkIfDestinationExists")
    void checkIfDestinationExistsErrorHandling() {
      assertThrows(NullPointerException.class, () -> tdr.checkIfDestinationExists(null));
      assertThrows(IllegalArgumentException.class, () -> tdr.checkIfDestinationExists(""));
      assertThrows(IllegalArgumentException.class, () -> tdr.checkIfDestinationExists("   "));
    }

    /**
     * Testing the exception when two train departures are leaving from the same track at the same
     * time.
     */
    @Test
    @DisplayName("Same track and time exception")
    void sameTrackAndTimeException() {
      TrainDepartureRegister newRegister = new TrainDepartureRegister();
      newRegister.addTrainDeparture(LocalTime.of(10, 3), "test", 1, "bergen", 1, 2);
      TrainDeparture td = new TrainDeparture(LocalTime.of(10, 3), "test", 2, "OSLO", 1, 2);
      assertThrows(
          IllegalArgumentException.class,
          () -> newRegister.checkIfTwoDeparturesLeaveFromSameTrackAtTheSameTime(td));
    }

    /** Testing the removal of train departure based on its ID. */
    @Test
    @DisplayName("Remove train departure based on ID")
    void removeTrainDepartureBasedOnId() {
      tdr.removeTrainDepartureBasedOnId(1);
      assertFalse(tdr.checkIfDepartureIdExists(1));
    }

    /** Testing the error handling of the method that removes train departure based on its ID. */
    @Test
    @DisplayName("Error handling of removeTrainDepartureBasedOnId")
    void removeTrainDepartureBasedOnIdErrorHandling() {
      assertThrows(IllegalArgumentException.class, () -> tdr.removeTrainDepartureBasedOnId(-1));
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
      assertEquals(trainDepartureNumber1.toString(), tdr.returnTrainDepartureBasedOnId(1));
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
      assertThrows(
          NullPointerException.class, () -> tdr.returnTrainDeparturesBasedOnDestination(null));
      assertThrows(
          IllegalArgumentException.class, () -> tdr.returnTrainDeparturesBasedOnDestination(""));
      assertThrows(
          IllegalArgumentException.class, () -> tdr.returnTrainDeparturesBasedOnDestination("   "));
    }

    /** Testing the retrieval of total number of train departures. */
    @Test
    @DisplayName("Get total number of train departures")
    void getTotalNumberOfTrainDepartures() {
      assertEquals(4, tdr.getNumberOfTrainDepartures());
    }

    /** Testing the retrieval of amount of time til next departure. */
    @Test
    @DisplayName("Get amount of time til next departure")
    void getAmountOfTimeTilNextDeparture() {
      LocalTime systemTime = LocalTime.of(10, 0);
      LocalTime timeOfNextDeparture = LocalTime.of(10, 31);
      Duration timeUntilNextDeparture = Duration.between(systemTime, timeOfNextDeparture);
      assertEquals(timeUntilNextDeparture.toMinutes(), tdr.getTimeTilNextDeparture(systemTime));
    }

    /**
     * Testing the error handling of the method that retrieves the amount of time til next
     * departure.
     */
    @Test
    @DisplayName("Error handling of getAmountOfTimeTilNextDeparture")
    void getAmountOfTimeTilNextDepartureErrorHandling() {
      assertThrows(NullPointerException.class, () -> tdr.getTimeTilNextDeparture(null));
    }

    /**
     * Testing the retrieval of the 50th percentile of the train departures based on the departure
     * time
     */
    @Test
    @DisplayName("IQR of the register")
    void getInterQuartileRange() {
      TrainDepartureRegister newRegister = new TrainDepartureRegister();
      newRegister.addTrainDeparture(LocalTime.of(10, 3), "test", 1, "bergen", 1, 3);
      newRegister.addTrainDeparture(LocalTime.of(12, 57), "test", 2, "OSLO", 0, 2);
      newRegister.addTrainDeparture(LocalTime.of(15, 20), "test", 3, "BergeN", 0, -1);
      newRegister.addTrainDeparture(LocalTime.of(11, 7), "test", 4, "OsLo", 1, 3);
      newRegister.addTrainDeparture(LocalTime.of(11, 30), "test", 5, "OsLo", 1, 3);

      String timeSpanBetween2ndAnd3rdDeparture = "11:07 - 12:57";

      assertEquals(timeSpanBetween2ndAnd3rdDeparture, newRegister.findInterQuartileRange());
    }

    /**
     * Testing the retrieval of all the destinations that the departures in the register are going
     * to.
     */
    @Test
    @DisplayName("Get all destinations in the register")
    void getAllDestinationsInRegister() {
      assertEquals("Bergen | Oslo", tdr.returnAllDestinationsInRegister());
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
      TrainDeparture trainDepartureNumber2WithDelay =
          new TrainDeparture(LocalTime.of(12, 30), "test", 2, "OSLO", 2, 2);

      assertEquals(trainDepartureNumber2WithDelay.toString(), tdr.returnTrainDepartureBasedOnId(2));
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

  /** Testing the toString method */
  @Test
  @DisplayName("Test the toString method")
  void testToString() {
    assertEquals(
        trainDepartureNumber1
            + "\n"
            + trainDepartureNumber4
            + "\n"
            + trainDepartureNumber2
            + "\n"
            + trainDepartureNumber3,
        tdr.toString());
  }
}
