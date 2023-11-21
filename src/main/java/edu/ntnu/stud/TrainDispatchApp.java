package edu.ntnu.stud;

import edu.ntnu.stud.userInterface.UserInterface;

/** This is the main class for the train dispatch application. */
public class TrainDispatchApp {

  /**
   * The main method for the train dispatch application. It creates a new user interface object and
   * uses it to first initialise the application and then start it.
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}
