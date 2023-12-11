package edu.ntnu.stud.app;

import edu.ntnu.stud.userinterface.UserInterface;

/**
 * Wrapper-class for the static main-method to run the application.
 *
 * <p><Strong>Goal: </Strong> Launch the user interface via the the main method.
 *
 * @author 10083
 * @version 1.2
 * @since 0.3
 */
public class TrainDispatchApp {

  /**
   * The main method for the train dispatch application. It creates a new user interface object and
   * uses it to first initialise the application and then start it.
   */
  public static void main(String[] args) {
    UserInterface.launch();
  }
}
