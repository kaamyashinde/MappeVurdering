package edu.ntnu.stud;
/**
 * This file runs JUnit tests on the TrainDeparture entity class to test the toString and printTableHeader methods.
 * The other methods aren't tested because they are getters and setters, and they are tested in the TrainDepartureRegisterTest class.
 */


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TrainDepartureTest {
    TrainDeparture trainDeparture = new TrainDeparture(1, "test", 1, "test", 2,  1); //Delayed train departure that has a track
    TrainDeparture trainDeparture1 = new TrainDeparture(1, "test", 1, "test", 0,  5); //Train Departure that is on time and has a track
    TrainDeparture trainDeparture2 = new TrainDeparture(1, "test", 1, "test", 2,  -1); //Delayed train departure that does not have a track
    @Test
    void testToString() {


        assertEquals("| 1          | test       | 1          | test                 | 3          | 1     |\n" +
                "------------------------------------------------------------------------------------", trainDeparture.toString()); //Testing the printing of all attributes
        assertEquals("| 1          | test       | 1          | test                 |            | 5     |\n" +
                "------------------------------------------------------------------------------------", trainDeparture1.toString()); //Since it is not delayed, a blank space should be printed for the delayed time
        assertEquals("| 1          | test       | 1          | test                 | 3          |       |\n" +
                "------------------------------------------------------------------------------------", trainDeparture2.toString()); //Since it does not have a track, a blank space should be printed for the track

      }

  @Test
  void printTableHeader() {
    assertEquals(TrainDeparture.HEADER, TrainDeparture.printTableHeader());
  }


    //testing the get-methods:
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
        assertEquals(2, trainDeparture.getDelay());
        assertEquals(0, trainDeparture1.getDelay()); //Testing the case where the train departure is on time
      }

    @Test
    void getDelayedTime() {
        assertEquals(3, trainDeparture.getDelayedTime());
      }

    @Test
    void getTrack() {
        assertEquals(1, trainDeparture.getTrack());
        assertEquals(-1, trainDeparture2.getTrack()); //Testing the case where the train departure does not have a track
      }


    //testing the set-methods:
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