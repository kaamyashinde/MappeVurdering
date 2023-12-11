package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.models.TrainDepartureRegister;
import edu.ntnu.stud.utils.UserInputValidation;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * UserInterface class to handle the interactions between the user and the Train Dispatch
 * Application.
 *
 * <p>Goal: interpret user input and act accordingly.
 *
 * @author 10083
 * @since 0.3
 * @version 1.0.2
 */
public class UserInterface {
  private static final int QUIT = 0;
  private static final int DEPARTURES_OVERVIEW = 1;
  private static final int ADD_DEPARTURE = 2;
  private static final int REMOVE_DEPARTURE = 3;
  private static final int FIND_DEPARTURE_BY_TRAIN_ID = 4;
  private static final int FIND_DEPARTURES_BY_DESTINATION = 5;
  private static final int ASSIGN_TRACK = 6;
  private static final int UPDATE_DELAY = 7;
  private static final int UPDATE_TIME = 8;
  private static final Scanner input = new Scanner(System.in);
  private static boolean running = true;
  private static LocalTime systemTime = LocalTime.of(0, 0);
  private static final String SEPARATOR =
      "------------------------------------------------------------------------------------";
  private static final String HEADER =
      SEPARATOR
          + "\n"
          + "| Departure  | Train Line | Train ID   | Destination          | Delay Time | Track |"
          + "\n"
          + SEPARATOR;
  private static final String RETURNING_TO_MENU = "Returning to the menu. \n";
  private static final TrainDepartureRegister kristiansand = new TrainDepartureRegister();

  /** Private constructor to ensure no construction of objects of this class. */
  private UserInterface() {}

  /**
   * Checks if the train ID exists in the register. If it exists then it returns true otherwise it
   * returns false.
   *
   * @param departureId unique identifier of the train departure
   * @return true if the train id exists, false if it does not
   */
  private static boolean checkIfDepartureIdExists(int departureId) {
    return kristiansand.doesDepartureIdExist(departureId);
  }

  /**
   * <strong>Goal: </strong> Avoid redundant code by extracting the common code from {@link
   * #addDeparture()} for the time input.
   *
   * @param caseNum the case number to determine if it is an hour or minute input which is sent to
   *     {@link UserInputValidation#validateIntegerUserInput(Scanner, int)}
   * @param message the prompt to print out to the user
   * @return the validated integer input
   */
  private static int getDepartureTimeToAddToDeparture(int caseNum, String message) {
    System.out.println(message);
    return UserInputValidation.validateIntegerUserInput(input, caseNum);
  }

  /**
   * Handles the input of the datatype integer from the user while adding a new departure. If the
   * input validation fails, a dummy value is set. This value is taken into consideration in the
   * method where it is being called, which is {@link #addDeparture()}.
   *
   * @param caseNum the case number to determine if it is a departure ID or track number input which
   *     is sent to {@link UserInputValidation#validateIntegerUserInput(Scanner, int)}
   * @return the validated integer input
   */
  private static int getIntToAddDeparture(int caseNum) {
    int checkedNum;
    if (caseNum == 1) {

      boolean exists;

      System.out.println("What is the unique Train id?");
      int depId = UserInputValidation.validateIntegerUserInput(input, 1);

      exists = checkIfDepartureIdExists(depId);

      if (exists) {
        System.out.println("There is already a train with this id. Returning back to the menu");
        return -6;
      }

      checkedNum = depId;
    } else {

      System.out.println("Is the departure assigned a track? (0: yes, 1: no)");
      int depHasTrack = UserInputValidation.validateIntegerUserInput(input, 4);
      int depTrack = -1;

      if (depHasTrack == 0) {
        System.out.println("What is the track number?");
        depTrack = UserInputValidation.validateIntegerUserInput(input, 3);
      }
      checkedNum = depTrack;
    }
    return checkedNum;
  }

  /**
   * Displays the menu to the user and prompts the user for in input. The input is then validated
   * and if the user inputs an invalid input three times,the application will quit.
   *
   * <p>{@link UserInputValidation#validateIntegerUserInput(Scanner, int)} is used to validate the
   * integer and if a dummy value is returned due to exceeding of the number of tries, then the
   * application quits.
   *
   * @return the validated user input which corresponds to the desired action
   */
  private static int showMenuAndGetChoice() {

    System.out.println(
        """
                    ------------MENU----------
                    0. QUIT
                    1. Overview of departures
                    2. Add a departure
                    3. Remove a departure
                    4. Find a departure
                    5. Filter by Destination
                    6. Assign a track
                    7. Add delay
                    8. Update Time
                    --------------------------
                    What would u like to do? (Enter a number between 0 and 8).
                    """);
    return UserInputValidation.validateIntegerUserInput(input, 5);
  }

  /** Launches the application by running the init and start methods. */
  public static void launch() {
    init();
    start();
  }

  /** Initialises the application by adding train departures to it. */
  private static void init() {
    try {
      kristiansand.addTrainDeparture(13, 20, "F1", 1, "Oslo", 1, 1);
      kristiansand.addTrainDeparture(10, 0, "F2", 2, "Stavanger", 0, 2);
      kristiansand.addTrainDeparture(12, 20, "F3", 4, "Bergen", 0, -1);
      kristiansand.addTrainDeparture(3, 0, "F4", 5, "Trondheim", 78, 6);
    } catch (IllegalArgumentException | NullPointerException | DateTimeException e) {
      System.out.println(
          "One of the items could not be added to the register because of the following reason: "
              + e.getMessage());
    }
  }

  /**
   * Starts the application by welcoming the user and registering the current time.
   *
   * <p>Runs {@link #triggerDesiredAction()} in a while loop until the user quits the application.
   */
  private static void start() {
    System.out.println(
        """
          =================TRAIN DISPATCH APPLICATION v.01=================

          Hello and Welcome to the Kristiansand Train Station!
          """);

    updateTime("The system time cannot be before 00:00", "Exiting the system.");

    try {
      while (running) {
        triggerDesiredAction();
      }
    } catch (IllegalArgumentException | DateTimeException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Triggers the desired action based on the user input. The validated input from {@link
   * #showMenuAndGetChoice()} is used to interpret the users input against a switch-case to perform
   * the user requested action.
   */
  private static void triggerDesiredAction() {
    int choice = showMenuAndGetChoice();
    switch (choice) {
      case QUIT -> quit();
      case DEPARTURES_OVERVIEW -> departuresOverview();
      case ADD_DEPARTURE -> addDeparture();
      case REMOVE_DEPARTURE -> removeTrainDepartureBasedOnId();
      case FIND_DEPARTURE_BY_TRAIN_ID -> getTrainDeparturesByIdMenu();
      case FIND_DEPARTURES_BY_DESTINATION -> findDeparturesByDestination();
      case ASSIGN_TRACK -> assignTrack();
      case UPDATE_DELAY -> updateDelay();
      case UPDATE_TIME -> updateTime(
          "The new time cannot be before the current time. It is currently " + systemTime + ".",
          RETURNING_TO_MENU);
      default -> System.out.println("-> Enter a valid number between 0 and 8.");
    }
  }

  /**
   * Quits the application by setting the running variable to false and printing a message to the
   * user.
   */
  private static void quit() {
    System.out.println("Thank you for using the Kristiansand Train Station. Hope to see you soon!");
    running = false;
  }

  /**
   * <strong>Goal: </strong> Target the first action of the application in {@link
   * #triggerDesiredAction()}
   *
   * <p>Shows an overview of all the departures at the train station sorted by time. It is formatted
   * as a table using the {@code HEADER} and {@code SEPARATOR} constants.
   *
   * <p>The register is first updated by removing the departures before the current time. The
   * situation where there are no remaining departures left prints out an appropriate message,
   * otherwise the overview is printed along with practical information about the departures.
   */
  private static void departuresOverview() {
    kristiansand.removeTrainDepartureBeforeTime(systemTime);

    if (kristiansand.getTimeTilNextDeparture(systemTime) == -1) {
      System.out.println("There are no more departures today. \n");
      return;
    }
    System.out.println("Here is an overview of all the departures: \n");
    long hours = kristiansand.getTimeTilNextDeparture(systemTime) / 60;
    long minutes = kristiansand.getTimeTilNextDeparture(systemTime) % 60;

    System.out.println(
        "Time: "
            + systemTime
            + "  | Next Departure in "
            + hours
            + " hours and "
            + minutes
            + " minutes.");

    System.out.println(HEADER + "\n" + kristiansand + "\n" + SEPARATOR);
    System.out.println(
        "IQR between "
            + kristiansand.findInterQuartileRange()
            + " | Total Number of Departures: "
            + kristiansand.getNumberOfTrainDepartures()
            + "\n");
  }

  /**
   * <strong>Goal: </strong> Target the second action of the application
   *
   * <p>Add a departure to teh train station in Kristiansand. The string inputs are validated by
   * {@link UserInputValidation} while the integer inputs are validated by another method in this
   * class called {@link #getIntToAddDeparture(int)}. If the dummy values are returned from the
   * validation, then the program prematurely hops out of the method. It then tries to create a new
   * {@link edu.ntnu.stud.models.TrainDeparture} object based on the inputs. If an exception is
   * thrown, it is caught and the user is returned to the menu.
   */
  private static void addDeparture() {
    try {
      int depHour = getDepartureTimeToAddToDeparture(6, "Enter the hour of the departure: ");
      int depMinute = getDepartureTimeToAddToDeparture(7, "Enter the minute of the departure: ");
      LocalTime depTime = LocalTime.of(depHour, depMinute);

      if (depTime.isBefore(systemTime)) {
        System.out.println(
            "The time cannot be before the current time. Departure not added. "
                + RETURNING_TO_MENU);
        return;
      } else {
        System.out.println("-> The departure time is " + depTime + ".");
      }

      input.nextLine();

      String depLine =
          UserInputValidation.validateStringUserInput(
              input,
              "Enter the line of the train in the form F12 (a capital letter + two numbers): ",
              2);

      if (depLine.isEmpty()) {
        System.out.println(RETURNING_TO_MENU);
        return;
      } else {
        System.out.println("-> The train line is " + depLine + ".");
      }

      int departureId = getIntToAddDeparture(1);

      System.out.println("-> The train ID is " + departureId + ".");

      input.nextLine();

      String depDestination =
          UserInputValidation.validateStringUserInput(
              input, "Enter the destination of the train: ", 1);
      if (depDestination.isEmpty()) {
        System.out.println(RETURNING_TO_MENU);
        return;
      } else {
        System.out.println("-> The train is going to " + depDestination + ".");
      }

      int depTrack = getIntToAddDeparture(2);
      if (depTrack == -1) {
        System.out.println("-> Track has not been assigned to the departure yet.");
      } else {
        System.out.println("-> The track number is " + depTrack + ".");
      }

      kristiansand.addTrainDeparture(
          depHour, depMinute, depLine, departureId, depDestination, 0, depTrack);

      System.out.println(
          "The train departure with ID "
              + departureId
              + " on it's way to "
              + depDestination
              + (depTrack == -1 ? " " : " from track " + depTrack + " ")
              + "has been successfully added to thh register.");
    } catch (IllegalArgumentException | DateTimeException e) {
      System.out.println(e.getMessage());
      System.out.println(RETURNING_TO_MENU);
    }
  }

  /**
   * <strong>Goal: </strong> Target the third action of the user interface - remove a departure
   * based on its ID.
   *
   * <p>Prompts the user for the departure ID to search for. If the validation done by {@link
   * UserInputValidation#validateIntegerUserInput(Scanner, int)} fails then a dummy value is set
   * which prematurely exists the method and returns the user back to the menu. After validation:
   *
   * <ol>
   *   <li>If the departure ID exists, then the departure is removed from the register.
   *   <li>If the departure ID does not exist, then a message is printed out saying that there is no
   *       departure with that ID.
   * </ol>
   */
  private static void removeTrainDepartureBasedOnId() {
    try {
      System.out.println("What is the ID of the train that you want to search for?");
      int departureId = UserInputValidation.validateIntegerUserInput(input, 1);

      boolean exists = checkIfDepartureIdExists(departureId);
      if (exists) {
        System.out.println("Removing the following departure: ");
        System.out.println(
            HEADER
                + "\n"
                + kristiansand.returnTrainDepartureBasedOnId(departureId)
                + "\n"
                + SEPARATOR
                + "\n");
        kristiansand.removeTrainDepartureBasedOnId(departureId);
      } else {
        System.out.println("There is no departure with the train ID " + departureId + ".\n");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.out.println(RETURNING_TO_MENU);
    }
  }

  /**
   * <strong>Goal: </strong> Target the fourth action of the user interface - retrieve a departure
   * based on the ID.
   *
   * <p>Prompts the user for the departure ID to search for. If the validation done by {@link
   * UserInputValidation#validateIntegerUserInput(Scanner, int)} fails then a dummy value is set
   * which prematurely exits this method and returns the user back to the menu. After validation:
   *
   * <ol>
   *   <li>If the departure ID exists, then the departure is printed out.
   *   <li>If the departure ID does not exist, then a message is printed out saying that there is no
   *       departure with that ID.
   * </ol>
   */
  private static void getTrainDeparturesByIdMenu() {
    try {
      System.out.println("What is the ID of the train that you want to search for?");
      int departureId = UserInputValidation.validateIntegerUserInput(input, 1);

      boolean exists = checkIfDepartureIdExists(departureId);
      if (exists) {
        System.out.println("Here is the departure with the train ID " + departureId + ":");
        System.out.println(
            HEADER
                + "\n"
                + kristiansand.returnTrainDepartureBasedOnId(departureId)
                + "\n"
                + SEPARATOR
                + "\n");
      } else {
        System.out.println("There is no departure with the train ID " + departureId + ".\n");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.out.println(RETURNING_TO_MENU);
    }
  }

  /**
   * <strong>Goal: </strong> Target the fifth action of the user interface.
   *
   * <p>A list of all the destinations that the trains are going to is printed before asking the
   * user for the destination to search for. This destination is validated using {@link
   * UserInputValidation#validateStringUserInput(Scanner, String, int)} and then:
   *
   * <ol>
   *   <li>If the destination exists, then the departures going to that destination are printed out.
   *   <li>If the destination does not exist, then a message is printed out saying that there are no
   *       departures going to that destination.
   * </ol>
   */
  private static void findDeparturesByDestination() {
    try {
      String destinations = kristiansand.returnAllDestinationsInRegister();
      if (destinations.isEmpty()) {
        System.out.println("There are no departures in the register. \n");
        return;
      } else {
        System.out.println("The trains today are going to the following destination: ");
        System.out.println(destinations + "\n");
      }

      input.nextLine();
      String destination =
          UserInputValidation.validateStringUserInput(input, "What is the destination?", 1);
      boolean exists = kristiansand.doesDestinationExist(destination);
      if (exists) {
        System.out.println("Here are the departures going to " + destination + ":");
        String tableContent =
            kristiansand.returnTrainDeparturesBasedOnDestinationAsString(destination);
        System.out.println(HEADER + "\n" + tableContent + "\n" + SEPARATOR + "\n");

      } else {
        System.out.println("There are no departures going to " + destination + ".\n");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.out.println(RETURNING_TO_MENU);
    }
  }

  /**
   * <strong>Goal:</strong> Target the sixth action of the user interface.
   *
   * <p>Asks the user for the train ID of the train that they want to assign a track to. After the
   * user input is validated using {@link UserInputValidation#validateIntegerUserInput(Scanner,
   * int)}, either of the two things happen:
   *
   * <ol>
   *   <li>If the train ID exists, then the user is asked for the track number to assign to the
   *       train. The track number is validated and then assigned to the train.
   *   <li>If the train ID does not exist, then a message is printed out saying that there is no
   *       train with that ID.
   * </ol>
   */
  private static void assignTrack() {
    try {
      System.out.println("What is the train Id of the train that you want to assign a track to?");
      int departureId = UserInputValidation.validateIntegerUserInput(input, 1);
      if (checkIfDepartureIdExists(departureId)) {
        System.out.println("Enter the track number (or -1 to remove the track): ");
        int track = UserInputValidation.validateIntegerUserInput(input, 3);
        kristiansand.assignTrack(departureId, track);
        System.out.println("Changes to the track have been saved.");
      } else {
        System.out.println("The train ID does not exist. \n");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.out.println(RETURNING_TO_MENU);
    }
  }

  /**
   * <strong>Goal</strong> Target the seventh action of the user interface.
   *
   * <p>Asks the user for the train ID of the train that they want to update the delay of. If the
   * validation done by {@link UserInputValidation#validateIntegerUserInput(Scanner, int)} fails, a
   * dummy value is set which prematurely exists the method and returns to the menu. After
   * validation:
   *
   * <ol>
   *   <li>If the train ID exists, then the user is asked for the delay to assign to the train. The
   *       delay is validated and assigned to the train.
   *   <li>If the train ID does not exist, then a message is printed out saying that there is no
   *       train with that ID.
   * </ol>
   */
  private static void updateDelay() {
    try {
      System.out.println("What is the train Id of the train that you want to update the delay of?");
      int departureId = UserInputValidation.validateIntegerUserInput(input, 1);
      if (checkIfDepartureIdExists(departureId)) {
        System.out.println("What is the delay in minutes?");
        int delay = UserInputValidation.validateIntegerUserInput(input, 2);

        kristiansand.assignDelay(departureId, delay);
      } else {
        System.out.println("The train ID does not exist. \n");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.out.println(RETURNING_TO_MENU);
    }
  }

  /**
   * <strong>Goal: </strong> Target the eighth action of the user interface. Updates the time
   * depending on whether the user is registering or updating the time. The validation is done by
   * {@link UserInputValidation#validateIntegerUserInput(Scanner, int)}.
   *
   * <ol>
   *   <li>If the user is registering the time, then validation fail leads to an error message
   *       saying that the program is existing.
   *   <li>If the user is updating the time, then validation fail leads to an error message saying
   *       that the user is being returned to the main menu.
   * </ol>
   *
   * <p>Try-and-catch block is used to catch the exceptions thrown by {@link LocalTime#of(int,
   * int)}.
   *
   * @param timeMessage the prompt when asking the user for the input
   * @param errorMessage the error message to print out if the validation fails
   */
  private static void updateTime(String timeMessage, String errorMessage) {
    LocalTime temp;
    int hour;
    int min;
    try {
      System.out.println("Enter the time in hour: ");
      hour = UserInputValidation.validateIntegerUserInput(input, 6);
      System.out.println("Enter the time in minutes: ");
      min = UserInputValidation.validateIntegerUserInput(input, 7);
      temp = LocalTime.of(hour, min);
      if (temp.isBefore(systemTime)) {
        System.out.println(timeMessage);
        temp = null;
      }
      if (temp == null && !errorMessage.equals(RETURNING_TO_MENU)) {
        running = false;
      } else if (temp != null) {
        systemTime = temp;
        System.out.println("The time has been registered. It is  " + systemTime + "\n");
      }
    } catch (IllegalArgumentException | DateTimeException e) {
      if (!errorMessage.equals(RETURNING_TO_MENU)) {
        System.out.println(errorMessage);
        running = false;
      } else {
        System.out.println(errorMessage);
      }
    }
  }
}
