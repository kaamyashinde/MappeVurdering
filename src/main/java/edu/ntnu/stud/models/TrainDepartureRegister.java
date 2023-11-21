package edu.ntnu.stud.models;

import edu.ntnu.stud.utils.ParameterValidation;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
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
  private final ArrayList<TrainDeparture> allTrainDepartures = new ArrayList<>();

  // constructor to initialize the train departure register
  public TrainDepartureRegister() {}

  /**
   * The method adds the train departure to the register.
   *
   * @param trainDeparture the train departure instance to be added to the register
   */
  public void addTrainDeparture(TrainDeparture trainDeparture) {
    allTrainDepartures.add(trainDeparture);
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

  /**
   * Sort the train departures based on the departure time.
   *
   * @return the sorted train departures
   */
  public ArrayList<TrainDeparture> sortTheTrainDepartureRegister() {
    ArrayList<TrainDeparture> unsortedListOfTrainDepartures = new ArrayList<>(allTrainDepartures);
    unsortedListOfTrainDepartures.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
    return unsortedListOfTrainDepartures;
  }

  /**
   * The method checks whether the delayed time os the train departure is before the time given.
   * Then it removes the train departure from teh register
   *
   * @param theTime the time to check if the train departure is before
   */
  public void removeTrainDepartureBeforeTime(LocalTime theTime)
      throws IllegalArgumentException, NullPointerException, DateTimeException {
    Objects.requireNonNull(theTime, "The time cannot be null");
    if (theTime.isBefore(LocalTime.of(0, 0)) || theTime.isAfter(LocalTime.of(23, 59))) {
      throw new DateTimeException("The time must be between 00:00 and 23:59. Please try again.");
    }
    allTrainDepartures.removeIf(td -> td.getDelayedTime().isBefore(theTime));
  }

  /**
   * The method checks whether the train ID is taken by another train departure in the register.
   *
   * @param departureIdUnique ID of the train departure
   * @return true if the train departure exists in the register, false otherwise
   */
  public boolean checkIfdepartureIdExists(int departureIdUnique) throws IllegalArgumentException {
    ParameterValidation.positiveIntegerValidation(departureIdUnique, "The train ID cannot be negative");
    return allTrainDepartures.stream().anyMatch(td -> td.getDepartureId() == departureIdUnique);
  }

  /**
   * The method checks whether or not there is a train that is going to a given destination.
   *
   * @param destination the destination to check if there is a train going to
   * @return true if there is a train going to the destination, false otherwise
   */
  public boolean checkIfDestinationExists(String destination) throws IllegalArgumentException {
    ParameterValidation.notBlankValidation(destination, "No destination has been detected. Please try again.");
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
  public String getTrainDepartureBasedOndepartureId(int departureId)
      throws IllegalArgumentException {
    ParameterValidation.positiveIntegerValidation(departureId, "The train ID cannot be negative");
    return allTrainDepartures.stream()
        .filter(td -> td.getDepartureId() == departureId)
        .map(TrainDeparture::toString)
        .collect(Collectors.joining("\n"));
  }

  /**
   * Make a copy of the list of train departures and remove all the train departures that are not
   * going to the destination specified.
   *
   * @param destination to identify the train departure that is being searched for
   * @return arrayList of objects with specific destination attribute
   */
  public ArrayList<TrainDeparture> getDeparturesBasedOnDestination(String destination)
      throws IllegalArgumentException, NullPointerException {
    ParameterValidation.notBlankValidation(
        destination, "No destination has been detected. Please enter the destination");
    ArrayList<TrainDeparture> copyOfAllTrainDepartures =new ArrayList<>();
    allTrainDepartures.forEach(trainDeparture -> copyOfAllTrainDepartures.add(new TrainDeparture(trainDeparture)));
    copyOfAllTrainDepartures.removeIf(td -> !td.getDestination().equalsIgnoreCase(destination)); //deep copies for life!
    return copyOfAllTrainDepartures;
  }

  /** Returns a list of destinations to help during filtering. */
  public String getDestinations() {
    return allTrainDepartures.stream()
        .map(TrainDeparture::getDestination)
        .distinct()
        .collect(Collectors.joining(" | "));
  }

  // set-methods:
  /**
   * The method sets the delay for a train departure based on the train ID.
   *
   * @param departureId unique identifier of the train departure
   * @param delay the delay in minutes
   */
  public void setDelay(int departureId, int delay) {
    ParameterValidation.positiveIntegerValidation(departureId, "The train ID cannot be negative");
    ParameterValidation.positiveIntegerValidation(delay, "The delay cannot be negative");
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
  public void setTrack(int departureId, int trackNum) throws IllegalArgumentException {
    ParameterValidation.positiveIntegerValidation(departureId, "The train ID cannot be negative");
    ParameterValidation.validateTrack(trackNum,
            "There are 15 tracks at the station. Please enter a value for track between 1 and 15");
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getDepartureId() == departureId)
        .forEach(traDep -> traDep.setTrack(trackNum));
  }
}
