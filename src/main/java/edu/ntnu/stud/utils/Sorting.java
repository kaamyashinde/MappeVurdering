package edu.ntnu.stud.utils;

import edu.ntnu.stud.models.TrainDeparture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Sorting {
    public static String sortAndReturnString(ArrayList<TrainDeparture> trainDepartures) {
        ArrayList<TrainDeparture> temp = sortArrayList(trainDepartures);
        return temp.stream().map(TrainDeparture::toString).collect(Collectors.joining("\n"));
    }
    public static ArrayList<TrainDeparture> sortArrayList(ArrayList<TrainDeparture> list){
        list.sort(Comparator.comparing(TrainDeparture::getDepartureTime));
        return list;
    }

}
