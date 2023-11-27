package edu.ntnu.stud.utils;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 10083
 * @since 1.1
 * @version 1.2
 */
public class UserInputValidation {
  private static final String RETURNING_TO_MENU = "Returning to the menu. \n";

  /**
   * Depending on the caseNum, the condition to validate the argument changes. Here is an overview:
   *
   * <ol>
   *   <li>Train Departure ID
   *   <li>Delay
   *   <li>Track
   *   <li>Track Assigned
   *   <li>Menu input
   * </ol>
   *
   * @param input //TODO
   * @param caseNum //TODO
   * @return validated integer
   */
  public static int getIntUserInput(Scanner input, int caseNum) {
    int attempts = 3;
    int start = 0;
    while (true) {
      start++;

      try {
        int num;
        num = input.nextInt();
        if (matchCaseNumToCondition(num, caseNum)) {
          return num;
        } else {
          validateArgument(num, caseNum);
        }
      } catch (InputMismatchException | IllegalArgumentException e) {
        if (start!=3) {
        System.out.println("You need to type a whole number. Attempts remaining: " + (attempts - start));}
        input.nextLine();
      }
      if (start == 3) {
        return -6;
      }
    }
  }

  /**
   * Depending on the caseNum, the condition to validate the argument changes. Here is an overview:
   *
   * <ol>
   *   <li>Train Departure ID
   *   <li>Delay
   *   <li>Track
   *   <li>Track Assigned
   *   <li>Menu input
   * </ol>
   *
   * @param num to be validated
   * @param caseNum the case number
   * @return validated number
   */
  private static boolean matchCaseNumToCondition(int num, int caseNum) {
    return (caseNum == 1)
        ? num > 0
        : (caseNum == 2)
            ? num > -1
            : (caseNum == 3)
                ? (num == -1 || num > 0)
                : (caseNum == 4) ? (num == 1 || num == 0) : (num > -1 && num < 9);
  }

  private static void validateArgument(int num, int caseNum) throws IllegalArgumentException{
    switch (caseNum) {
      case 1 -> {
        if (num < 1) {
          throw new IllegalArgumentException("The train ID must be positive.");
        }
      }
      case 2 -> {
        if (num < 0) {
          throw new IllegalArgumentException("The delay cannot be negative.");
        }
      }
      case 3 -> {
        if (num < -1 || num == 0 || num > 15) {
          throw new IllegalArgumentException(
              "There are 15 tracks at the station. Please enter a value for track between 1 and 15.");
        }
      }
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
    }
  }

  public static String validateStringUserInput(Scanner input, String message, int caseNum) {
    String text = null;

    System.out.println(message);
    for (int i = 3; i > 0; i--) {
      text = input.nextLine();

      if (!text.isBlank() && matchStringToRegEx(caseNum, text)) {
        return text;
      } else if (i == 1) {
        System.out.println("Too many failed attempts. The departure won't be added.");
        text = "";
      } else {
        System.out.println("Invalid input. Please try again. Attempts remaining: " + (i - 1));
      }
    }
    return text;
  }

  private static boolean matchStringToRegEx(int caseNum, String userInput) {
    Pattern forTrainLine = Pattern.compile("[A-Z]\\d{2}"); // case num 2
    Pattern forDestination = Pattern.compile("\\b[A-Z][a-z]*\\b"); // case num 1

    Matcher matcher =
        (caseNum == 1)
            ? forDestination.matcher(
                Character.toUpperCase(userInput.charAt(0)) + userInput.substring(1).toLowerCase())
            : forTrainLine.matcher(userInput);
    return matcher.matches();
  }

  public static LocalTime validateTime(Scanner input, String exitMessage) {
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
}
