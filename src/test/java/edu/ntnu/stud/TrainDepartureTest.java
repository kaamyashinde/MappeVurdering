package edu.ntnu.stud;
/**
 * This file runs JUnit tests on the TrainDeparture entity class to test the toString and printTableHeader methods.
 * The other methods aren't tested because they are getters and setters, and they are tested in the TrainDepartureRegisterTest class.
 * */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TrainDepartureTest {
    TrainDeparture trainDeparture = new TrainDeparture(1, "test", 1, "test", 1, 1, 1);

    @Test
    void testToString() {
        assertEquals("| 1          | test       | 1          | test                 | 1     | 1          | 1     |\n" +
                "--------------------------------------------------------------------------------------------", trainDeparture.toString());
      }

  @Test
  void printTableHeader() {
    assertEquals(TrainDeparture.HEADER, TrainDeparture.printTableHeader());
  }

    @Test
    void getDepartureTime() {
        assertEquals(1, trainDeparture.getDepartureTime());
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
        assertEquals(1, trainDeparture.getDelay());
      }

    @Test
    void getDelayedTime() {
        assertEquals(1, trainDeparture.getDelayedTime());
      }

    @Test
    void getTrack() {
        assertEquals(1, trainDeparture.getTrack());
      }

    @Test
    void setDelay() {
        trainDeparture.setDelay(2);
        assertEquals(2, trainDeparture.getDelay());
      }

    @Test
    void setDelayedTime() {
        trainDeparture.setDelayedTime();
        assertEquals(2, trainDeparture.getDelayedTime());
      }

    @Test
    void setTrack() {
        trainDeparture.setTrack(2);
        assertEquals(2, trainDeparture.getTrack());
      }
}