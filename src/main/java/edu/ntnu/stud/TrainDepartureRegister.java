package edu.ntnu.stud;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This file contains the class which handles the register of the train departures - aka the list
 * that contains all the train departures. There are two main get methods that filter the list based
 * on the Train id and the Destination. There are also two set methods that set the delay and track
 * number of the train departure. Apart from these setters and getters, there are methods to perform
 * other tasks such as the toString to print out the whole table and the addTrainDeparture method to
 * add a new train departure to the list.
 */
public class TrainDepartureRegister {
  ArrayList<TrainDeparture> allTrainDepartures;

  // constructor to initialize the train departure register
  public TrainDepartureRegister() {
    allTrainDepartures = new ArrayList<>();
  }

  /**
   * The method adds the train departure to the register.
   *
   * @param trainDeparture the train departure instance to be added to the register
   */
  public void addTrainDeparture(TrainDeparture trainDeparture) {
    allTrainDepartures.add(trainDeparture);
    this.sortTheTrainDepartureRegister();
  }

  /**
   * The method returns the train departures in the register as a string.
   *
   * @return the train departures in the register as a string
   */
  @Override
  public String toString() {
    return allTrainDepartures.stream()
        .map(TrainDeparture::toString)
        .collect(Collectors.joining("\n"));
  }

  /** Sort the train departures based on the departure time. */
  public void sortTheTrainDepartureRegister() {
    allTrainDepartures.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
  }

  /**
   * The method checks whether the delayed time os the train departure is before the time given.
   * Then it removes the train departure from teh register
   *
   * @param theTime the time to check if the train departure is before
   */
  public void removeTrainDepartureBeforeTime(LocalTime theTime) throws IllegalArgumentException {
    Objects.requireNonNull(theTime, "The time cannot be null");
    if (theTime.isBefore(LocalTime.of(0, 0)) || theTime.isAfter(LocalTime.of(23, 59))) {
      throw new DateTimeException("The time must be between 00:00 and 23:59. Please try again.");
    }
    allTrainDepartures.removeIf(td -> td.getDelayedTime().isBefore(theTime));
  }

  /**
   * The method checks whether the train ID is taken by another train departure in the register.
   *
   * @param trainIdUnique ID of the train departure
   * @return true if the train departure exists in the register, false otherwise
   */
  public boolean checkIfExists(int trainIdUnique) throws IllegalArgumentException {
    if (trainIdUnique < 0) {
      throw new IllegalArgumentException("The train ID cannot be negative");
    }
    return allTrainDepartures.stream().anyMatch(td -> td.getTrainId() == trainIdUnique);
  }

  // get-methods:
  /**
   * The method filters the list of train departures based on the train ID and returns the train If
   * none found, it returns null.
   *
   * @param trainId ID of the train being searched for
   * @return the train departure based on the train id as a string
   */
  public String getTrainDepartureBasedOnTrainId(int trainId) throws IllegalArgumentException {
    if (trainId < 0) {
      throw new IllegalArgumentException("The train ID cannot be negative");
    }
    return allTrainDepartures.stream()
        .filter(td -> td.getTrainId() == trainId)
        .findFirst()
        .map(TrainDeparture::toString)
        .orElse(null);
  }

  /**
   * The method filters the list of train departures based on the destination and returns the train
   * departures If none found, it returns null.
   *
   * @param destination to identify the train departure that is being searched for
   * @return the train departure based on the destination as a string
   */
  public String getTrainDepartureBasedOnDestination(String destination)
      throws IllegalArgumentException {
    Objects.requireNonNull(
        destination, "The destination cannot be null. Please enter a value for destination");
    if (destination.isBlank() || destination.isEmpty()) {
      throw new IllegalArgumentException(
          "No destination has been detected. Please enter the destination");
    }

    List<String> trainDeparturesToSameDestination =
        allTrainDepartures.stream()
            .filter(td -> td.getDestination().equalsIgnoreCase(destination))
            .map(TrainDeparture::toString)
            .toList();

    return (trainDeparturesToSameDestination.isEmpty())
        ? null
        : String.join("\n", trainDeparturesToSameDestination);
  }

  // set-methods:
  /**
   * The method sets the delay for a train departure based on the train ID.
   *
   * @param trainId unique identifier of the train departure
   * @param delay the delay in minutes
   */
  public void setDelayThroughRegister(int trainId, int delay) {
    if (trainId < 0) {
      throw new IllegalArgumentException("The train ID cannot be negative");
    }
    if (delay < 0) {
      throw new IllegalArgumentException("The delay cannot be negative");
    }
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getTrainId() == trainId)
        .forEach(traDep -> traDep.setDelayAndDelayTime(delay));
  }

  /**
   * The method sets the track number for a train departure based on the train ID.
   *
   * @param trainId unique identifier of the train departure
   * @param trackNum the track number
   */
  public void setTrackNumberThroughRegister(int trainId, int trackNum)
      throws IllegalArgumentException {
    if (trainId < 0) {
      throw new IllegalArgumentException("The train ID cannot be negative");
    }
    if (trackNum < 1 || trackNum > 15) {
      throw new IllegalArgumentException(
          "There are 15 tracks at the station. Please enter a value for track between 1 and 15");
    }
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getTrainId() == trainId)
        .forEach(traDep -> traDep.setTrack(trackNum));
  }
}
