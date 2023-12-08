package edu.ntnu.stud.userinterface;

import edu.ntnu.stud.models.TrainDepartureRegister;
import edu.ntnu.stud.utils.Sorting;
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
 * @version 1.2
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
  private static LocalTime time = LocalTime.of(0, 0);
  private static final String SEPARATOR =
      "------------------------------------------------------------------------------------";
  private static final String HEADER =
      SEPARATOR
          + "\n"
          + "| Departure  | Train Line | Train ID   | Destination          | Delay Time | Track |"
          + "\n"
          + SEPARATOR;

  private static final TrainDepartureRegister kristiansand = new TrainDepartureRegister();

  private static final String RETURNING_TO_MENU = "Returning to the menu. \n";

  /** Private constructor to ensure no construction of objects of this class. */
  private UserInterface() {}

  /** Launches the application by running the init and start methods. */
  public static void launch() {
    init();
    start();
  }

  /** Initialises the application by adding train departures to it. */
  public static void init() {
    try {
      kristiansand.addTrainDeparture(LocalTime.of(13, 20), "F1", 1, "Oslo", 1, 1);
      kristiansand.addTrainDeparture(LocalTime.of(10, 0), "F2", 2, "Stavanger", 0, 2);
      kristiansand.addTrainDeparture(LocalTime.of(12, 20), "F3", 4, "Bergen", 0, -1);
      kristiansand.addTrainDeparture(LocalTime.of(3, 0), "F4", 5, "Trondheim", 78, 6);
      kristiansand.addTrainDeparture(LocalTime.of(1, 50), "F5", 6, "Oslo", 5, -1);
      kristiansand.addTrainDeparture(LocalTime.of(23, 27), "F3", 7, "Bergen", 9, 4);
      kristiansand.addTrainDeparture(LocalTime.of(15, 46), "F2", 8, "Trondheim", 2, -1);
      kristiansand.addTrainDeparture(LocalTime.of(19, 20), "F2", 9, "Bergen", 0, -1);
    } catch (IllegalArgumentException | NullPointerException | DateTimeException e) {
      System.out.println(
          "One of the items could not be added to the register because of the following reason: "
              + e.getMessage());
    }
  }

  /**
   * Starts the application by welcoming the user and registering the current time.
   *
   * <p>Runs the method that triggers the desired action from the user.
   */
  public static void start() {

    System.out.println(
        """
           =================TRAIN DISPATCH APPLICATION v.01=================

           Hello and Welcome to the Kristiansand Train Station!
          """);

    updateTime("The system time cannot be before 00:00", "Exiting the program.");

    try {
      while (running) {
        triggerDesiredAction();
      }
    } catch (IllegalArgumentException | DateTimeException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Displays the menu to the user and prompts the user for in input. The input is then validated
   * and if the user inputs an invalid input three times,the application will quit.
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

    int choice = UserInputValidation.validateIntegerUserInput(input, 5);
    if (choice == -6) {
      System.out.println("Failed to update the time. Exiting the system. \n");
      return 0;
    }
    return choice;
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
          "The new time cannot be before the current time. It is currently " + time + ".",
          RETURNING_TO_MENU);
      default -> System.out.println("-> Enter a valid number between 0 and 8.");
    }
  }

  /**
   * Quits the application by setting the running variable to false and printing a message to the
   * user.
   */
  private static void quit() {
    System.out.println(
        "\n Thank you for using the Kristiansand Train Station. Hope to see you soon!");
    running = false;
  }

  /**
   * <strong>Goal: </strong> Target the first action of the application
   *
   * <p>Shows an overview of all the departures at the train station sorted by time. It is formatted
   * as a table using the {@code HEADER} and {@code SEPARATOR} constants.
   */
  private static void departuresOverview() {
    kristiansand.removeTrainDepartureBeforeTime(time);

    if (kristiansand.getTimeTilNextDeparture(time) == -1) {
      System.out.println("There are no more departures today. \n");
      return;
    }
    System.out.println("Here is an overview of all the departures: \n");
    long hours = kristiansand.getTimeTilNextDeparture(time) / 60;
    long minutes = kristiansand.getTimeTilNextDeparture(time) % 60;


    System.out.println("Time: " + time + "  | Next Departure in " + hours + " hours and " + minutes + " minutes.");

    System.out.println(HEADER + "\n" + kristiansand + "\n" + SEPARATOR);
    System.out.println("IQR between " + kristiansand.findInterQuartileRange() + " | Total Number of Departures: " + kristiansand.getNumberOfTrainDepartures() + "\n");




  }

  /**
   * <strong>Goal: </strong> Target the second action of the application
   *
   * <p>Add a departure to teh train station in Kristiansand. It asks the user for inputs which are
   * validated by the {@link UserInputValidation}. If the dummy values are returned from the
   * validation, then the program prematurely hops out of the method. It then tries to create a new
   * {@link edu.ntnu.stud.models.TrainDeparture} object based on the inputs. If an exception is
   * thrown, it is caught and the user is returned to the menu.
   */
  private static void addDeparture() {

    LocalTime depTime = UserInputValidation.validateTimeUserInput(input, RETURNING_TO_MENU);
    if (depTime == null) {
      return;
    } else if (depTime.isBefore(time)) {
        System.out.println("The time cannot be before the current time. Departure not added. " + RETURNING_TO_MENU);
        return;
        } else {
        System.out.println("-> The departure time is " + depTime + ".");
    }

    input.nextLine(); // consume the \n from the previous input
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
    if (departureId == -6) {
      return;
    } else {
      System.out.println("-> The train ID is " + departureId + ".");
    }

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

    try {

      kristiansand.addTrainDeparture(depTime, depLine, departureId, depDestination, 0, depTrack);

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
   * Handles the input of the datatype integer from the user while adding a new departure. If the
   * input validation fails, a dummy value is set. This value is taken into consideration in the
   * method where it is being called, which is {@link #addDeparture()}.
   */
  private static int getIntToAddDeparture(int caseNum) {
    int checkedNum = 0;
    switch (caseNum) {
      case 1 -> {
        boolean exists;

        System.out.println("What is the unique Train id?");
        int depId = UserInputValidation.validateIntegerUserInput(input, 1);
        if (depId == -6) {
          System.out.println(RETURNING_TO_MENU);
          return -6;
        } else {
          exists = checkIfDepartureIdExists(depId);

          if (exists) {
            System.out.println("There is already a train with this id. Returning back to the menu");
            return -6;
          }
        }

        checkedNum = depId;
      }
      case 2 -> {
        System.out.println("Is the departure assigned a track? (0: yes, 1: no)");
        int depHasTrack = UserInputValidation.validateIntegerUserInput(input, 4);
        int depTrack = -1;

        if (depHasTrack == 0) {
          System.out.println("What is the track number?");
          depTrack = UserInputValidation.validateIntegerUserInput(input, 3);
        }
        checkedNum = depTrack;
      }
      default -> {
        System.out.println("This statement should never be reached.");
      }
    }
    return checkedNum;
  }

  /**
   * <strong>Goal: </strong> Target the third action of the user interface - remove a departure
   * based on its ID.
   *
   * <p>Prompts the user for the departure ID to search for. If the validation fails then a dummy
   * value is set which prematurely exists the method and returns the user back to the menu. After
   * validation:
   *
   * <ol>
   *   <li>If the departure ID exists, then the departure is removed from the register.
   *   <li>If the departure ID does not exist, then a message is printed out saying that there is no
   *       departure with that ID.
   * </ol>
   */
  private static void removeTrainDepartureBasedOnId() {
    System.out.println("What is the ID of the train that you want to search for?");
    int departureId = UserInputValidation.validateIntegerUserInput(input, 1);
    if (departureId == -6) {
      System.out.println("Returning back to menu. \n");
      return;
    }
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
  }

  /**
   * <strong>Goal: </strong> Target the fourth action of the user interface - retrieve a departure
   * based on the ID.
   *
   * <p>Prompts the user for the departure ID to search for. If the validation fails then a dummy
   * value is set which prematurely exits this method and returns the user back to the menu. After
   * validation:
   *
   * <ol>
   *   <li>If the departure ID exists, then the departure is printed out.
   *   <li>If the departure ID does not exist, then a message is printed out saying that there is no
   *       departure with that ID.
   * </ol>
   */
  private static void getTrainDeparturesByIdMenu() {

    System.out.println("What is the ID of the train that you want to search for?");
    int departureId = UserInputValidation.validateIntegerUserInput(input, 1);
    if (departureId == -6) {
      System.out.println("Returning back to menu. \n");
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
              + SEPARATOR
              + "\n");
    } else {
      System.out.println("There is no departure with the train ID " + departureId + ".\n");
    }
  }

  /**
   * <strong>Goal: </strong> Target the fifth action of the user interface.
   *
   * <p>A list of all the destinations that the trains are going to is printed before asking the
   * user for the destination to search for. This destination is validated and then:
   *
   * <ol>
   *   <li>If the destination exists, then the departures going to that destination are printed out.
   *   <li>If the destination does not exist, then a message is printed out saying that there are no
   *       departures going to that destination.
   * </ol>
   */
  private static void findDeparturesByDestination() {
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
    boolean exists = kristiansand.checkIfDestinationExists(destination);
    if (exists) {
      System.out.println("Here are the departures going to " + destination + ":");
      String tableContent =
          Sorting.sortAndReturnString(
              kristiansand.returnTrainDeparturesBasedOnDestination(destination));
      System.out.println(HEADER + "\n" + tableContent + "\n" + SEPARATOR + "\n");

    } else {
      System.out.println("There are no departures going to " + destination + ".\n");
    }
  }

  /**
   * <strong>Goal:</strong> Target the sixth action of the user interface.
   *
   * <p>Asks the user for the train ID of the train that they want to assign a track to. After the
   * user input is validated, either of the two things happen:
   *
   * <ol>
   *   <li>If the train ID exists, then the user is asked for the track number to assign to the
   *       train. The track number is validated and then assigned to the train.
   *   <li>If the train ID does not exist, then a message is printed out saying that there is no
   *       train with that ID.
   * </ol>
   */
  private static void assignTrack() {
    System.out.println("What is the train Id of the train that you want to assign a track to?");
    int departureId = UserInputValidation.validateIntegerUserInput(input, 1);
    if (departureId == -6) {
      System.out.println(RETURNING_TO_MENU);
      return;
    }
    System.out.println(departureId);
    if (checkIfDepartureIdExists(departureId)) {
      System.out.println("What is the track number?");
      int track = UserInputValidation.validateIntegerUserInput(input, 3);
      kristiansand.assignTrack(departureId, track);
    } else {
      System.out.println("The train ID does not exist. \n");
    }
  }

  /**
   * <strong>Goal</strong> Target the seventh action of the user interface.
   *
   * <p>Asks the user for the train ID of the train that they want to update the delay of. If the
   * validation is failed, a dummy value is set which prematurely exists the method and returns to
   * the menu. After validation:
   *
   * <ol>
   *   <li>If the train ID exists, then the user is asked for the delay to assign to the train. The
   *       delay is validated and assigned to the train.
   *   <li>If the train ID does not exist, then a message is printed out saying that there is no
   *       train with that ID.
   * </ol>
   */
  private static void updateDelay() {
    System.out.println("What is the train Id of the train that you want to update the delay of?");
    int departureId = UserInputValidation.validateIntegerUserInput(input, 1);
    if (departureId == -6) {
      System.out.println("Returning back to the menu \n");
      return;
    }
    if (checkIfDepartureIdExists(departureId)) {
      System.out.println("What is the delay in minutes?");
      int delay = UserInputValidation.validateIntegerUserInput(input, 2);
      if (delay == -6) {
        System.out.println("Returning back to the menu \n");
        return;
      }
      kristiansand.assignDelay(departureId, delay);
    } else {
      System.out.println("The train ID does not exist. \n");
    }
  }

  /**
   * <strong>Goal: </strong> Target the eighth action of the user interface. Updates the time
   * depending on whether the user is registering or updating the time.
   *
   * <ol>
   *   <li>If the user is registering the time, then validation fail leads to an error message
   *       saying that the program is existing.
   *   <li>If the user is updating the time, then validation fail leads to an error message saying
   *       that the user is being returned to the main menu.
   * </ol>
   */
  private static void updateTime(String timeMessage, String errorMessage) {
    LocalTime temp = UserInputValidation.validateTimeUserInput(input, errorMessage);
    assert temp != null;
    if (temp.isBefore(time)) {
      System.out.println(timeMessage);
      temp = null;
    }
    if (temp == null && !errorMessage.equals(RETURNING_TO_MENU)) {
      running = false;
    } else if (temp != null) {
      time = temp;
      System.out.println("The time has been registered. It is  " + time + "\n");
    }
  }

  /**
   * Checks if the train ID exists in the register. If it exists then it returns true otherwise it
   * returns false.
   *
   * @param departureId unique identifier of the train departure
   * @return true if the train id exists, false if it does not
   */
  private static boolean checkIfDepartureIdExists(int departureId) {
    return kristiansand.checkIfDepartureIdExists(departureId);
  }
}
