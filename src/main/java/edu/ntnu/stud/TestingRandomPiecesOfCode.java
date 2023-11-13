package edu.ntnu.stud;

import java.sql.SQLOutput;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TestingRandomPiecesOfCode {
    public static void tryingTimeThingy(){
        int time = 1390;
        int minutes = time % 100;
        System.out.println(minutes);
        int minutesToAdd = 0;
        if (minutes > 0) {
            //Calculate the number of minutes to add to round up to the next hour

            if (minutes == 60) {
                minutesToAdd = 40;
            } else if (minutes > 60){
                minutesToAdd = minutes - 60;
            }
            time += minutesToAdd;
        }
        System.out.println(time);
        System.out.println("-----------------");
    }
    public static void tryingFormatter(){
        LocalTime time1 = LocalTime.of(10, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time1.format(formatter); // Formats as "10:30:00"
        LocalTime parsedTime = LocalTime.parse("15:45:00", formatter); // Parses the string into a LocalTime instance


        DateTimeFormatter newformatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime parsedTimeNew = LocalTime.parse("15:45", newformatter);
        System.out.println(parsedTimeNew);
        System.out.println(parsedTimeNew.plusMinutes(90));
    }

    public static void register(){
        TrainDepartureRegister newRegister = new TrainDepartureRegister();
        TrainDeparture td1 = new TrainDeparture(LocalTime.of(10,30), "test", 1, "test", 1, 3);
        TrainDeparture td2 = new TrainDeparture(LocalTime.of(12,30), "test", 2, "test", 0, 2);
        TrainDeparture td3 = new TrainDeparture(LocalTime.of(15,30), "test", 3, "test", 0, -1);

        newRegister.addTrainDeparture(td1);
        newRegister.addTrainDeparture(td2);
        newRegister.addTrainDeparture(td3);

        System.out.println(newRegister.toString());
    }
    public static void main(String[] args){
    register();



    }
}
