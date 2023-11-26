package edu.ntnu.stud.models;

import edu.ntnu.stud.utils.ParameterValidation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Represents the train departure register containing the instances of the train departures. The
 * instances are stored in an ArrayList.
 *
 * <p><Strong>Goal: </Strong>Act as a model for a train departure register.
 *
 * @author 10083
 * @version 1.0
 * @since 0.2
 */
public class TrainDepartureRegister {
  private final ArrayList<TrainDeparture> allTrainDepartures = new ArrayList<>();

  /** Constructs an object of the class TrainDepartureRegister. */
  public TrainDepartureRegister() {}

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

    allTrainDepartures.add(
        new TrainDeparture(departureTime, trainLine, departureId, destination, delay, track));
  }

  /**
   * Sort the train departures based on the departure time.
   *
   * @return the sorted list of train departures
   */
  public ArrayList<TrainDeparture> sortTheTrainDepartureRegister() {
    ArrayList<TrainDeparture> unsortedListOfTrainDepartures = deepCopyOfDepartures();
    unsortedListOfTrainDepartures.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
    return unsortedListOfTrainDepartures;
  }

  /**
   * The method checks whether the delayed time os the train departure is before the time given.
   * Then it removes the train departure from teh register
   *
   * @param theTime the time to check if the train departure is before
   */
  public void removeTrainDepartureBeforeTime(LocalTime theTime) throws NullPointerException {
    ParameterValidation.validateTime(theTime);
    allTrainDepartures.removeIf(td -> td.getDelayedTime().isBefore(theTime));
  }

  /**
   * The method checks whether the train ID is taken by another train departure in the register.
   *
   * @param departureIdUnique ID of the train departure
   * @return true if the train departure exists in the register, false otherwise
   */
  public boolean checkIfDepartureIdExists(int departureIdUnique) throws IllegalArgumentException {
    ParameterValidation.validateId(departureIdUnique);
    return allTrainDepartures.stream().anyMatch(td -> td.getDepartureId() == departureIdUnique);
  }

  /**
   * The method checks whether or not there is a train that is going to a given destination.
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
   * Method filters the original list for the departureId sepcified and returns the train departure
   * matching the departureId.
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
   * Make a deep copy of the objects in the register.
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
   * Store a deep copy of the objects in the register in a new array list and remove all the
   * departures that are not going to destination specified in the argument.
   *
   * @param destination to identify the train departure that is being searched for
   * @return arrayList of objects with specific destination attribute
   */
  public ArrayList<TrainDeparture> returnTrainDeparturesBasedOnDestination(String destination)
      throws IllegalArgumentException, NullPointerException {
    ParameterValidation.notBlankValidation(
        destination, "No destination has been detected. Please enter the destination");
    ArrayList<TrainDeparture> copyOfDepartures = deepCopyOfDepartures();
    copyOfDepartures.removeIf(td -> !td.getDestination().equalsIgnoreCase(destination));
    return copyOfDepartures;
  }

  /** Returns a list of destinations to help during filtering. */
  public String returnAllDestinationsInRegister() {
    return deepCopyOfDepartures().stream()
        .map(TrainDeparture::getDestination)
        .distinct()
        .collect(Collectors.joining(" | "));
  }

  /**
   * The method sets the delay for a train departure based on the train ID.
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
   * The method sets the track number for a train departure based on the train ID.
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

  /**
   * The method returns the train departures in the register as a string.
   *
   * @return the train departures in the register as a string
   */
  @Override
  public String toString() {
    ArrayList<TrainDeparture> sortedRegister = sortTheTrainDepartureRegister();
    return sortedRegister.stream().map(TrainDeparture::toString).collect(Collectors.joining("\n"));
  }
}
