package edu.ntnu.stud.utils;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validation of the user input is done through the static methods in this class. The methods take
 * the user input and case num as arguments in order to take the appropriate action.
 *
 * <p><strong>Goal: </strong> Validate the user input and return the validated input or a dummy
 * value.
 *
 * @author 10083
 * @since 1.1
 * @version 1.2
 */
public class UserInputValidation {
  /**
   * The condition to check the integer against in order to validate it. There are multiple cases
   * where the integer input needs to be validated. The case number and their corresponding
   * conditions are mentioned below.
   *
   * <ol>
   *   <li>Train Departure ID
   *   <li>Delay
   *   <li>Track
   *   <li>Track Assigned
   *   <li>Menu input
   *   <li>Hour
   *   <li>Minute
   * </ol>
   *
   * @param num to be validated
   * @param caseNum the case number
   * @return whether the number fulfills the condition depending on its case number
   */
  private static boolean conditionToValidateAgainstDependingOnIntUserInput(int num, int caseNum) {
    boolean isValid = false;
    switch (caseNum) {
      case 1 -> isValid = num > 0;
      case 2 -> isValid = num > -1;
      case 3 -> isValid = num == -1 || num > 0;
      case 4 -> isValid = num == 1 || num == 0;
      case 5 -> isValid = num > -1 && num < 9;
      case 6 -> isValid = num > -1 && num < 24;
      case 7 -> isValid = num > -1 && num < 60;
    }
    return isValid;
  }

  /**
   * The condition to check the string against in order to validate it. There are two cases:
   *
   * <ol>
   *   <li>Destination
   *   <li>Train Line
   * </ol>
   *
   * @param caseNum the case number
   * @param userInput the string to be validated
   * @return whether the string fulfills the condition depending on its case number
   */
  private static boolean conditionToValidateAgainstDependingOnStringUserInput(
      int caseNum, String userInput) {
    Pattern forTrainLine = Pattern.compile("[A-Z]\\d{2}"); // case num 2
    Pattern forDestination = Pattern.compile("^[A-Z][a-z]*$"); // case num 1

    Matcher matcher =
        (caseNum == 1) ? forDestination.matcher(userInput) : forTrainLine.matcher(userInput);
    return matcher.matches();
  }

  /**
   * Handles the exception throwing if the integer input is not valid, aiding in catching the
   * exceptions in the UI.
   *
   * @param num integer to be validated
   * @param caseNum the case number to trigger the appropriate validation check
   * @throws IllegalArgumentException if the integer input is not valid
   */
  private static void exceptionThrowingIfInvalidIntegerUserInput(int num, int caseNum)
      throws IllegalArgumentException {
    switch (caseNum) {
      case 1 -> ParameterValidation.validateId(num);

      case 2 -> ParameterValidation.validateDelay(num);

      case 3 -> ParameterValidation.validateTrack(num);

      case 4 -> {
        if (num != 1 && num != 0) {
          throw new IllegalArgumentException("The track must be either 1 or 0.");
        }
      }
      case 5 -> {
        if (num < 0 || num > 8) {
          throw new IllegalArgumentException("The menu option must be between 0 and 8.");
        }
      }
      case 6 -> {
        if (num < 0 || num > 23) {
          throw new IllegalArgumentException("The hour must be between 0 and 23.");
        }
      }
      default -> {
        if (num < 0 || num > 59) {
          throw new IllegalArgumentException("The minute must be between 0 and 59.");
        }
      }
    }
  }

  /**
   * Handles the validation of the integer inputs.
   *
   * @param input the scanner object
   * @param caseNum the case number to trigger the appropriate validation
   * @return validated integer or dummy value
   */
  public static int validateIntegerUserInput(Scanner input, int caseNum) {
    int attempts = 3;
    int start = 0;
    while (true) {
      start++;

      try {
        int num;
        num = input.nextInt();
        if (conditionToValidateAgainstDependingOnIntUserInput(num, caseNum)) {
          return num;
        } else {
          exceptionThrowingIfInvalidIntegerUserInput(num, caseNum);
        }
      } catch (InputMismatchException | IllegalArgumentException e) {
        if (start != 3) {
          System.out.println(
              "You need to type a whole number. Attempts remaining: " + (attempts - start));
        }
        input.nextLine();
      }
      if (start == 3) {
        throw new IllegalArgumentException("Amount of failed attempts exceeded.");
      }
    }
  }

  /**
   * Validates the time input. The method takes the scanner object and the exit message to be
   * displayed when the user has exceeded the number of attempts .
   *
   * @param input the scanner object
   * @param exitMessage depends on the context of the method call
   * @return validated time or the dummy value
   */
  public static LocalTime validateTimeUserInput(Scanner input, String exitMessage) {
    int attempts = 3;
    int start = 0;
    while (true) {
      start++;
      try {
        System.out.println("Enter the time in hours (0-23): ");
        int hour = input.nextInt();

        System.out.println("Enter the time in minutes (0-59): ");
        int min = input.nextInt();

        return LocalTime.of(hour, min);
      } catch (DateTimeException | IllegalArgumentException | InputMismatchException e) {
        System.out.println(
            "Error: Please enter a time between 00:00 and 23:59. You have "
                + (attempts - start)
                + " attempts remaining before the system returns back to the menu.\n");
        input.nextLine();
      }
      if (start == 3) {
        System.out.println("Too many failed attempts." + exitMessage);
        return null;
      }
    }
  }

  /**
   * Validation of the string inputs. The case number and their corresponding conditions are as
   * follows:
   *
   * <ol>
   *   <li>Destination
   *   <li>Train Line
   * </ol>
   *
   * @param input the scanner object
   * @param message the prompt message to be displayed when asking for the input
   * @param caseNum the case number
   * @return validated string or dummy value (empty string)
   */
  public static String validateStringUserInput(Scanner input, String message, int caseNum) {
    String text = null;

    System.out.println(message);
    for (int i = 3; i > 0; i--) {
      text = input.nextLine();

      if (!text.isBlank() && conditionToValidateAgainstDependingOnStringUserInput(caseNum, text)) {
        return text;
      } else if (i == 1) {
       throw new IllegalArgumentException("Too many failed attempts. The departure won't be added.");
      } else {
        System.out.println("Invalid input. Please try again. Attempts remaining: " + (i - 1));
      }
    }
    return text;
  }
}
