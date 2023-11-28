package edu.ntnu.stud.models;

import edu.ntnu.stud.utils.ParameterValidation;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the train departure register containing the instances of the train departures. The
 * instances are stored in an ArrayList.
 *
 * <p><Strong>Goal: </Strong>Act as a model for a train departure register.
 *
 * @author 10083
 * @version 1.1
 * @since 0.2
 */
public class TrainDepartureRegister {
  private final ArrayList<TrainDeparture> allTrainDepartures = new ArrayList<>();

  /** Constructs an object of the class TrainDepartureRegister. */
  public TrainDepartureRegister() {}

  /**
   * Makes a deep copy of the objects in the register.
   *
   * @return arrayList containing deep copies of the departure instances in the register
   */
  private ArrayList<TrainDeparture> deepCopyOfDepartures() {
    ArrayList<TrainDeparture> copyOfAllDepartures = new ArrayList<>();
    allTrainDepartures.forEach(
        trainDeparture -> copyOfAllDepartures.add(new TrainDeparture(trainDeparture)));
    return copyOfAllDepartures;
  }

  /**
   * Removes departures that are not going to the destination from the deep copied list.
   *
   * @param destination to identify the train departure that is being searched for
   * @return arrayList of objects going to the same destination
   */
  public ArrayList<TrainDeparture> returnTrainDeparturesBasedOnDestination(String destination)
      throws IllegalArgumentException, NullPointerException {
    ParameterValidation.notBlankValidation(
        destination, "No destination has been detected. Please enter the destination");
    ArrayList<TrainDeparture> copyOfDepartures = deepCopyOfDepartures();
    copyOfDepartures.removeIf(td -> !td.getDestination().equalsIgnoreCase(destination));
    return copyOfDepartures;
  }

  /**
   * Sorts the train departures based on the departure time.
   *
   * @return the sorted list of train departures
   */
  public ArrayList<TrainDeparture> sortTheTrainDepartureRegister() {
    ArrayList<TrainDeparture> unsortedListOfTrainDepartures = deepCopyOfDepartures();
    unsortedListOfTrainDepartures.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
    return unsortedListOfTrainDepartures;
  }

  /**
   * Checks whether the train ID is taken by another train departure in the register.
   *
   * @param departureIdUnique ID of the train departure
   * @return true if the train departure exists in the register, false otherwise
   */
  public boolean checkIfDepartureIdExists(int departureIdUnique) throws IllegalArgumentException {
    ParameterValidation.validateId(departureIdUnique);
    return allTrainDepartures.stream().anyMatch(td -> td.getDepartureId() == departureIdUnique);
  }

  /**
   * Checks whether or not there is a train that is going to a given destination.
   *
   * @param destination the destination to check if there is a train going to
   * @return true if there is a train going to the destination, false otherwise
   */
  public boolean checkIfDestinationExists(String destination) throws IllegalArgumentException {
    ParameterValidation.notBlankValidation(
        destination, "No destination has been detected. Please try again.");
    return allTrainDepartures.stream()
        .anyMatch(td -> td.getDestination().equalsIgnoreCase(destination));
  }

  /**
   * Finds the total number of departures in the register.
   *
   * @return the number of departures in the register
   */
  public int getNumberOfTrainDepartures() {
    return allTrainDepartures.size();
  }

  /** Calculates the amount of time before the nearest departure leaves the station. */
  public long getTimeTilNextDeparture(LocalTime time) {
    ParameterValidation.validateTime(time);
    ArrayList<TrainDeparture> sortedList = sortTheTrainDepartureRegister();
    if (allTrainDepartures.isEmpty()) {
      return -1;
    }
    LocalTime timeOfNextDeparture = sortedList.get(0).getDelayedTime();

    Duration timeUntilNextDeparture = Duration.between(time, timeOfNextDeparture);
    return timeUntilNextDeparture.toMinutes();
  }

  /** Finds the 50th percentile of the train departures based on the departure time. */
  public String findInterQuartileRange() {
    ArrayList<TrainDeparture> sortedRegister = sortTheTrainDepartureRegister();
    int size = sortedRegister.size();
    int start = size / 4; // 25th percentile
    int end = 3 * size / 4; // 75th percentile

    LocalTime theStart = sortedRegister.get(start).getDepartureTime();
    LocalTime theEnd = sortedRegister.get(end).getDepartureTime();

    return theStart + " - " + theEnd;
  }

  /**
   * Generates a list of all the destinations that the departures in the register are going to.
   *
   * @return the list of destinations
   */
  public String returnAllDestinationsInRegister() {
    return deepCopyOfDepartures().stream()
        .map(TrainDeparture::getDestination)
        .distinct()
        .collect(Collectors.joining(" | "));
  }

  /**
   * Filters the original list for the departureId specified and retrieves information about that
   * train departure.
   *
   * @param departureId ID of the train being searched for
   * @return the train departure object with the specific train ID as a string
   */
  public String returnTrainDepartureBasedOnId(int departureId) throws IllegalArgumentException {
    ParameterValidation.validateId(departureId);
    return deepCopyOfDepartures().stream()
        .filter(td -> td.getDepartureId() == departureId)
        .map(TrainDeparture::toString)
        .collect(Collectors.joining("\n"));
  }

  /**
   * Adds a departure to the register after parameter validation is performed on the arguments.
   *
   * @param departureTime local time value representing the time it leaves the station
   * @param trainLine string representing the train line
   * @param departureId int representing the unique identifier for the train
   * @param destination string representing the destination for the train
   * @param delay int representing the delay in minutes
   * @param track int representing the track number
   * @throws NullPointerException if the departure time is null
   * @throws IllegalArgumentException
   *     <ol>
   *       <li>if the Train line or destination is blank
   *       <li>if the departure ID or delay is negative
   *       <li>if the track number is not between 1 and 15
   *     </ol>
   */
  public void addTrainDeparture(
      LocalTime departureTime,
      String trainLine,
      int departureId,
      String destination,
      int delay,
      int track)
      throws NullPointerException, IllegalArgumentException {
    ParameterValidation.validateTime(departureTime);
    ParameterValidation.notBlankValidation(
        trainLine, "No Train line has been detected. Please enter the train line");
    ParameterValidation.notBlankValidation(
        destination, "No destination has been detected. Please enter the destination");
    ParameterValidation.validateId(departureId);
    ParameterValidation.validateDelay(delay);
    ParameterValidation.validateTrack(track);

    TrainDeparture td =
        new TrainDeparture(departureTime, trainLine, departureId, destination, delay, track);
    checkIfTwoDeparturesLeaveFromSameTrackAtTheSameTime(td);

    allTrainDepartures.add(td);
  }

  /**
   * Edits the delay for a train departure based on the train ID.
   *
   * @param departureId unique identifier of the train departure
   * @param delay the delay in minutes
   */
  public void assignDelay(int departureId, int delay) {
    ParameterValidation.validateId(departureId);
    ParameterValidation.validateDelay(delay);
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getDepartureId() == departureId)
        .forEach(traDep -> traDep.setDelayAndDelayTime(delay));
  }

  /**
   * Edits the track number for a train departure based on the train ID.
   *
   * @param departureId unique identifier of the train departure
   * @param trackNum the track number
   */
  public void assignTrack(int departureId, int trackNum) throws IllegalArgumentException {
    ParameterValidation.validateId(departureId);
    ParameterValidation.validateTrack(trackNum);
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getDepartureId() == departureId)
        .forEach(traDep -> traDep.setTrack(trackNum));
  }

  /** Checks if two departures have the same departure time from the same track. */
  public void checkIfTwoDeparturesLeaveFromSameTrackAtTheSameTime(TrainDeparture td) {
    if (allTrainDepartures.contains(td)) {
      throw new IllegalArgumentException(
          "There already exists a departure with the same departure time that is leaving the same track. Please try again.");
    }
  }

  /**
   * Removes the train departure based on its ID
   *
   * @param departureId the unique ID of the train to be removed
   */
  public void removeTrainDepartureBasedOnId(int departureId) throws IllegalArgumentException {
    ParameterValidation.validateId(departureId);
    allTrainDepartures.removeIf(td -> td.getDepartureId() == departureId);
  }

  /**
   * Removes the train departures that left the station before the system time.
   *
   * @param theTime the time to check if the train departure is before
   */
  public void removeTrainDepartureBeforeTime(LocalTime theTime) throws NullPointerException {
    ParameterValidation.validateTime(theTime);
    if (!allTrainDepartures.isEmpty()) {
      allTrainDepartures.removeIf(td -> td.getDelayedTime().isBefore(theTime));
    }
  }

  /**
   * Collects the train departures in the register as a string.
   *
   * @return the string containing the train departures
   */
  @Override
  public String toString() {
    ArrayList<TrainDeparture> sortedRegister = sortTheTrainDepartureRegister();
    return sortedRegister.stream().map(TrainDeparture::toString).collect(Collectors.joining("\n"));
  }
}
