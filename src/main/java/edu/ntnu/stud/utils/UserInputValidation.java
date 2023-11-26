package edu.ntnu.stud.utils;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputValidation {
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
    int num = 0;

    for (int i = 3; i > 0; i--) {
      num = getIntInput(input);
      if ((caseNum == 1)
          ? num > 0
          : (caseNum == 2)
              ? num > -1
              : (caseNum == 3)
                  ? (num == -1 || num > 0)
                  : (caseNum == 4) ? (num == 1 || num == 0) : (num > -1 && num < 8)) {
        return num;
      } else if (i == 1) {
        System.out.println("too many failed attempts. the departure can not be added. ");
      } else if (num == -6) {
        return num;

      } else {
        System.out.println("Invalid input. Please try again. Attempts remaining: " + (i - 1));
      }
    }

    return num;
  }

  public static int getIntInput(Scanner input) {
    int attempts = 3;
    int start = 0;
    while (true) {
      start++;
      try {
        return input.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("You need to type a whole number.");
        System.out.println("Attempts remaining: " + (attempts - start));
        input.nextLine();
      }
      if (start == 3) {
        return -6;
      }
    }
  }

  public static int getTimeInputTime(Scanner input) {
    int num = -6;

    for (int i = 3; i > 0; i--) {
      if (input.hasNextInt()) {
        num = input.nextInt();
        break;
      } else if (i == 1) {
        System.out.println("too many failed attempts. departure cannot be added. \n");
        break;
      } else {
        System.out.println("invalid input. try again. attempts remaining: " + (i - 1));
        input.next();
      }
    }

    return num;
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
}
