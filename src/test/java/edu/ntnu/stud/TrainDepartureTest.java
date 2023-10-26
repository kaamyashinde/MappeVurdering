package edu.ntnu.stud;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TrainDepartureTest {

    @Test
    void testToString() {
    TrainDeparture trainDeparture = new TrainDeparture(1, "test", 1, "test", 1, 1, 1);
        assertEquals("| 1          | test       | 1          | test                 | 1     | 1          | 1     |\n" +
                "+------------+------------+------------+----------------------+-------+------------+-------+", trainDeparture.toString());
      }

  @Test
  void printTableHeader() {
    TrainDeparture trainDeparture = new TrainDeparture(1, "test", 1, "test", 1, 1, 1);
    assertEquals(TrainDeparture.HEADER, TrainDeparture.printTableHeader());
  }
}