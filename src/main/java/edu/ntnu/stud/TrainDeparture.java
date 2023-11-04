package edu.ntnu.stud;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This file contains the class for the train departure. The information that is to be displayed for
 * each train departure is the following: - Departure Time - Train Line - Train ID - Destination -
 * Delayed Time - Track Number. The class contains get-methods for all attributes, set-methods for
 * the delay information and track attribute, a to-string method and a static method to print out
 * the header for the main table that will display the train departures.
 */
public class TrainDeparture {
  private final LocalTime DEPARTURE_TIME;
  private final String departureTimeFormatted;
  private final String TRAIN_LINE;
  private final int TRAIN_ID;
  private final String DESTINATION;
  private int delay;
  private LocalTime delayedTime;
  private String delayedTimeFormatted;
  private int track;

  // Defining static String constants that will be used in the toString method to avoid repetition
  static final String SEPARATOR =
      "------------------------------------------------------------------------------------";
  static final String HEADER =
      SEPARATOR
          + "\n"
          + "| Departure  | Train Line | Train ID   | Destination          | Delay Time | Track |"
          + "\n"
          + SEPARATOR;

  // Constructor to initialize the train departure object
  public TrainDeparture(
      LocalTime departureTime,
      String trainLine,
      int trainId,
      String destination,
      int delay,
      int track) {
    this.DEPARTURE_TIME = departureTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    this.departureTimeFormatted = DEPARTURE_TIME.format(formatter);
    this.TRAIN_LINE = trainLine;
    this.TRAIN_ID = trainId;
    this.DESTINATION = destination;
    this.delay = delay;
    this.delayedTime = DEPARTURE_TIME.plusMinutes(delay);
    this.delayedTimeFormatted = delayedTime.format(formatter);
    this.track = track;
  }

  // get methods:
  public LocalTime getDepartureTime() {
    return DEPARTURE_TIME;
  }

  public String getDepartureTimeFormatted() {
    return departureTimeFormatted;
  }

  public String getTrainLine() {
    return TRAIN_LINE;
  }

  public int getTrainId() {
    return TRAIN_ID;
  }

  public String getDestination() {
    return DESTINATION;
  }

  public int getDelay() {
    return delay;
  }

  public LocalTime getDelayedTime() {
    return delayedTime;
  }

  public String getDelayedTimeFormatted() {
    return delayedTimeFormatted;
  }

  public int getTrack() {
    return track;
  }

  // set methods:
  public void setDelayAndDelayTime(int delay) {
    this.delay = delay;
    this.delayedTime = DEPARTURE_TIME.plusMinutes(delay);
  }

  public void setTrack(int track) {
    this.track = track;
  }

  // Additional method that prints out the table header - will be used in the register methods
  public static String printTableHeader() {
    // Print the table header
    return HEADER;
  }

  // to-string method
  @Override
  public String toString() {

    if (track
        == -1) { // The scenario where there is no track number assigned to the train departure
      return String.format(
              // Define the format for each row of the table
              "| %-10s | %-10s | %-10d | %-20s | %-10s | %-5s |",
              departureTimeFormatted, TRAIN_LINE, TRAIN_ID, DESTINATION, delayedTime, " ")
          + "\n"
          + SEPARATOR;
    } else if (delay == 0) { // The scenario where the train departure is on time
      return String.format(
              "| %-10s | %-10s | %-10d | %-20s | %-10s | %-5d |",
              departureTimeFormatted, TRAIN_LINE, TRAIN_ID, DESTINATION, " ", track)
          + "\n"
          + SEPARATOR;
    } else
      return String
              .format( // The scenario where the train departure is delayed and has a track number
                       // assigned
                  "| %-10s | %-10s | %-10d | %-20s | %-10s | %-5d |",
                  departureTimeFormatted, TRAIN_LINE, TRAIN_ID, DESTINATION, delayedTime, track)
          + "\n"
          + SEPARATOR;
  }
}
