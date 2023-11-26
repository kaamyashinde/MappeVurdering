package edu.ntnu.stud;

import edu.ntnu.stud.models.TrainDeparture;
import edu.ntnu.stud.models.TrainDepartureRegister;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TestingRandomPiecesOfCode {

  private String username;
  private int age;

  public TestingRandomPiecesOfCode(String username, int age) {
    // Validation for username (assuming non-null and non-empty)
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }

    // Validation for age (assuming a valid age range)
    if (age < 0 || age > 150) {
      throw new IllegalArgumentException("Invalid age");
    }

    this.username = username;
    this.age = age;
  }

  // Getters and other methods...

  // Example of how to update the username with validation
  public void setUsername(String newUsername) {
    try {
      this.username = username;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
  }

  // Example of how to update the age with validation
  public void setAge(int newAge) {
    if (newAge < 0 || newAge > 150) {
      throw new IllegalArgumentException("Invalid age");
    }

    this.age = newAge;
  }

  public static void tryingTimeThingy() {
    int time = 1390;
    int minutes = time % 100;
    System.out.println(minutes);
    int minutesToAdd = 0;
    if (minutes > 0) {
      // Calculate the number of minutes to add to round up to the next hour

      if (minutes == 60) {
        minutesToAdd = 40;
      } else if (minutes > 60) {
        minutesToAdd = minutes - 60;
      }
      time += minutesToAdd;
    }
    System.out.println(time);
    System.out.println("-----------------");
  }

  public static void tryingFormatter() {
    LocalTime time1 = LocalTime.of(10, 30);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = time1.format(formatter); // Formats as "10:30:00"
    LocalTime parsedTime =
        LocalTime.parse("15:45:00", formatter); // Parses the string into a LocalTime instance

    DateTimeFormatter newformatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime parsedTimeNew = LocalTime.parse("15:45", newformatter);
    System.out.println(parsedTimeNew);
    System.out.println(parsedTimeNew.plusMinutes(90));
  }

  public static void register() {
    /*TrainDepartureRegister newRegister = new TrainDepartureRegister();
    TrainDeparture td1 = new TrainDeparture(LocalTime.of(10, 30), "test", 1, "test", 1, 3);
    TrainDeparture td2 = new TrainDeparture(LocalTime.of(12, 30), "test", 2, "test", 0, 2);
    TrainDeparture td3 = new TrainDeparture(LocalTime.of(15, 30), "test", 3, "test", 0, -1);

    newRegister.addTrainDeparture(td1);
    newRegister.addTrainDeparture(td2);
    newRegister.addTrainDeparture(td3);*/

    //System.out.println(newRegister.toString());
  }

  public static void main(String[] args) {
Scanner input = new Scanner(System.in);
int hour = getIntt(input);




  }

  public static int getIntt(Scanner input) {
    int num = 0;
    for (int i = 3; i > 0; i--) {
      if (input.hasNextInt()) {
        num = input.nextInt();
        break;
      } else if (i == 1) {
        System.out.println("too many failed attempts");
        num = -1;
      } else {
        System.out.println("invalid input. attempts remaining: " + (i - 1));
        input.next(); //consume invalid attempt
      }
    }
    return num;
  }

  public static int getInt(Scanner input) {
    int num = 0;
    int attempts = 3;

    while (attempts > 0) {
      if (input.hasNextInt()) {
        num = input.nextInt();
        break;
      } else if (attempts == 1) {
        System.out.println("Too many failed attempts");
        num = -1;
        break;
      } else {
        System.out.println("Invalid input. Attempts remaining: " + (attempts - 1));
        input.next(); // Consume the invalid input to avoid an infinite loop
        attempts--;
      }
    }
    return num;
  }

}
