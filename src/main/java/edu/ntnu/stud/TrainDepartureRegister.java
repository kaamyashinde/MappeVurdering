package edu.ntnu.stud;

/**
 * This file contains the class which handles the register of the train departures - aka the list
 * that contains all the train departures.
 */
/*
1. print overview of train departures //TODO
  sortbed by departure time
2. add new train departure
  check for unique train id
3. assign track to train departure
    use train id
4. add delay
    use train id
5. search by unique train id //TODO
6. filter by destination //TODO
7. update the time //TODO
8. exit application //TODO
 */


import java.util.ArrayList;

public class TrainDepartureRegister {
  ArrayList<TrainDeparture> allTrainDepartures;
  private int numberOfTrainDepartures;

  // constructor to initialize the train departure register
  public TrainDepartureRegister() {
    allTrainDepartures = new ArrayList<>();
    numberOfTrainDepartures = allTrainDepartures.size();
  }

  // create a new train departure
  public void addTrainDeparture(TrainDeparture trainDeparture) {
    allTrainDepartures.add(trainDeparture);
  }

  // set-methods:

  // set the delay for a train departure for a departure in the list
  public void setDelayThroughRegister(int trainId, int delay) {
    for (TrainDeparture traDep : allTrainDepartures) {
      if (traDep.getTrainId() == trainId) {
        traDep.setDelay(delay);
        traDep.setDelayedTime();
      }
    }
  }

  // set the track number for a train departure for a departure in the list
  public void setTrackNumberThroughRegister(int trainId, int trackNum) {
    for (TrainDeparture traDep : allTrainDepartures) {
      if (traDep.getTrainId() == trainId) {
        traDep.setTrack(trackNum);
      }
    }
  }

  // to-string method to print out the whole table.
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (TrainDeparture trains : allTrainDepartures) {
      result.append(trains.toString()).append("\n");
    }
    return TrainDeparture.printTableHeader() + "\n" + result;
  }
}
