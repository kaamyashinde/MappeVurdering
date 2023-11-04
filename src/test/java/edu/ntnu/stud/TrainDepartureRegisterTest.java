package edu.ntnu.stud;
/**
 * This file runs JUnit tests on the TrainDepartureRegister class to test the methods.
 */

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
class TrainDepartureRegisterTest {

    @Test
    void addTrainDeparture() {
        TrainDepartureRegister tdr = new TrainDepartureRegister();
        TrainDeparture trainDepartureNumber1 = new TrainDeparture(LocalTime.of(15,30), "test", 1, "test", 1, 1);
        tdr.addTrainDeparture(trainDepartureNumber1);
        TrainDeparture trainDepartureNumber2 = new TrainDeparture(LocalTime.of(15,30), "test", 2, "test", 2, 2);
        tdr.addTrainDeparture(trainDepartureNumber2);

        assertEquals(2, tdr.allTrainDepartures.size());

      }

    @Test
    void setDelayThroughRegister() {
        TrainDepartureRegister tdr = new TrainDepartureRegister();
        TrainDeparture trainDepartureNumber1 = new TrainDeparture(LocalTime.of(15,30), "test", 1, "test", 1, 1);
        tdr.addTrainDeparture(trainDepartureNumber1);
        tdr.setDelayThroughRegister(1, 2);

        assertEquals(3, trainDepartureNumber1.getDelayedTime());
      }

    @Test
    void setTrackNumberThroughRegister() {
        TrainDepartureRegister tdr = new TrainDepartureRegister();
        TrainDeparture trainDepartureNumber1 = new TrainDeparture(LocalTime.of(15,30), "test", 1, "test", 1, 1);
        tdr.addTrainDeparture(trainDepartureNumber1);
        tdr.setTrackNumberThroughRegister(1, 2);

        assertEquals(2, trainDepartureNumber1.getTrack());
      }

    @Test
    void testToString() {
        TrainDepartureRegister tdr = new TrainDepartureRegister();
        TrainDeparture trainDepartureNumber1 = new TrainDeparture(LocalTime.of(15,30), "test", 1, "test", 1, 1);
        TrainDeparture trainDepartureNumber2 = new TrainDeparture(LocalTime.of(15,30), "test", 2, "test", 2, 2);
        tdr.addTrainDeparture(trainDepartureNumber1);
        tdr.addTrainDeparture(trainDepartureNumber2);

        assertEquals(TrainDeparture.HEADER + "\n" + trainDepartureNumber1.toString() + "\n" + trainDepartureNumber2+ "\n" , tdr.toString());
      }
}