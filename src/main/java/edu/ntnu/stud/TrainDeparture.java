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
  private final LocalTime departureTime;
  private final String departureTimeFormatted;
  private final String trainLine;
  private final int trainId;
  private final String destination;
  private int delay;
  private LocalTime delayedTime;
  private int track;

  // Defining static String constants that will be used in the toString method to avoid repetition
  private static final String SEPARATOR =
      "------------------------------------------------------------------------------------";
  private static final String HEADER =
      SEPARATOR
          + "\n"
          + "| Departure  | Train Line | Train ID   | Destination          | Delay Time | Track |"
          + "\n"
          + SEPARATOR;

  /**
   * The constructor for the train departure class. It takes in the following parameters: -
   *
   * @param departureTime -> invalid data set to 00:00
   * @param trainLine -> invalid data set to "Unknown"
   * @param trainId -> invalid data set to -2
   * @param destination -> invalid data set to "Unknown"
   * @param delay -> invalid data set to -2
   * @param track -> invalid data set to -2
   */
  public TrainDeparture(
      LocalTime departureTime,
      String trainLine,
      int trainId,
      String destination,
      int delay,
      int track) {
    this.departureTime = departureTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    this.departureTimeFormatted =
        this.departureTime.format(
            formatter); // do not need an invalid data checker coz variable depends on departureTime
    this.trainLine =
        (trainLine == null || trainLine.isEmpty() || trainLine.isBlank()) ? "Unknown" : trainLine;
    this.trainId = (trainId < 0) ? -2 : trainId;
    this.destination =
        (destination == null || destination.isEmpty() || destination.isBlank())
            ? "Unknown"
            : destination;
    this.delay = (delay < 0) ? -2 : delay;
    this.delayedTime = this.departureTime.plusMinutes(delay);
    this.track = (track < -1) ? -2 : track;
  }



  // get methods:
  public LocalTime getDepartureTime() {
    return departureTime;
  }

  public String getDepartureTimeFormatted() {
    return departureTimeFormatted;
  }

  public String getTrainLine() {
    return trainLine;
  }

  public int getTrainId() {
    return trainId;
  }

  public String getDestination() {
    return destination;
  }

  public int getDelay() {
    return delay;
  }

  public LocalTime getDelayedTime() {
    return delayedTime;
  }

  public String getDelayedTimeFormatted() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    return delayedTime.format(formatter);
  }

  public int getTrack() {
    return track;
  }

  // set methods:
  public void setDelayAndDelayTime(int newDelay) {
    this.delay = newDelay;
    this.delayedTime = departureTime.plusMinutes(delay);
  }

  public void setTrack(int track) {
    this.track = track;
  }

  // Additional method that prints out the table header - will be used in the register methods
  public static String getTableHeader() {
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
              departureTimeFormatted, trainLine, trainId, destination, delayedTime, " ")
          + "\n"
          + SEPARATOR;
    } else if (delay == 0) { // The scenario where the train departure is on time
      return String.format(
              "| %-10s | %-10s | %-10d | %-20s | %-10s | %-5d |",
              departureTimeFormatted, trainLine, trainId, destination, " ", track)
          + "\n"
          + SEPARATOR;
    } else
      return String
              .format( // The scenario where the train departure is delayed and has a track number
                  // assigned
                  "| %-10s | %-10s | %-10d | %-20s | %-10s | %-5d |",
                  departureTimeFormatted, trainLine, trainId, destination, delayedTime, track)
          + "\n"
          + SEPARATOR;
  }
}
