package edu.ntnu.stud.models;

import edu.ntnu.stud.utils.ParameterValidation;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a train departure.
 *
 * <p>Includes attributes such as departure time, train line, departure ID, destination, delay and
 * track.
 *
 * <p>All attributes are immutable except for delay and track. Class includes accessor methods for
 * all attributes and mutator methods for the mutable attributes.
 *
 * <p>Input validation is performed on all parameters of the constructor before initialising the
 * attributes.
 *
 * <p><Strong>Goal: </Strong>Act as a model for storing information about train departures.
 *
 * @version 1.0.3
 * @author 10083
 * @since 0.1
 */
public class TrainDeparture {
  private final int departureId;
  private final LocalTime departureTime;
  private final String destination;
  private final String trainLine;
  private LocalTime delayedTime;
  private int delay;
  private int track;

  /**
   * Constructs a new train departure with the specified details.
   *
   * @param departureTimeInHours int representing the departure time in hours
   * @param departureTimeInMinutes int representing the departure time in minutes
   * @param trainLine string representing the train line
   * @param departureId int representing the unique identifier for the train
   * @param destination string representing the destination for the train
   * @param delay int representing the delay in minutes
   * @param track int representing the track number
   * @throws NullPointerException if the departure time is null
   * @throws IllegalArgumentException has three different conditions:
   *     <ol>
   *       <li>if the Train line or destination is blank
   *       <li>if the departure ID or delay is negative
   *       <li>if the track number is not between 1 and 15
   *     </ol>
   */
  public TrainDeparture(
      int departureTimeInHours,
      int departureTimeInMinutes,
      String trainLine,
      int departureId,
      String destination,
      int delay,
      int track)
      throws NullPointerException, IllegalArgumentException {
    ParameterValidation.validateHour(departureTimeInHours);
    ParameterValidation.validateMinute(departureTimeInMinutes);
    ParameterValidation.notBlankValidation(
        trainLine, "No Train line has been detected. Please enter the train line");
    ParameterValidation.notBlankValidation(
        destination, "No destination has been detected. Please enter the destination");
    ParameterValidation.validateId(departureId);
    this.departureTime = LocalTime.of(departureTimeInHours, departureTimeInMinutes);
    this.trainLine = trainLine;
    this.departureId = departureId;
    this.destination = destination;
    setDelayAndDelayTime(delay);
    setTrack(track);
  }

  /**
   * Constructs a new train departure with the specified details.
   *
   * <p><Strong>Role: </Strong> Used to create a deep copy of the train departure
   *
   * @param trainDeparture the object of the class to be copied
   */
  public TrainDeparture(TrainDeparture trainDeparture) {
    this.departureTime = trainDeparture.departureTime;
    this.trainLine = trainDeparture.trainLine;
    this.departureId = trainDeparture.departureId;
    this.destination = trainDeparture.destination;
    this.delay = trainDeparture.delay;
    this.delayedTime = trainDeparture.delayedTime;
    this.track = trainDeparture.track;
  }

  /**
   * Retrieves the delay for the train.
   *
   * @return The delay for the train
   */
  private int getDelay() {
    return delay;
  }

  /**
   * Retrieves the train line for the train.
   *
   * @return The track number for the train
   */
  private String getTrainLine() {
    return trainLine;
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
   * Retrieves the unique identifier for the train.
   *
   * @return The unique identifier for the train
   */
  public int getDepartureId() {
    return departureId;
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
   * Retrieves the destination for the train.
   *
   * @return The destination for the train
   */
  public String getDestination() {
    return destination;
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
   * @param delay the delay in minutes
   * @throws IllegalArgumentException if the delay is negative
   */
  public void setDelayAndDelayTime(int delay) {
    ParameterValidation.validateDelay(delay);
    this.delay = delay;
    this.delayedTime = departureTime.plusMinutes(this.delay);
  }

  /**
   * Assigns a track number to the departure.
   *
   * @param track the track number
   * @throws IllegalArgumentException if the track number is not between 1 and 15
   */
  public void setTrack(int track) {
    ParameterValidation.validateTrack(track);
    this.track = track;
  }

  /**
   * Retrieves a formatted string representation of the train departure.
   *
   * <p><Strong>PS. </Strong>ChatGPT was used to implement <code>String.format()</code>
   *
   * @return The formatted string with all attributes
   */
  @Override
  public String toString() {
    String formattedDestination =
        Character.toUpperCase(getDestination().charAt(0))
            + getDestination().substring(1).toLowerCase();
    return String.format(
        "| %-10s | %-10s | %-10d | %-20s | %-10s | %-5s |",
        getDepartureTime(),
        getTrainLine(),
        getDepartureId(),
        formattedDestination,
        (getDelay() > 0) ? getDelayedTime() : " ",
        (getTrack() == -1) ? " " : getTrack());
  }

  /**
   * Compares the specified object with this train departure for equality.
   *
   * @param o the object to be compared for equality with this train departure
   * @return true if the specified object is equal to this train departure, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrainDeparture that = (TrainDeparture) o;
    return track == that.track && Objects.equals(delayedTime, that.delayedTime);
  }
}
