package edu.ntnu.stud;

/**
 * This file contains the class which handles the register of the train departures - aka the list
 * that contains all the train departures.
 */

/*
1.
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

    //set the delay for a train departure for a departure in the list
  public void setDelayThroughRegister(int trainId, int delay) {
    for (TrainDeparture traDep : allTrainDepartures) {
      if (traDep.getTrainId() == trainId) {
        traDep.setDelay(delay);
        traDep.setDelayedTime();
      }
    }
  }

    //set the track number for a train departure for a departure in the list
  public void setTrackNumberThroughRegister(int trainId, int trackNum) {
    for (TrainDeparture traDep : allTrainDepartures) {
      if (traDep.getTrainId() == trainId) {
        traDep.setTrack(trackNum);
      }
    }
  }

  //to-string method to print out the whole table.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TrainDeparture.printTableHeader());
        for (TrainDeparture traDep : allTrainDepartures) {
        sb.append(traDep.toString());
        }
        return sb.toString();
    }


}
