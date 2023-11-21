package edu.ntnu.stud.models;

import edu.ntnu.stud.utils.ParameterValidation;

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
  private final LocalTime departureTime;

  private final String departureTimeFormatted;

  private final String trainLine;

  private final int departureId;

  private final String destination;

  private int delay;

  private LocalTime delayedTime;

  private int track;

  private static final DateTimeFormatter DELAY_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

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
    ParameterValidation.notBlankValidation(
        trainLine, "No Train line has been detected. Please enter a value for train line");
    ParameterValidation.notBlankValidation(
        destination, "No destination has been detected. Please enter the destination");
    ParameterValidation.positiveIntegerValidation(
        departureId, "The departure ID cannot be negative");
    this.departureTime = departureTime;
    this.departureTimeFormatted = this.departureTime.format(DEPARTURE_FORMATTER);
    this.trainLine = trainLine;
    this.departureId = departureId;
    this.destination = destination;
    setDelayAndDelayTime(delay);
    setTrack(track);
  }

  // Copy constructor params TrainDeparture
  public TrainDeparture(TrainDeparture trainDeparture) {
    this.departureTime = trainDeparture.departureTime;
    this.departureTimeFormatted = trainDeparture.departureTimeFormatted;
    this.trainLine = trainDeparture.trainLine;
    this.departureId = trainDeparture.departureId;
    this.destination = trainDeparture.destination;
    this.delay = trainDeparture.delay;
    this.delayedTime = trainDeparture.delayedTime;
    this.track = trainDeparture.track;
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
    ParameterValidation.positiveIntegerValidation(newDelay, "The delay cannot be negative");
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
    ParameterValidation.validateTrack(
        track,
        "There are 15 tracks at the station. Please enter a value for track between 1 and 15");
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
}
