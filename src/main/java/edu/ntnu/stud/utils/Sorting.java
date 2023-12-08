package edu.ntnu.stud.utils;

import edu.ntnu.stud.models.TrainDeparture;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Sorting of array lists of type TrainDeparture.
 *
 * <p><strong>Goal: </strong> Provide static methods to sort an array list.
 */
public class Sorting {
  /**
   * Stores a copy of the sorted array list as a string and returns it.
   *
   * @param trainDepartures the array list to be sorted
   * @return the sorted array list as a string
   */
  public static String sortAndReturnString(ArrayList<TrainDeparture> trainDepartures) {
    ArrayList<TrainDeparture> temp = sortArrayList(trainDepartures);
    return temp.stream().map(TrainDeparture::toString).collect(Collectors.joining("\n"));
  }

  /**
   * Sorts an array list and returns it.
   *
   * @param list the array list to be sorted
   * @return the sorted array list
   */
  private static ArrayList<TrainDeparture> sortArrayList(ArrayList<TrainDeparture> list) {
    list.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
    return list;
  }
}
