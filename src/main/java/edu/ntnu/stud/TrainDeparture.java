package edu.ntnu.stud;

import java.time.DateTimeException;
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
  // friendly)
  private LocalTime delayedTime;
  private int track;
  private static final DateTimeFormatter DELAY_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
  private static final DateTimeFormatter DEPARTURE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  /**
   * The constructor for the train departure class. It takes in the following parameters: -
   *
   * @param departureTime time the train leaves
   * @param trainLine the name of the train line
   * @param trainId the unique identifier for the train
   * @param destination place the train will arrive at
   * @param delay amount of time the train is delayed
   * @param track the track the train is on
   */
  public TrainDeparture(
      LocalTime departureTime,
      String trainLine,
      int trainId,
      String destination,
      int delay,
      int track)
      throws IllegalArgumentException {
    validateInput(departureTime, trainLine, trainId, destination, delay, track);
    this.departureTime = departureTime;
    this.departureTimeFormatted = this.departureTime.format(DEPARTURE_FORMATTER);
    this.trainLine = trainLine;
    this.trainId = trainId;
    this.destination = destination;
    this.delay = delay;
    this.delayedTime = this.departureTime.plusMinutes(delay);
    this.track = track;
  }

  /**
   * This method will take in the same parameters as the constructor and validate them by throwing
   * an error if an Illegal Argument is detected.
   *
   * @param departureTime time the train leaves
   * @param trainLine the name of the train line
   * @param trainId the unique identifier for the train
   * @param destination place the train will arrive at
   * @param delay amount of time the train is delayed
   * @param track the track the train is on
   */
  private void validateInput(
      LocalTime departureTime,
      String trainLine,
      int trainId,
      String destination,
      int delay,
      int track) {
    if (departureTime == null) {
      throw new IllegalArgumentException(
          " The time cannot be null. Please enter a value for the time");
    }
    if (departureTime.isBefore(LocalTime.of(0, 0)) || departureTime.isAfter(LocalTime.of(23, 59))) {
      throw new DateTimeException("The time must be between 00:00 and 23:59. Please try again.");
    }
    if (trainLine == null || trainLine.isEmpty()) {
      throw new IllegalArgumentException(
          "The train line cannot be null or empty. Please try again.");
    }
    if (trainId < 0) {
      throw new IllegalArgumentException("The train id cannot be negative. Please try again.");
    }
    if (destination == null || destination.isEmpty()) {
      throw new IllegalArgumentException(
          "The destination cannot be null or empty. Please try again.");
    }
    if (delay < 0) {
      throw new IllegalArgumentException("The delay cannot be negative. Please try again.");
    }
    if (track < -1) {
      throw new IllegalArgumentException("The track cannot be negative. Please try again.");
    }
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
    return delayedTime.format(DELAY_FORMATTER);
  }

  public int getTrack() {
    return track;
  }

  // set methods:

  /**
   * This method will set the delayed time for a train departure
   *
   * @param newDelay the delay in minutes
   */
  public void setDelayAndDelayTime(int newDelay) {
    if (newDelay < 0) {
      throw new IllegalArgumentException("The delay cannot be negative. Please try again.");
    }
    this.delay = newDelay;
    this.delayedTime = departureTime.plusMinutes(delay);
  }

  /**
   * This method will set the track number for a train departure
   *
   * @param track the track number
   */
  public void setTrack(int track) {
    if (track < -1) {
      throw new IllegalArgumentException("The track cannot be negative. Please try again.");
    }
    this.track = track;
  }

  /**
   * This method formats the attributes of the object to a string that can be printed out as the
   * rows in the final table
   *
   * @return the formatted string
   */
  @Override
  public String toString() {
    return String.format(
        "| %-10s | %-10s | %-10d | %-20s | %-10s | %-5s |",
        departureTimeFormatted,
        trainLine,
        trainId,
        destination,
        (delay > 0) ? delayedTime : " ",
        (track == -1) ? " " : track);
  }
}
