package edu.ntnu.stud.utils;

import java.time.LocalTime;

/**
 * Validation of the arguments passed in the methods in the models package to ensure robustness of
 * code. The validation is done and exceptions are thrown if the arguments are invalid.
 * <strong>Goal: </strong> To validate the arguments passed in the methods in the models package.
 *
 * @author 10083
 * @version 0.4
 * @since 0.4
 */
public class ParameterValidation {
  /**
   * Validates the destination.
   *
   * @param string the string object
   * @param errorMessage the error message
   */
  public static void notBlankValidation(String string, String errorMessage) {
    if (string.isBlank()) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  /**
   * Validates the delay.
   *
   * @param number the delay
   */
  public static void validateDelay(int number) {
    if (number < 0) {
      throw new IllegalArgumentException("The delay cannot be negative.");
    }
  }

  /**
   * Validates the train ID.
   *
   * @param number the train ID
   */
  public static void validateId(int number) {
    if (number < 1) {
      throw new IllegalArgumentException("The train ID must be positive.");
    }
  }

  /**
   * Validates the time.
   *
   * @param time the time
   */
  public static void validateTime(LocalTime time) {
    if (time == null) {
      throw new NullPointerException("The time cannot be null.");
    }
  }

  /**
   * Validates the train line.
   *
   * @param trackNum the track number
   */
  public static void validateTrack(int trackNum) {
    if (trackNum < -1 || trackNum == 0 || trackNum > 15) {
      throw new IllegalArgumentException(
          "There are 15 tracks at the station. Please enter a value for track between 1 and 15.");
    }
  }
}
