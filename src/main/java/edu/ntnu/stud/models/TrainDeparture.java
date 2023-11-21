package edu.ntnu.stud;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a train departure.
 *
 * <p>This class provides a model for storing information about train departures. It includes
 * attributes such as departure time, train line, departure ID, destination, delay and track.
 *
 * <p>All attributes are immutable except for delay, delayed Time and track.
 *
 * <p><strong>Note:</strong>This class does not perform input validation.
 */
public class TrainDeparture {
  /** Departure time for the train. */
  private final LocalTime departureTime;

  /** Departure time formatted as string. */
  private final String departureTimeFormatted;

  /** Name of the train line. */
  private final String trainLine;

  /** Unique identifier for the train. */
  private final int departureId;

  /** Place the train will arrive at. */
  private final String destination;

  /** Amount of time the train is delayed by. */
  private int delay;

  /** Departure time plus the delay. */
  private LocalTime delayedTime;

  /** The track the train is on. */
  private int track;

  /** The formatter for the delay time. */
  private static final DateTimeFormatter DELAY_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  /** The formatter for the departure time. */
  private static final DateTimeFormatter DEPARTURE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  /**
   * Constructs a new train departure with the specified details.
   *
   * @param departureTime local time value representing the time it leaves the station
   * @param trainLine The name of the train line
   * @param departureId The unique identifier for the train
   * @param destination Place the train will arrive at
   * @param delay Amount of time the train is delayed
   * @param track The track the train is on
   */
  public TrainDeparture(
      LocalTime departureTime,
      String trainLine,
      int departureId,
      String destination,
      int delay,
      int track)
      throws IllegalArgumentException, NullPointerException {
    if (departureTime == null) {
      throw new NullPointerException(
          "The departure time cannot be null. Please enter a value for departure time");
    }
    if (trainLine.isBlank()) {
      throw new IllegalArgumentException(
          "No Train line has been detected. Please enter a value for train line");
    }

    if (departureId < 1) {
      throw new IllegalArgumentException("The train ID must be a positive whole number.");
    }
    if (destination.isBlank()) {
      throw new IllegalArgumentException(
          "No destination has been detected. Please enter the destination");
    }
    this.departureTime = departureTime;
    this.departureTimeFormatted = this.departureTime.format(DEPARTURE_FORMATTER);
    this.trainLine = trainLine;
    this.departureId = departureId;
    this.destination = destination;
    setDelayAndDelayTime(delay);
    setTrack(track);
  }

  /**
   * Retrieves the departure time for the train.
   *
   * @return The departure time
   */
  public LocalTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Retrieves the departure time formatted as a string.
   *
   * @return The departure time formatted as a string
   */
  public String getDepartureTimeFormatted() {
    return departureTimeFormatted;
  }

  /**
   * Retrieves the name of the train line.
   *
   * @return The name of the train line
   */
  public String getTrainLine() {
    return trainLine;
  }

  /**
   * Retrieves the unique identifier for the train.
   *
   * @return The unique identifier for the train
   */
  public int getDepartureId() {
    return departureId;
  }

  /**
   * Retrieves the destination for the train.
   *
   * @return The destination for the train
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Retrieves the delay for the train.
   *
   * @return The delay for the train
   */
  public int getDelay() {
    return delay;
  }

  /**
   * Retrieves the delayed time for the train.
   *
   * @return The delayed time for the train
   */
  public LocalTime getDelayedTime() {
    return delayedTime;
  }

  /**
   * Retrieves the delayed time formatted as a string.
   *
   * @return The delayed time formatted as a string
   */
  public String getDelayedTimeFormatted() {
    return delayedTime.format(DELAY_FORMATTER);
  }

  /**
   * Retrieves the track number for the train.
   *
   * @return The track number for the train
   */
  public int getTrack() {
    return track;
  }

  /**
   * Assigns a delay in minutes to the train departure.
   *
   * @param newDelay the delay in minutes
   * @throws IllegalArgumentException if the delay is negative
   */
  public void setDelayAndDelayTime(int newDelay) {
    if (newDelay < 0) {
      throw new IllegalArgumentException("The delay must be a positive whole number.");
    }
    this.delay = newDelay;
    this.delayedTime = departureTime.plusMinutes(delay);
  }

  /**
   * Assigns a track number to the departure.
   *
   * @param track the track number
   * @throws IllegalArgumentException if the track number is not between 1 and 15
   */
  public void setTrack(int track) {
    if (track < -1 || track == 0 || track > 15) {
      throw new IllegalArgumentException(
          "There are 15 tracks at the station. Please enter a value for track between 1 and 15");
    }
    this.track = track;
  }

  /**
   * Retrieves a formatted string representation of the train departure.
   *
   * @return The formatted string with all attributes
   */
  @Override
  public String toString() {
    String formattedDestination =
        Character.toUpperCase(destination.charAt(0)) + destination.substring(1).toLowerCase();
    return String.format(
        "| %-10s | %-10s | %-10d | %-20s | %-10s | %-5s |",
        departureTimeFormatted,
        trainLine,
        departureId,
        formattedDestination,
        (delay > 0) ? delayedTime : " ",
        (track == -1) ? " " : track);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TrainDeparture that = (TrainDeparture) o;
    return departureId == that.departureId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(departureId);
  }
}
