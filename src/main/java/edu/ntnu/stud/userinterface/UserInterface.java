package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.models.TrainDepartureRegister;
import edu.ntnu.stud.utils.Sorting;
import edu.ntnu.stud.utils.UserInputValidation;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * This class is the user interface for the train dispatch application. It contains the init and
 * start methods, which are used to initialise the application and start it.
 */
public class UserInterface {
  Scanner input = new Scanner(System.in);
  LocalTime time = LocalTime.of(0, 0);
  private static final int QUIT = 0;
  private static final int DEPARTURES_OVERVIEW = 1;
  private static final int ADD_DEPARTURE = 2;
  private static final int FIND_DEPARTURE_BY_TRAIN_ID = 3;
  private static final int FIND_DEPARTURES_BY_DESTINATION = 4;
  private static final int ASSIGN_TRACK = 5;
  private static final int UPDATE_DELAY = 6;
  private static final int UPDATE_TIME = 7;
  boolean running = true;

  TrainDepartureRegister kristiansand;

  private static final String SEPARATOR =
      "------------------------------------------------------------------------------------";
  private static final String HEADER =
      SEPARATOR
          + "\n"
          + "| Departure  | Train Line | Train ID   | Destination          | Delay Time | Track |"
          + "\n"
          + SEPARATOR;

  /**
   * This method initiates the application by creating a new TrainDepartureRegister object and
   * adding some train departures to it.
   */
  public void init() {
    kristiansand = new TrainDepartureRegister();
    kristiansand.addTrainDeparture(LocalTime.of(13, 20), "F1", 1, "Oslo", 1, 1);
    kristiansand.addTrainDeparture(LocalTime.of(10, 0), "F2", 2, "Stavanger", 0, 2);
    kristiansand.addTrainDeparture(LocalTime.of(12, 20), "F3", 4, "Bergen", 0, -1);
    kristiansand.addTrainDeparture(LocalTime.of(3, 0), "F4", 5, "Trondheim", 78, 6);
    kristiansand.addTrainDeparture(LocalTime.of(1, 50), "F5", 6, "Oslo", 5, -1);
    kristiansand.addTrainDeparture(LocalTime.of(23, 27), "F3", 7, "Bergen", 9, 4);
    kristiansand.addTrainDeparture(LocalTime.of(15, 46), "F2", 8, "Trondheim", 2, -1);
    kristiansand.addTrainDeparture(LocalTime.of(19, 20), "F2", 9, "Bergen", 0, -1);
  }

  /**
   * This method starts the application by welcoming the user and then asking them for the current
   * time. This is followed by a while loop that runs as long as the running boolean is true. Inside
   * the while loop is a method containing a switch statement that triggers the desired action based
   * on the user input.
   */
  public void start() {

    System.out.println(
        """
                          =================TRAIN DISPATCH APPLICATION v.01=================

                          Hello and Welcome to the Kristiansand Train Station!
                          """);

    updateTime(1);

    try {
      while (running) {
        triggerDesiredAction();
      }
    } catch (IllegalArgumentException | DateTimeException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * This shows the menu and gets the choice of the user.
   *
   * @return the choice of the user
   */
  private int showMenuAndGetChoice() {
    System.out.println(
        """
                ------------MENU----------
                0. QUIT
                1. Overview of departures
                2. Add a departure
                3. Find a departure
                4. Filter by Destination
                5. Assign a track
                6. Add delay
                7. Update Time
                --------------------------
                """);

    int choice = UserInputValidation.getIntUserInput(input, 5);
    if (choice == -6) {
      System.out.println("Thank you for using the system.");
      running = false;
    }

    return choice;
  }

  /**
   * This method is used to trigger the desired action based on the user input. it uses the method
   * showMenuAndGetChoice to get the choice of the user and stores it in a variable called choice.
   * This variable is then used in a switch statement to trigger the desired action.
   */
  private void triggerDesiredAction() {
    int choice = showMenuAndGetChoice();
    switch (choice) {
      case QUIT -> quit();
      case DEPARTURES_OVERVIEW -> departuresOverview();
      case ADD_DEPARTURE -> addDeparture();
      case FIND_DEPARTURE_BY_TRAIN_ID -> getTrainDeparturesByIdMenu();
      case FIND_DEPARTURES_BY_DESTINATION -> findDeparturesByDestination();
      case ASSIGN_TRACK -> assignTrack();
      case UPDATE_DELAY -> updateDelay();
      case UPDATE_TIME -> updateTime(2);
      default -> System.out.println("hi");
    }
  }

  /**
   * This method is used to target the quit action of the user interface. It sets the running
   * boolean to false which stops the while loop in the start method from running.
   */
  public void quit() {
    System.out.println("Thank you for using the Kristiansand Train Station. Hope to see you soon!");
    running = false;
  }

  /**
   * This method is used to target the first action of the user interface, which is to show an
   * overview of the departures at the train station in Kristiansand. It is formatted as a table
   * using the HEADER and SEPARATOR constants.
   */
  public void departuresOverview() {
    kristiansand.removeTrainDepartureBeforeTime(time);
    System.out.println("Here is an overview of all the departures:");
    System.out.println(HEADER + "\n" + kristiansand.toString() + "\n" + SEPARATOR);
  }

  /**
   * This method is used to target the second action of the user interface, which is to add a
   * departure to the train station in Kristiansand. It asks the user for a bunch of inputs, and
   * then creates a new TrainDeparture object based on the inputs.
   */
  public void addDeparture() {
    try {
      LocalTime depTime = getLocalTimeToAddToDeparture();
      System.out.println("-> Departure Time registered. It is " + depTime + ".");
      input.nextLine();

      String depLine =
          UserInputValidation.validateStringUserInput(
              input,
              "Enter the line of the train in the form F12 (a capital letter + two numbers): ",
              2);

      if (depLine.isEmpty()) {
        return;
      } else {
        System.out.println("-> Train line registered. It is " + depLine + ".");
      }
      int departureId = getIntToAddDeparture(1);
      System.out.println("-> Train ID registered. It is " + departureId + ".");
      input.nextLine();
      String depDestination =
          UserInputValidation.validateStringUserInput(
              input, "Enter the destination of the train: ", 1);
      if (depDestination.isEmpty()) {
        return;
      } else {
        System.out.println("-> Destination registered. It is " + depDestination + ".");
      }
      int depTrack = getIntToAddDeparture(2);
      if (depTrack == -1) {
        System.out.println("Track has not been assigned to the departure yet.");
      } else {
        System.out.println("-> Track registered. It is " + depTrack + ".");
      }
      kristiansand.addTrainDeparture(depTime, depLine, departureId, depDestination, 0, depTrack);
    } catch (IllegalArgumentException | DateTimeException e) {
      System.out.println(e.getMessage());
    }
  }

  /** Method to handle the integer inputs from the user to add Departure. */
  public int getIntToAddDeparture(int caseNum) {
    int checkedNum;
    switch (caseNum) {
      case 1 -> {
        int depId = 0;
        boolean exists = true;

        while (exists) {
          System.out.println("What is the unique Train id?");
          depId = UserInputValidation.getIntUserInput(input, 1);
          exists = kristiansand.checkIfDepartureIdExists(depId);

          if (exists) {
            System.out.println("There is already a train with this id. Please try again");
          }
        }
        checkedNum = depId;
      }
      case 2 -> {
        System.out.println("Is the departure assigned a track? (0: yes, 1: no)");
        int depHasTrack = UserInputValidation.getIntUserInput(input, 4);
        int depTrack = -1;

        if (depHasTrack == 0) {
          System.out.println("What is the track number?");
          depTrack = UserInputValidation.getIntUserInput(input, 3);
        }
        checkedNum = depTrack;
      }
      default -> {checkedNum = 1;}
    }
    return checkedNum;
  }

  /**
   * This method is used to get the departure time from the user. It asks the user for the hour and
   * minutes and then creates a LocalTime object based on the inputs.
   *
   * @return the LocalTime object with departureTime
   */
  private LocalTime getLocalTimeToAddToDeparture() throws IllegalArgumentException {
    try {
      System.out.println("Enter the time in hours: ");
      int depHour = UserInputValidation.getTimeInputTime(input);
      System.out.println("Enter the time in minutes: ");
      int depMin = UserInputValidation.getTimeInputTime(input);

      return LocalTime.of(depHour, depMin);
    } catch (DateTimeException e) {
      System.out.println("The time must be between 00:00 and 23:59. Please try again.");
      return getLocalTimeToAddToDeparture();
    }
  }

  /**
   * This method is used to target the third action of the user interface, which is to find a
   * departure based on the train id.
   */
  public void getTrainDeparturesByIdMenu() {

    System.out.println("What is the ID of the train that you want to search for?");
    int departureId = UserInputValidation.getIntUserInput(input, 1);
    if (departureId == -6) {
      System.out.println("Returning back to menu.");
      return;
    }
    boolean exists = checkIfDepartureIdExists(departureId);
    if (exists) {
      System.out.println("Here is the departure with the train ID " + departureId + ":");
      System.out.println(
          HEADER
              + "\n"
              + kristiansand.returnTrainDepartureBasedOnId(departureId)
              + "\n"
              + SEPARATOR);
    } else {
      System.out.println("There is no departure with the train ID " + departureId + ".");
    }
  }

  /**
   * This method is used to target the fourth action of the user interface, which is to find a
   * departure based on the destination.
   */
  public void findDeparturesByDestination() {
    System.out.println("The trains today are going to the following destination: ");
    System.out.println(kristiansand.returnAllDestinationsInRegister());
    input.nextLine(); // to consume the \n character
    String destination =
        UserInputValidation.validateStringUserInput(input, "What is the destination?", 1);
    boolean exists = kristiansand.checkIfDestinationExists(destination);
    if (exists) {
      System.out.println("Here are the departures going to " + destination + ":");
      String tableContent =
          Sorting.sortAndReturnString(
              kristiansand.returnTrainDeparturesBasedOnDestination(destination));
      System.out.println(HEADER + "\n" + tableContent + "\n" + SEPARATOR);

    } else {
      System.out.println("There are no departures going to " + destination + ".");
    }
  }

  /**
   * This method is used to target the fifth action of the user interface, which is to assign a
   * track to a departure.
   */
  public void assignTrack() {
    System.out.println("What is the train Id of the train that you want to assign a track to?");
    int departureId = UserInputValidation.getIntUserInput(input, 1);
    if (departureId == -6) {
      return;
    }
    System.out.println(departureId);
    if (checkIfDepartureIdExists(departureId)) {
      System.out.println("What is the track number?");
      int track = UserInputValidation.getIntUserInput(input, 3);
      kristiansand.assignTrack(departureId, track);
    } else {
      System.out.println("The train ID does not exist.");
    }
  }

  /**
   * This method is used to target the sixth action of the user interface, which is to update the
   * delay of a departure.
   */
  public void updateDelay() {
    System.out.println("What is the train Id of the train that you want to update the delay of?");
    int departureId = UserInputValidation.getIntUserInput(input, 1);
    if (departureId == -6) {
      return;
    }
    if (checkIfDepartureIdExists(departureId)) {
      System.out.println("What is the delay in minutes?");
      int delay = UserInputValidation.getIntUserInput(input, 2);
      kristiansand.assignDelay(departureId, delay);
    } else {
      System.out.println("The train ID does not exist.");
    }
  }

  /**
   * This method is used to target the seventh action of the user interface, which is to update the
   * time.
   */

  public void updateTime(int caseNum) {
    int attempts = 3;
    int start = 0;
    while (true) {
      start++;
      try {
        System.out.println("Please enter the hour of the day (0-23): ");
        int hour = UserInputValidation.getIntInput(input);
        if (hour == -6) {
          if (caseNum == 1) {
            quit();
          } else {
            System.out.println("Time cannot be update. Returning back to the menu.");
            return;
          }
        }
        System.out.println("Please enter the minute of the day (0-23): ");
        int minute = UserInputValidation.getIntInput(input);
        if (minute == -6) {
          if (caseNum == 1) {
            quit();
          } else {
            System.out.println("Time cannot be update. Returning back to the menu.");
            return;
          }
        }
        LocalTime temp = LocalTime.of(hour, minute);
        if (temp.isBefore(time)) {
          System.out.println("The time cannot be before the current time.");
          return;
        } else {
          time = temp;
        }
        System.out.println("The time has been registered. It is currently " + time);
        kristiansand.removeTrainDepartureBeforeTime(time);
        start = 4;

      } catch (DateTimeException e) {
        System.out.println(e.getMessage());
        System.out.println("attempts remaining: " + (attempts - start));
      }
      if (start == 3) {
        System.out.println("ran out of attempts.");
        return;
      } if (start == 4) {
        return;
      }
    }
  }

  /*
   * */

  /**
   * This method is used to check if a train id exists in the register. This method is used
   * throughout the other methods to check if the train id exists before performing an action.
   *
   * @param departureId unique identifier of the train departure
   * @return true if the train id exists, false if it does not
   */
  public boolean checkIfDepartureIdExists(int departureId) {
    return kristiansand.checkIfDepartureIdExists(departureId);
  }

  }
