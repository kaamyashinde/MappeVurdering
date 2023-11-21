package edu.ntnu.stud.utils;

public class ParameterValidation {

    public static void positiveIntegerValidation(int number, String errorMessage){
        if(number < 0){
            throw new IllegalArgumentException(errorMessage);
        }
    }
    public static void notBlankValidation(String string, String errorMessage){
        if(string.isBlank()){
            throw new IllegalArgumentException(errorMessage);
        }
    }
    public static void validateTrack(int trackNum, String errorMessage){
        if (trackNum < -1 || trackNum == 0 || trackNum > 15) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
