package edu.ntnu.stud.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests on the TrainDeparture entity class to test the methods that it has. The methods that
 * are tested are the getters, setters and the toString method. The tests are run on four different
 * train departures - each testing a different condition on the attributes.
 */
class TrainDepartureTest {
  private TrainDeparture trainDeparture1;
  private TrainDeparture trainDeparture2;
  private TrainDeparture trainDeparture3;
  private TrainDeparture trainDeparture4;
  private static final String TRAIN_LINE = "L123";
  private static final String DESTINATION = "KrIStiansand";

  /** Creates instances of the object train departure before each test runs. */
  @BeforeEach
  public void setup() {
    trainDeparture1 = new TrainDeparture(10, 30, TRAIN_LINE, 1, DESTINATION, 2, -1);
    trainDeparture2 = new TrainDeparture(11, 30, TRAIN_LINE, 2, DESTINATION, 5, 1);
    trainDeparture3 = new TrainDeparture(23, 59, TRAIN_LINE, 3, DESTINATION, 0, 5);
    trainDeparture4 = new TrainDeparture(3, 30, TRAIN_LINE, 4, DESTINATION, 2, -1);
  }

  /** Testing the toString method. */
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

  @Nested
  @DisplayName("Testing the formatters.")
  class TestingTheTimeFormatters {
    /** Testing the getDepartureTime method with valid input. */
    @Test
    @DisplayName("Getting the departure time formatted with valid input")
    void getDepartureTimeFormattedWithValidInput() {
      String expected = "10:30";
      String outcome = trainDeparture1.getDepartureTime().toString();
      assertEquals(expected, outcome);
    }

    /** Testing the getDelayedTimeFormatted method with valid input */
    @Test
    @DisplayName("Getting the delayed time formatted with valid input")
    void getDelayedTimeFormattedWithValidInput() {
      assertEquals("11:35", trainDeparture2.getDelayedTime().toString());
    }
  }

  /** Testing the getters of the train departure entity class. */
  @Nested
  @DisplayName("Testing the accessor methods")
  class TestingErrorHandlingOfAccessors {
    /** Testing the train line method with an an empty, blank and null string. */
    @Test
    @DisplayName("Getting the train line with invalid input")
    void getTrainLineWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(10, 30, "", 1, "Oslo", 2, 1));
      assertThrows(
          NullPointerException.class,
          () -> new TrainDeparture(10, 30, null, 1, "Oslo", 2, 1));
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(10, 30, " ", 1, "Oslo", 2, 1));
    }

    /** Testing the train ID method with a negative value */
    @Test
    @DisplayName("Getting the train ID with invalid input")
    void getDepartureIdWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(10, 30, "l", -9, "Oslo", 2, 1));
    }

    /** Testing the destination method with an empty, null and blank string. */
    @Test
    @DisplayName("Testing the getDestination method with an invalid input")
    void getDestinationWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(10, 30, "l", 1, "", 2, 1));
      assertThrows(
          NullPointerException.class,
          () -> new TrainDeparture(10, 30, "l", 1, null, 2, 1));
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(10, 30, "l", 1, " ", 2, 1));
    }

    /** Testing the getDelay method with a negative value. */
    @Test
    @DisplayName("Testing the getDelay method with an invalid input")
    void getDelayWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(10, 30, "l", 1, "Oslo", -8, 1));
    }

    /** Testing the getTrack method with a negative number that is less than -1. */
    @Test
    @DisplayName("Getting the track with invalid input")
    void getTrackWithInvalidInput() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TrainDeparture(10, 30, "l", 1, "Oslo", 0, -8));
    }
  }

  /** Testing the setters of the train departure entity class. */
  @Nested
  @DisplayName("Testing the setters")
  class TestingTheSetters {
    /** Testing the setDelayAndDelayTime method with valid input. */
    @Test
    @DisplayName("Setting the delay with valid input")
    void setDelayWithValidInput() {
      trainDeparture4.setDelayAndDelayTime(2);
      assertEquals("03:32", trainDeparture4.getDelayedTime().toString());
    }

    /** Testing the setDelayAndDelayTime method with a negative number. */
    @Test
    @DisplayName("Setting the delay with invalid input")
    void setDelayWithInvalidInput() {
      TrainDeparture td = new TrainDeparture(10, 30, "l", 1, "Oslo", 0, 2);
      assertThrows(IllegalArgumentException.class, () -> td.setDelayAndDelayTime(-1));
    }

    /** Testing the setTrack method with valid input. */
    @Test
    @DisplayName("Setting the track with valid input")
    void setTrackWithValidInput() {
      trainDeparture4.setTrack(2);
      assertEquals(2, trainDeparture4.getTrack());
    }

    /** Testing the setTrack method with a negative number. */
    @Test
    @DisplayName("Setting the track with invalid input")
    void setTrackWithInvalidInput() {
      TrainDeparture td = new TrainDeparture(10, 30, "l", 1, "Oslo", 0, 4);
      assertThrows(IllegalArgumentException.class, () -> td.setTrack(-8));
    }
  }
}
