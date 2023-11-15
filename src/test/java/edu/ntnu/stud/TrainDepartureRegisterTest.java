package edu.ntnu.stud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalTime;

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
    trainDepartureNumber1 = new TrainDeparture(LocalTime.of(10, 30), "test", 1, "bergen", 1, 3);
    trainDepartureNumber2 = new TrainDeparture(LocalTime.of(12, 30), "test", 2, "OSLO", 0, 2);
    trainDepartureNumber3 = new TrainDeparture(LocalTime.of(15, 30), "test", 3, "BergeN", 0, -1);
    trainDepartureNumber4 = new TrainDeparture(LocalTime.of(11, 30), "test", 4, "OsLo", 1, 3);
    tdr.addTrainDeparture(trainDepartureNumber1);
    tdr.addTrainDeparture(trainDepartureNumber2);
    tdr.addTrainDeparture(trainDepartureNumber3);
    tdr.addTrainDeparture(trainDepartureNumber4);
  }

  /** Adding a train departure with valid attribute values. */
  @Test
  @DisplayName("Add train departure")
  void addTrainDepartureWithValidInputs() {
    assertEquals(4, tdr.allTrainDepartures.size());
  }

  /** Testing the sorting of the register and the toString method */
  @Test
  @DisplayName("Test the sorting and toString method")
  void sortTrainDepartureRegisterAndReturnUsingToString() {
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

  /** Testing whether the train departures are removed before the time given */
  @Test
  @DisplayName("Test the removal of train departures")
  void removeTrainDepartureBeforeTime() {
    tdr.removeTrainDepartureBeforeTime(LocalTime.of(11, 0));
    assertEquals(
        trainDepartureNumber4 + "\n" + trainDepartureNumber2 + "\n" + trainDepartureNumber3,
        tdr.toString());
  }

  /** Testing the error handling of the method that removes train departures */
  @Test
  @DisplayName("Error handling of removeTrainDepartureBeforeTime")
  void removeTrainDepartureBeforeTimeErrorHandling() {
    assertThrows(NullPointerException.class, () -> tdr.removeTrainDepartureBeforeTime(null));
    assertThrows(
        DateTimeException.class, () -> tdr.removeTrainDepartureBeforeTime(LocalTime.of(24, 0)));
  }

  /** Testing whether a train departure exists in the register */
  @Test
  @DisplayName("Check if train departure exists in the register")
  void checkIfExists() {
    assertTrue(tdr.checkIfExists(1));
    assertFalse(tdr.checkIfExists(9));
  }

  /**
   * Testing the error handling of the method that checks if a train departure exists in the
   * register
   */
  @Test
  @DisplayName("Error handling of checkIfExists")
  void checkIfExistsErrorHandling() {
    assertThrows(IllegalArgumentException.class, () -> tdr.checkIfExists(-1));
  }

  // JUnit test for the get-methods:

  /** Testing the filtering of the train departures based on the train ID */
  @Test
  @DisplayName("Get train departure based on train ID")
  void getTrainDepartureBasedOnTrainId() {
    assertEquals(trainDepartureNumber1.toString(), tdr.getTrainDepartureBasedOnTrainId(1));
  }

  /**
   * Testing the error handling of the method that filters the train departures based on the train
   * ID
   */
  @Test
  @DisplayName("Error handling of getTrainDepartureBasedOnTrainId")
  void getTrainDepartureBasedOnTrainIdErrorHandling() {
    assertThrows(IllegalArgumentException.class, () -> tdr.getTrainDepartureBasedOnTrainId(-1));
  }

  /** Testing the filtering of the train departures based on the destination */
  @Test
  @DisplayName("Get train departure based on destination")
  void getTrainDepartureBasedOnDestination() {
    assertEquals(
        trainDepartureNumber1.toString() + "\n" + trainDepartureNumber3,
        tdr.getTrainDepartureBasedOnDestination("Bergen"));
  }

  /**
   * Testing the error handling of the method that filters the train departures based on the
   * destination
   */
  @Test
  @DisplayName("Error handling of getTrainDepartureBasedOnDestination")
  void getTrainDepartureBasedOnDestinationErrorHandling() {
    assertThrows(NullPointerException.class, () -> tdr.getTrainDepartureBasedOnDestination(null));
    assertThrows(IllegalArgumentException.class, () -> tdr.getTrainDepartureBasedOnDestination(""));
    assertThrows(
        IllegalArgumentException.class, () -> tdr.getTrainDepartureBasedOnDestination("   "));
  }

  // JUnit test for the set-methods:

  /** Testing the setting of the delay for a train departure */
  @Test
  @DisplayName("Set delay for a train departure")
  void setDelayThroughRegister() {
    tdr.setDelayThroughRegister(2, 2);
    assertEquals("12:32", trainDepartureNumber2.getDelayedTimeFormatted());
  }

  /** Error handling of the method that sets the delay for a train departure */
  @Test
  @DisplayName("Error handling of setDelayThroughRegister")
  void setDelayThroughRegisterErrorHandling() {
    assertThrows(IllegalArgumentException.class, () -> tdr.setDelayThroughRegister(-1, 2));
    assertThrows(IllegalArgumentException.class, () -> tdr.setDelayThroughRegister(2, -2));
  }

  /** Testing the setting of the track number for a train departure */
  @Test
  @DisplayName("Set track number for a train departure")
  void setTrackNumberThroughRegister() {
    tdr.setTrackNumberThroughRegister(2, 2);
    assertEquals(2, trainDepartureNumber2.getTrack());
  }

  /** Error handling of the method that sets the track number for a train departure */
  @Test
  @DisplayName("Error handling of setTrackNumberThroughRegister")
  void setTrackNumberThroughRegisterErrorHandling() {
    assertThrows(IllegalArgumentException.class, () -> tdr.setTrackNumberThroughRegister(-1, 2));
    assertThrows(IllegalArgumentException.class, () -> tdr.setTrackNumberThroughRegister(2, -2));
    assertThrows(IllegalArgumentException.class, () -> tdr.setTrackNumberThroughRegister(2, 16));
  }
}
