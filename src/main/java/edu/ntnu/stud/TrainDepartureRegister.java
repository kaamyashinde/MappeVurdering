package edu.ntnu.stud;

/**
 * This file contains the class which handles the register of the train departures - aka the list
 * that contains all the train departures. There are two main get methods that filter the list based
 * on the Train id and the Destination. There are also two set methods that set the delay and track
 * number of the train departure. Apart from these setters and getters, there are methods to perform
 * other tasks such as the toString to print out the whole table and the addTrainDeparture method to
 * add a new train departure to the list.
 */
import java.util.*;

public class TrainDepartureRegister {
  ArrayList<TrainDeparture> allTrainDepartures;

  // constructor to initialize the train departure register
  public TrainDepartureRegister() {
    allTrainDepartures = new ArrayList<>();
  }

  // utilities:
  public void addTrainDeparture(
      TrainDeparture trainDeparture) { // add a new train Departure in the register (ArrayList)
    allTrainDepartures.add(trainDeparture);
    this.sortTheTrainDepartureRegister();
  }

  @Override
  public String toString() { // to-string method to print out the whole table.
    StringBuilder result = new StringBuilder();
    allTrainDepartures.forEach(trains -> result.append(trains.toString()).append("\n"));
    return TrainDeparture.printTableHeader() + "\n" + result;
  }

  public void sortTheTrainDepartureRegister() { // sort the train departures by departure time
    Collections.sort(allTrainDepartures, Comparator.comparing(TrainDeparture::getDepartureTime));
  }

  // get-methods:
  public String getTrainDepartureBasedOnTrainId(
      int trainId) { // Get the train departure based on the unique Train Id
    return allTrainDepartures.stream()
        .filter(
            td ->
                td.getTrainId()
                    == trainId) // filter the array List to create a new ArrayList only containing
        // the train departure that matches the trainId
        .findFirst() // Finds the first element in the stream
        .map(TrainDeparture::toString) // maps the train departure to a string
        .orElse(
            "Train not found."); // if the train is not found, return the string "Train not found."
  }

  public String getTrainDepartureBasedOnDestination(
      String destination) { // Get the train departure based on the Destination
    String theDestination;
    theDestination = destination.toLowerCase();
    return allTrainDepartures.stream()
        .filter(
            td ->
                td.getDestination()
                    .toLowerCase()
                    .equals(theDestination)) // filter the array List to create a new ArrayList only
        // containing the train departure that matches the trainId
        .findFirst() // Finds the first element in the stream
        .map(TrainDeparture::toString) // maps the train departure to a string
        .orElse(
            "Train not found."); // if the train is not found, return the string "Train not found."
  }

  // set-methods:
  public void setDelayThroughRegister(
      int trainId, int delay) { // set the delay for a train departure for a departure in the list
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getTrainId() == trainId)
        .forEach(traDep -> traDep.setDelayAndDelayTime(delay));
  }

  public void setTrackNumberThroughRegister(
      int trainId,
      int trackNum) { // set the track number for a train departure for a departure in the list
    allTrainDepartures.stream()
        .filter(traDep -> traDep.getTrainId() == trainId)
        .forEach(traDep -> traDep.setTrack(trackNum));
  }
}
