package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.Scanner;

public class UserInterFace {
  Scanner input = new Scanner(System.in);
  LocalTime time;
  private static final int QUIT = 0;
  private static final int DEPARTURES_OVERVIEW = 1;
  private static final int ADD_DEPARTURE = 2;
  private static final int FIND_DEPARTURE_BY_TRAIN_ID = 3;
  private static final int FIND_DEPARTURES_BY_DESTINATION = 4;
  private static final int ASSIGN_TRACK = 5;
  private static final int UPDATE_DELAY = 6;
  private static final int UPDATE_TIME = 7;
  boolean running = true;

  TrainDepartureRegister Kristiandsand;

  // formatet for hver case

  //  private Static final int CASE1 = 1; osv

  public void init() {
    TrainDepartureRegister KristiansandTrainStation = new TrainDepartureRegister();

    // ask the user for the time right now
    System.out.println(
        """
                Hello and Welcome to the Kristiansnad Train Station!
                What is the time right now?

                Please enter the hour of the day (0-23):
                """);
    int hour = getIntInput(input);
    System.out.println("Please enter the minute of the hour (0-59):");
    int minute = getIntInput(input);

    time = LocalTime.of(hour, minute);
  }

  public void start() {
    /*
     * Add some departures:
     * */
    Kristiandsand.addTrainDeparture(
        new TrainDeparture(LocalTime.of(13, 20), "F1", 1, "Oslo", 1, 1));
    Kristiandsand.addTrainDeparture(
        new TrainDeparture(LocalTime.of(10, 0), "F2", 2, "Stavanger", 0, 2));
    Kristiandsand.addTrainDeparture(
        new TrainDeparture(LocalTime.of(12, 20), "F3", 4, "Bergen", 0, -1));
    Kristiandsand.addTrainDeparture(
        new TrainDeparture(LocalTime.of(3, 0), "F4", 5, "Trondheim", 78, 6));
    Kristiandsand.addTrainDeparture(
        new TrainDeparture(LocalTime.of(1, 50), "F5", 6, "Oslo", 5, -1));
    Kristiandsand.addTrainDeparture(
        new TrainDeparture(LocalTime.of(23, 27), "F3", 7, "Bergen", 9, 4));
    Kristiandsand.addTrainDeparture(
        new TrainDeparture(LocalTime.of(15, 46), "F2", 8, "Trondheim", 2, -1));
    Kristiandsand.addTrainDeparture(
        new TrainDeparture(LocalTime.of(19, 20), "F2", 9, "Bergen", 0, -1));

    System.out.println("What would u like to do?");
    int choice = getIntInput(input);

    running = true;
    while (running) {
      switch (choice) {
        case QUIT -> QUIT();
        case DEPARTURES_OVERVIEW -> DEPARTURES_OVERVIEW();
        case ADD_DEPARTURE -> ADD_DEPARTURE();
        case FIND_DEPARTURE_BY_TRAIN_ID -> FIND_DEPARTURE_BY_TRAIN_ID();
        case FIND_DEPARTURES_BY_DESTINATION -> FIND_DEPARTURES_BY_DESTINATION();
        case ASSIGN_TRACK -> ASSIGN_TRACK();
        case UPDATE_DELAY -> UPDATE_DELAY();
        case UPDATE_TIME -> UPDATE_TIME();
      }
    }
  }

  /*
   Methods:
  private static final int QUIT = 0;
   private static final int DEPARTURES_OVERVIEW = 1;
   private static final int ADD_DEPARTURE = 2;
   private static final int FIND_DEPARTURE_BY_TRAIN_ID = 3;
   private static final int FIND_DEPARTURES_BY_DESTINATION = 4;
   private static final int ASSIGN_TRACK = 5
   private static final int UPDATE_DELAY = 6;
   private static final int UPDATE_TIME = 7;
    */

  private void QUIT() {
    System.out.println("Thankyou for using the Kristiansand Train Station. Hope to see you soon!");
    running = false;
  }

  private void DEPARTURES_OVERVIEW() {
    // update the overview of the departures based on the time:
    Kristiandsand.removeTrainDepartureBeforeTime(time);

    // print the updated overview:
    System.out.println("Here is an overview of all the departures:");
    System.out.println(Kristiandsand.toString());
  }

  private void ADD_DEPARTURE() {
    System.out.println("What is the time of the departure in hours?");
    int depHour = getIntInput(input);
    System.out.println("What is the time of the departure in minutes?");
    int depMin = getIntInput(input);

    LocalTime depTime =
        LocalTime.of(depHour, depMin); // TODO: check if the time is after the current time!

    System.out.println("What is the line of the train?");
    String depLine = input.nextLine();

    int depTrainId;
    boolean exists;
    do {
      System.out.println("What is the unique Train id?");
      depTrainId = getIntInput(input);
      exists = Kristiandsand.checkIfExists(depTrainId);

      if (exists) {
        System.out.println("There is already a train with this id. Please try again");
      }
    } while (exists);

    System.out.println("Where is the train going?");
    String depDestination = input.nextLine();

    System.out.println("is it on time? (0: yes; 1: no)");
    int depOnTime = getIntInput(input);
    int depDelay = 0;
    if (depOnTime == 1) {
      System.out.println("By how many minutes is it delayed?");
      depDelay = getIntInput(input);
    }

    System.out.println("Is the departure assigned a track? (0: yes; 1: no)");
    int depHasTrack = getIntInput(input);
    int depTrack;
    if (depHasTrack == 0) {
      System.out.println("What is the track number?");
      depTrack = getIntInput(input);
    } else {
      depTrack = -1;
    }

    Kristiandsand.addTrainDeparture(
        new TrainDeparture(depTime, depLine, depTrainId, depDestination, depDelay, depTrack));
  }

  private void FIND_DEPARTURE_BY_TRAIN_ID() {

    int trainId;
    boolean doesntExist = true;
    while (doesntExist) {
      System.out.println(
          "What is the train id?"); // so the user can try again if the train id does not exist
      trainId = getIntInput(input);

      Kristiandsand.getTrainDepartureBasedOnTrainId(
          trainId); // if Id exists, it is printed out, else it prints out a message
      doesntExist =
          Kristiandsand.checkIfExists(
              trainId); // if Id does not exist, the user can try again. otherwise, the loop ends
      if (doesntExist) {
        System.out.println("There is no train with this id. Please try again");
      }
    }
  }

  private void FIND_DEPARTURES_BY_DESTINATION() {
    System.out.println("What is the destination?");
    String destination = input.nextLine();
    // TODO: check if the destination exists to make it easier to send out messages to the user
    System.out.println(Kristiandsand.getTrainDepartureBasedOnDestination(destination));
  }

  private void ASSIGN_TRACK() {
    System.out.println("What is the train Id of the train that you want to assign a track to?");
    int trainId = getIntInput(input);
    while (!checkIfTrainIdExists(trainId)) {
      System.out.println("There is no train with this id. Please try again");
      trainId = getIntInput(input);

      if (checkIfTrainIdExists(trainId)) {
        System.out.println("What is the track number?");
        int track = getIntInput(input);
        Kristiandsand.setTrackNumberThroughRegister(trainId, track);
      }
    }
  }

  private void UPDATE_DELAY() {
    System.out.println("What is the train Id of the train that you want to update the delay of?");
    int trainId = getIntInput(input);
    while (!checkIfTrainIdExists(trainId)) {
      System.out.println("There is no train with this id. Please try again");
      trainId = getIntInput(input);

      if (checkIfTrainIdExists(trainId)) {
        System.out.println("What is the delay in minutes?");
        int delay = getIntInput(input);
        Kristiandsand.setDelayThroughRegister(trainId, delay);
      }
    }
  }

  private void UPDATE_TIME() {
    System.out.println("Please enter the hour of the day (0-23):");
    int hour = getIntInput(input);
    System.out.println("Please enter the minute of the hour (0-59):");
    int minute = getIntInput(input);
    time = LocalTime.of(hour, minute);

    Kristiandsand.removeTrainDepartureBeforeTime(time);
  }

  private boolean checkIfTrainIdExists(int trainId) {
    return Kristiandsand.checkIfExists(trainId);
  }

  // scanner utility
  private static int getIntInput(Scanner input) {
    while (true) {
      try {
        return input.nextInt();
      } catch (java.util.InputMismatchException e) {
        System.out.println("Du må skrive inn et heltall.");
        input.nextLine(); // Consume the invalid input
      }
    }
  }
}
