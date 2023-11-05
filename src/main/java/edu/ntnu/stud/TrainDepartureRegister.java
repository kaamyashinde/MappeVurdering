package edu.ntnu.stud;

/**
 * This file contains the class which handles the register of the train departures - aka the list
 * that contains all the train departures.
 */
/*
1. print overview of train departures
  sortbed by departure time
2. add new train departure
  check for unique train id
3. assign track to train departure
    use train id
4. add delay
    use train id
5. search by unique train id //TODO (in progress)
6. filter by destination //TODO
7. update the time
8. exit application
 */


import java.util.*;

public class TrainDepartureRegister {
  ArrayList<TrainDeparture> allTrainDepartures;

  // constructor to initialize the train departure register
  public TrainDepartureRegister() {
    allTrainDepartures = new ArrayList<>();
  }

  // create a new train departure
  public void addTrainDeparture(TrainDeparture trainDeparture) {
    allTrainDepartures.add(trainDeparture);
    this.sortTheTrainDepartureRegister();
  }

  //get-methods:
  public void getTrainDepartureBasedOnTrainId(int trainId){
    allTrainDepartures.stream().filter(traDep -> traDep.getTrainId() == trainId).forEach(System.out::println);
  }

  public String getTrainDeparturesBasedOnTrainId(int trainId){
    StringBuilder result = new StringBuilder();
    allTrainDepartures.stream().filter(traDep -> traDep.getTrainId() == trainId).forEach(traDep -> result.append(traDep.toString()).append("\n"));
    return result.toString();
  }

  // set-methods:

  // set the delay for a train departure for a departure in the list
  public void setDelayThroughRegister(int trainId, int delay) {
    allTrainDepartures.stream()
            .filter(traDep -> traDep.getTrainId() == trainId)
            .forEach(traDep -> traDep.setDelayAndDelayTime(delay));

  }

  // set the track number for a train departure for a departure in the list
  public void setTrackNumberThroughRegister(int trainId, int trackNum) {
    allTrainDepartures.stream().filter(traDep -> traDep.getTrainId() == trainId).forEach(traDep -> traDep.setTrack(trackNum));
  }

  // to-string method to print out the whole table.
  public String toString() {
    StringBuilder result = new StringBuilder();
    allTrainDepartures.forEach(trains -> result.append(trains.toString()).append("\n"));
    return TrainDeparture.printTableHeader() + "\n" + result;
  }

  //sort the train departures by departure time
  public void sortTheTrainDepartureRegister(){
    Collections.sort(allTrainDepartures, Comparator.comparing(TrainDeparture::getDepartureTime));
  }
}
