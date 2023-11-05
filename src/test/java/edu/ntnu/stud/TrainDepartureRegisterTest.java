package edu.ntnu.stud;

/** This file runs JUnit tests on the TrainDepartureRegister class to test the methods. */
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TrainDepartureRegisterTest {
  TrainDepartureRegister tdr = new TrainDepartureRegister();
  TrainDeparture trainDepartureNumber1 =
      new TrainDeparture(LocalTime.of(10, 30), "test", 1, "bergen", 1, 3);
  TrainDeparture trainDepartureNumber2 =
      new TrainDeparture(LocalTime.of(12, 30), "test", 2, "OSLO", 0, 2);
  TrainDeparture trainDepartureNumber3 =
      new TrainDeparture(LocalTime.of(15, 30), "test", 3, "BergeN", 0, -1);

  TrainDeparture trainDepartureNumber4 =
      new TrainDeparture(LocalTime.of(11, 30), "test", 4, "OsLo", 1, 3);

  // JUnit test for the utility methods:
  @Test
  void addTrainDeparture() {
    tdr.addTrainDeparture(trainDepartureNumber1);
    tdr.addTrainDeparture(trainDepartureNumber2);
    tdr.addTrainDeparture(trainDepartureNumber3);
    assertEquals(3, tdr.allTrainDepartures.size());
  }

  @Test
  void testToString() {
    tdr.addTrainDeparture(trainDepartureNumber1);
    tdr.addTrainDeparture(trainDepartureNumber2);
    tdr.addTrainDeparture(trainDepartureNumber3);
    assertEquals(
        TrainDeparture.HEADER
            + "\n"
            + trainDepartureNumber1
            + "\n"
            + trainDepartureNumber2
            + "\n"
            + trainDepartureNumber3
            + "\n",
        tdr.toString());
  }

  @Test
  void sortTheTrainDepartureRegister() {
    tdr.addTrainDeparture(trainDepartureNumber1);
    tdr.addTrainDeparture(trainDepartureNumber2);
    tdr.addTrainDeparture(trainDepartureNumber3);
    tdr.addTrainDeparture(trainDepartureNumber4);
    assertEquals(
        TrainDeparture.HEADER
            + "\n"
            + trainDepartureNumber1.toString()
            + "\n"
            + trainDepartureNumber4
            + "\n"
            + trainDepartureNumber2
            + "\n"
            + trainDepartureNumber3
            + "\n",
        tdr.toString());
  }

  // JUnit test for the get-methods:
  @Test
  void getTrainDepartureBasedOnTrainId() {
    tdr.addTrainDeparture(trainDepartureNumber1);
    assertEquals(
        TrainDeparture.HEADER + "\n" + trainDepartureNumber1.toString(),
        tdr.getTrainDepartureBasedOnTrainId(1));
  }

  @Test
  void getTrainDepartureBasedOnDestination() {
    tdr.addTrainDeparture(trainDepartureNumber1);
    tdr.addTrainDeparture(trainDepartureNumber3);
    assertEquals(
        TrainDeparture.printTableHeader()
            + "\n"
            + trainDepartureNumber1.toString()
            + "\n"
            + trainDepartureNumber3,
        tdr.getTrainDepartureBasedOnDestination("Bergen"));
  }

  // JUnit test for the set-methods:
  @Test
  void setDelayThroughRegister() {
    tdr.addTrainDeparture(trainDepartureNumber2);
    tdr.setDelayThroughRegister(2, 2);
    assertEquals("12:32", trainDepartureNumber2.getDelayedTimeFormatted());
  }

  @Test
  void setTrackNumberThroughRegister() {
    tdr.setTrackNumberThroughRegister(2, 2);
    assertEquals(2, trainDepartureNumber2.getTrack());
  }
}
