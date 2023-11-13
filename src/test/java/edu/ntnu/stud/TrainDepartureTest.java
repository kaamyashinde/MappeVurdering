package edu.ntnu.stud;

import java.time.LocalTime;
/**
 * This file runs JUnit tests on the TrainDeparture entity class to test the toString and
 * getTableHeader methods. The other methods aren't tested because they are getters and setters,
 * and they are tested in the TrainDepartureRegisterTest class.
 */
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainDepartureTest {

  TrainDeparture trainDeparture =
      new TrainDeparture(
          LocalTime.of(15, 30),
          "test",
          1,
          "test",
          2,
          1); // Delayed train departure that has a track
  TrainDeparture trainDeparture1 =
      new TrainDeparture(
          LocalTime.of(15, 30),
          "test",
          1,
          "test",
          0,
          5); // Train Departure that is on time and has a track
  TrainDeparture trainDeparture2 =
      new TrainDeparture(
          LocalTime.of(15, 30),
          "test",
          1,
          "test",
          2,
          -1); // Delayed train departure that does not have a track

  @Test
  void testToString() {

    assertEquals(
        "| 15:30      | test       | 1          | test                 | 15:32      | 1     |\n"
            + "------------------------------------------------------------------------------------",
        trainDeparture.toString()); // Testing the printing of all attributes
    assertEquals(
        "| 15:30      | test       | 1          | test                 |            | 5     |\n"
            + "------------------------------------------------------------------------------------",
        trainDeparture1
            .toString()); // Since it is not delayed, a blank space should be printed for the
                          // delayed time
    assertEquals(
        "| 15:30      | test       | 1          | test                 | 15:32      |       |\n"
            + "------------------------------------------------------------------------------------",
        trainDeparture2
            .toString()); // Since it does not have a track, a blank space should be printed for the
                          // track
  }

  @Test
  void getTableHeader() {
    assertEquals(TrainDeparture.getTableHeader(), TrainDeparture.getTableHeader());
  }

  // testing the get-methods:
  @Test
  void getDepartureTimeFormatted() {
    // if this test works then the method for getDepartureTime() works as well
    // because if it hadn't then it wouldn't have had converted the right value to String
    assertEquals("15:30", trainDeparture.getDepartureTimeFormatted());
  }

  @Test
  void getTrainLine() {
    assertEquals("test", trainDeparture.getTrainLine());
  }

  @Test
  void getTrainId() {
    assertEquals(1, trainDeparture.getTrainId());
  }

  @Test
  void getDestination() {
    assertEquals("test", trainDeparture.getDestination());
  }

  @Test
  void getDelay() {
    assertEquals(2, trainDeparture.getDelay());
    assertEquals(
        0, trainDeparture1.getDelay()); // Testing the case where the train departure is on time
  }

  @Test
  void getDelayedTimeFormatted() {
      // if this test works then the method for getDelayedTime() works as well
      // because if it hadn't then it wouldn't have had converted the right value to String
    assertEquals("15:32", trainDeparture.getDelayedTimeFormatted());
  }

  @Test
  void getTrack() {
    assertEquals(1, trainDeparture.getTrack());
    assertEquals(
            -1, trainDeparture2.getTrack()); // Testing the case where the train departure does not have a track
  }

  // testing the set-methods:
  @Test
  void setDelay() {
    trainDeparture.setDelayAndDelayTime(2);
    assertEquals(2, trainDeparture.getDelay());
  }

  @Test
  void setTrack() {
    trainDeparture.setTrack(2);
    assertEquals(2, trainDeparture.getTrack());
  }
}
