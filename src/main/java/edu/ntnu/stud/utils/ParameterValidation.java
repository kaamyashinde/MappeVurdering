package edu.ntnu.stud.utils;

import java.time.LocalTime;

/**
 * Validation of the arguments passed in the methods in the models package to ensure robustness of
 * code. The validation is done and exceptions are thrown if the arguments are invalid.
 *
 * <p><strong>Goal:</strong> To validate the arguments passed in the methods in the models package.
 *
 * @author 10083
 * @version 0.5
 * @since 0.4
 */
public class ParameterValidation {
  /**
   * Validates the string if it is not blank.
   *
   * @param string the string object
   * @param errorMessage the error message
   * @throws IllegalArgumentException if the string is blank
   */
  public static void notBlankValidation(String string, String errorMessage)
      throws IllegalArgumentException {
    if (string.isBlank()) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  /**
   * Validates the delay if it is not negative.
   *
   * @param number the delay
   * @throws IllegalArgumentException if the delay is negative
   */
  public static void validateDelay(int number) throws IllegalArgumentException {
    if (number < 0) {
      throw new IllegalArgumentException("The delay cannot be negative.");
    }
  }

  /**
   * Validates the hour if it is between 0 and 23.
   *
   * @param hour time in hours
   * @throws IllegalArgumentException if the hour is not between 0 and 23
   */
  public static void validateHour(int hour) throws IllegalArgumentException {
    if (hour < -1 || hour > 23) {
      throw new IllegalArgumentException("The hour must be between 0 and 23.");
    }
  }

  /**
   * Validates the train ID if it is positive.
   *
   * @param number the train ID
   * @throws IllegalArgumentException if the train ID is less than 1
   */
  public static void validateId(int number) throws IllegalArgumentException {
    if (number < 1) {
      throw new IllegalArgumentException("The train ID must be positive.");
    }
  }

  /**
   * Validates the minute if it is between 0 and 59.
   *
   * @param minute time in minutes
   * @throws IllegalArgumentException if the minute is not between 0 and 59
   */
  public static void validateMinute(int minute) throws IllegalArgumentException {
    if (minute < -1 || minute > 59) {
      throw new IllegalArgumentException("The minute must be between 0 and 59.");
    }
  }

  /**
   * Validates the time if it is not null.
   *
   * @param time the time
   * @throws NullPointerException if the time is null
   */
  public static void validateTime(LocalTime time) throws NullPointerException {
    if (time == null) {
      throw new NullPointerException("The time cannot be null.");
    }
  }

  /**
   * Validates the track if it is between 1 and 15 (or -1).
   *
   * @param trackNum the track number
   * @throws IllegalArgumentException if the track number is not valid
   */
  public static void validateTrack(int trackNum) throws IllegalArgumentException {
    if (trackNum < -1 || trackNum == 0 || trackNum > 15) {
      throw new IllegalArgumentException(
          "There are 15 tracks at the station. Please enter a value for track between 1 and 15.");
    }
  }
}
