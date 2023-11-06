package edu.ntnu.stud;

/**
 * This file contains the class which handles the register of the train departures - aka the list
 * that contains all the train departures. There are two main get methods that filter the list based
 * on the Train id and the Destination. There are also two set methods that set the delay and track
 * number of the train departure. Apart from these setters and getters, there are methods to perform
 * other tasks such as the toString to print out the whole table and the addTrainDeparture method to
 * add a new train departure to the list.
 */
import java.time.LocalTime;
import java.util.*;

public class TrainDepartureRegister {
  ArrayList<TrainDeparture> allTrainDepartures;

  // constructor to initialize the train departure register
  public TrainDepartureRegister() {
    allTrainDepartures = new ArrayList<>();
  }

  /** The utilities methods in the TrainDepartureRegister are the following: */
  public void addTrainDeparture(
      /**
       * @param trainDeparture add a train departure to the register
       */
      TrainDeparture trainDeparture) {
    allTrainDepartures.add(trainDeparture);
    this.sortTheTrainDepartureRegister();
  }

  @Override
  public String toString() {
    /**
     * @return the whole table as a string
     */
    StringBuilder result = new StringBuilder();
    allTrainDepartures.forEach(trains -> result.append(trains.toString()).append("\n"));
    return TrainDeparture.getTableHeader() + "\n" + result;
  }

  public void sortTheTrainDepartureRegister() {
    /** sort the train departures based on the departure time */
    allTrainDepartures.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
  }

  public void removeTrainDepartureBeforeTime(LocalTime time) {
    /**
     * @param time remove the train departures that are before the time
     */
    allTrainDepartures.stream()
        .filter(td -> td.getDelayedTime().isBefore(time))
        .forEach(allTrainDepartures::remove);
  }

  public boolean checkIfExists(int trainIdUnique) {
    /**
     * @param trainIdUnique
     * @return true if the train departure exists in the register, false otherwise
     */
    return allTrainDepartures.stream()
        .anyMatch(td -> td.getTrainId() == trainIdUnique);
  }

  /** The get methods in the TrainDepartureRegister are the following: */
  public String getTrainDepartureBasedOnTrainId(
      /**
       * @param trainId
       * @return the train departure based on the train id as a string
       */
      int trainId) { // Get the train departure based on the unique Train Id
    return TrainDeparture.getTableHeader()
        + "\n"
        + allTrainDepartures.stream()
            .filter(
                td ->
                    td.getTrainId()
                        == trainId) // filter the array List to create a new ArrayList only
            // containing
            // the train departure that matches the trainId
            .findFirst() // Finds the first element in the stream because there is only one train
            // with that specific ID
            .map(TrainDeparture::toString) // maps the train departure to a string
            .orElse("Train not found."); // if the train is not found, return the string "Train not
    // found."
  }

  public String getTrainDepartureBasedOnDestination(String destination) {
    /**
     * @param destination
     * @return the train departure based on the destination as a string
     */
    String theDestination = destination.toLowerCase();

    List<String> trainDeparturesToSameDestination =
        allTrainDepartures.stream()
            .filter(
                td ->
                    td.getDestination()
                        .toLowerCase()
                        .equals(theDestination)) // filter based on the destination
            .map(TrainDeparture::toString) // maps the train departure to a string
            .toList(); // adds it to the list trainDeparturesToSameDestination

    if (trainDeparturesToSameDestination.isEmpty()) {
      return "Train not found.";
    } else {
      return TrainDeparture.getTableHeader()
          + "\n"
          + String.join("\n", trainDeparturesToSameDestination);
    }
  }

  // set-methods:
  public void setDelayThroughRegister(
      /**
       * @param trainId and delay add the delay for a train departure with the specific trainId
       */
      int trainId, int delay) { // set the delay for a train departure for a departure in the list
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getTrainId() == trainId)
        .forEach(traDep -> traDep.setDelayAndDelayTime(delay));
  }

  public void setTrackNumberThroughRegister(
      /**
       * @param trainId and trackNum add the track number for a train departure with the specific
       *     trainId
       */
      int trainId,
      int trackNum) { // set the track number for a train departure for a departure in the list
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getTrainId() == trainId)
        .forEach(traDep -> traDep.setTrack(trackNum));
  }
}
