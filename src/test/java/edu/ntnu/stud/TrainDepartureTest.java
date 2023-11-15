package edu.ntnu.stud;

import java.time.DateTimeException;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This file runs JUnit tests on the TrainDeparture entity class to test the methods that it has.
 * The methods that are tested are the getters, setters and the toString method. The tests are run
 * on four different train departures - each testing a different condition on the attributes.
 */
class TrainDepartureTest {
  private TrainDeparture trainDeparture1;

  private TrainDeparture trainDeparture2;
  private TrainDeparture trainDeparture3;
  private TrainDeparture trainDeparture4;
  // private TrainDeparture trainDeparture5;

  private static final String TRAIN_LINE = "L123";
  private static final String DESTINATION = "Kristiansand";

  /** This method is run before each test to set up the objects that are used in the tests. */
  @BeforeEach
  public void setup() {
    trainDeparture1 = new TrainDeparture(LocalTime.of(10, 30), TRAIN_LINE, 1, DESTINATION, 2, -1);
    trainDeparture2 = new TrainDeparture(LocalTime.of(11, 30), TRAIN_LINE, 2, DESTINATION, 5, 1);
    trainDeparture3 = new TrainDeparture(LocalTime.of(23, 59), TRAIN_LINE, 3, DESTINATION, 0, 5);
    trainDeparture4 = new TrainDeparture(LocalTime.of(3, 30), TRAIN_LINE, 4, DESTINATION, 2, -1);
    // trainDeparture5 = new TrainDeparture(LocalTime.of(-1, 23), "", -6, " ", -7, -89);
  }

  /** Testing the toString method */
  @Test
  @DisplayName("Testing the toString method")
  void testToString() {
    assertEquals(
        "| 10:30      | "
            + TRAIN_LINE
            + "       | 1          | Kristiansand         | 10:32      |       |",
        trainDeparture1.toString());
    assertEquals(
        "| 11:30      | "
            + TRAIN_LINE
            + "       | 2          | Kristiansand         | 11:35      | 1     |",
        trainDeparture2.toString());
    assertEquals(
        "| 23:59      | "
            + TRAIN_LINE
            + "       | 3          | Kristiansand         |            | 5     |",
        trainDeparture3.toString());
  }

  /** Testing the getters of the train departure entity class */
  @Nested
  @DisplayName("Testing the getters")
  class TestingTheGetters {
    /** Testing the getDepartureTime method with valid input */
    @Test
    @DisplayName("Getting the departure time formatted with valid input")
    void getDepartureTimeFormattedWithValidInput() {
      String expected = "10:30";
      String outcome = trainDeparture1.getDepartureTimeFormatted();
      assertEquals(expected, outcome);
    }

    /** Testing the getDepartureTime method with a time that is before 00:00 */
    @Test
    @DisplayName("Getting the departure time formatted with invalid input")
    void getDepartureTimeFormattedWithInvalidInput() {
      assertThrows(
          DateTimeException.class,
          () -> new TrainDeparture(LocalTime.of(-10, 30), "l", 1, "Oslo", 2, 1));
      assertThrows(DateTimeException.class, () -> new TrainDeparture(null, "l", 1, "Oslo", 2, 1));
    }

    /** Testing the train line method with valid input */
    @Test
    @DisplayName("Getting the train line with valid input")
    void getTrainLineWithValidInput() {
      assertEquals(TRAIN_LINE, trainDeparture1.getTrainLine());
    }

    /** Testing the train line method with an empty string */
    @Test
    @DisplayName("Getting the train line with invalid input")
    void getTrainLineWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(LocalTime.of(10, 30), "", 1, "Oslo", 2, 1));
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(LocalTime.of(10, 30), null, 1, "Oslo", 2, 1));
    }

    /** Testing the train ID method with valid input */
    @Test
    @DisplayName("Getting the train ID with valid input")
    void getTrainIdWithValidInput() {
      assertEquals(1, trainDeparture1.getTrainId());
    }

    /** Testing the train ID method with a negative value */
    @Test
    @DisplayName("Getting the train ID with invalid input")
    void getTrainIdWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(LocalTime.of(10, 30), "l", -9, "Oslo", 2, 1));
    }

    /** Testing the destination method with valid input */
    @Test
    @DisplayName("Testing the getDestination method with a valid input")
    void getDestinationWithValidInput() {
      assertEquals(DESTINATION, trainDeparture1.getDestination());
    }

    /** Testing the destination method with an empty string */
    @Test
    @DisplayName("Testing the getDestination method with an invalid input")
    void getDestinationWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(LocalTime.of(10, 30), "l", 1, "", 2, 1));
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(LocalTime.of(10, 30), "l", 1, null, 2, 1));
    }

    /** Testing the getDelay method with a valid input */
    @Test
    @DisplayName("Testing the getDelay method with a valid input")
    void getDelayWithValidInput() {
      assertEquals(2, trainDeparture1.getDelay());
      assertEquals(0, trainDeparture3.getDelay());
    }

    /** Testing the getDelay method with a negative value */
    @Test
    @DisplayName("Testing the getDelay method with an invalid input")
    void getDelayWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(LocalTime.of(10, 30), "l", 1, "Oslo", -8, 1));
    }

    /** Testing the getDelayedTimeFormatted method with valid input */
    @Test
    @DisplayName("Getting the delayed time formatted with valid input")
    void getDelayedTimeFormattedWithValidInput() {
      assertEquals("11:35", trainDeparture2.getDelayedTimeFormatted());
    }

    /** Testing the getTrack method with a valid input */
    @Test
    void getTrackWithValidInput() {
      assertEquals(-1, trainDeparture4.getTrack());
      assertEquals(1, trainDeparture2.getTrack());
    }

    /** Testing the getTrack method with a negative number that is less than -1 */
    @Test
    @DisplayName("Getting the track with invalid input")
    void getTrackWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(LocalTime.of(10, 30), "l", 1, "Oslo", 0, -8));
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(LocalTime.of(10, 30), "l", 1, "Oslo", 0, 16));
    }
  }

  /** Testing the setters of the train departure entity class */
  @Nested
  @DisplayName("Testing the setters")
  class TestingTheSetters {
    /** Testing the setDelayAndDelayTime method with valid input */
    @Test
    @DisplayName("Setting the delay with valid input")
    void setDelayWithValidInput() {
      trainDeparture4.setDelayAndDelayTime(2);
      assertEquals(2, trainDeparture4.getDelay());
    }

    /** Testing the setDelayAndDelayTime method with invalid input */
    @Test
    @DisplayName("Setting the delay with invalid input")
    void setDelayWithInvalidInput() {
      TrainDeparture td = new TrainDeparture(LocalTime.of(10, 30), "l", 1, "Oslo", 0, 2);
      assertThrows(IllegalArgumentException.class, () -> td.setDelayAndDelayTime(-1));
    }

    /** Testing the setTrack method with valid input */
    @Test
    @DisplayName("Setting the track with valid input")
    void setTrackWithValidInput() {
      trainDeparture4.setTrack(2);
      assertEquals(2, trainDeparture4.getTrack());
    }

    /** Testing the setTrack method with a negative number */
    @Test
    @DisplayName("Setting the track with valid input")
    void setTrackWithInvalidInput() {
      TrainDeparture td = new TrainDeparture(LocalTime.of(10, 30), "l", 1, "Oslo", 0, 4);
      assertThrows(IllegalArgumentException.class, () -> td.setTrack(-8));
    }
  }
}
