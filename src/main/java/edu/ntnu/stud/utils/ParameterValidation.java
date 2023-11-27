package edu.ntnu.stud.utils;

import java.time.LocalTime;

/**
 * @author 10083
 * @version 0.4
 * @since 0.4
 */
public class ParameterValidation {

    public static void validateDelay(int number){
        if(number < 0){
            throw new IllegalArgumentException("The delay cannot be negative.");
        }
    }
    public static void validateId(int number){
        if(number < 1){
            throw new IllegalArgumentException("The train ID must be positive.");
        }
    }
    public static void notBlankValidation(String string, String errorMessage){
        if(string.isBlank()){
            throw new IllegalArgumentException(errorMessage);
        }
    }
    public static void validateTrack(int trackNum){
        if (trackNum < -1 || trackNum == 0 || trackNum > 15) {
            throw new IllegalArgumentException("There are 15 tracks at the station. Please enter a value for track between 1 and 15.");
        }
    }
    public static void validateTime(LocalTime time){
        if(time == null){
            throw new NullPointerException("The time cannot be null.");
        }
    }
}
