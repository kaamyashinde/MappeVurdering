package edu.ntnu.stud;

/**
 * This file contains the class for the train departure.
 *
 * The information that is to be displayed for
 * each train departure is the following:
 * - Departure Time - Train Line - Train ID -
 * Destination - Delayed Time - Track Number.
 *
 * The class contains get-methods for all attributes, set-methods for the delay information and track attribute,
 * a to-string method and a static method to print out the header for the main table that will display the train departures.
 */
public class TrainDeparture {
  private final int departureTime;
  private final String trainLine;
  private final int trainId;
  private final String destination;
  private int delay;
  private int delayedTime;
  private int track;
  static final String HEADER =
      """
                --------------------------------------------------------------------------------------------
                | Departure  | Train Line | Train ID   | Destination          | Delay | Delay Time | Track |
                --------------------------------------------------------------------------------------------""";

  // Constructor to initialize the train departure object
  public TrainDeparture(
      int departureTime,
      String trainLine,
      int trainId,
      String destination,
      int delay,
      int delayedTime,
      int track) {
    this.departureTime = departureTime;
    this.trainLine = trainLine;
    this.trainId = trainId;
    this.destination = destination;
    this.delay = delay;
    this.delayedTime = delayedTime;
    this.track = track;
  }

  // get methods:
  public int getDepartureTime() {
    return departureTime;
  }

  public String getTrainLine() {
    return trainLine;
  }

  public int getTrainId() {
    return trainId;
  }

  public String getDestination() {
    return destination;
  }

  public int getDelay() {
    return delay;
  }

  public int getDelayedTime() {
    return delayedTime;
  }

  public int getTrack() {
    return track;
  }



  // set methods:
  public void setDelay(int delay) {
    this.delay = delay;
  }

  public void setDelayedTime() {
    this.delayedTime = departureTime + delay;
  }

  public void setTrack(int track) {
    this.track = track;
  }

  // to-string method
  @Override
  public String toString() {
    // Define the format for each row of the table
    return String.format(
            "| %-10s | %-10s | %-10s | %-20s | %-5d | %-10d | %-5d |",
            departureTime, trainLine, trainId, destination, delay, delayedTime, track)
        + "\n"
        + "--------------------------------------------------------------------------------------------";
  }

  //additional method that prints out the table header - will be used in the main method so that the header is only printed once
  public static String printTableHeader() {
    // Print the table header
    return HEADER;
  }
}
