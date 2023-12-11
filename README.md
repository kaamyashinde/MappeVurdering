# Portfolio project IDATT1003 - 2023

This file uses Mark Down syntax. For more information see [here](https://www.markdownguide.org/basic-syntax/).

STUDENT NAME = "Kaamya Shinde"  
STUDENT ID = "10083"

## Project description

[//]: # (TODO: Write a short description of your project/product here.)
This project has been developed as a part of the course IDATT1003 - Programming 1 at NTNU. It is a Java based
application set up as a Maven project. The application is a simplified Train Dispatch System.

## Project structure

[//]: # (TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc.)
The project is structured as a Maven project. The source files are stored in the `src/main/java` folder. The JUnit test
classes are stored in the `src/test/java` folder. The source files are divided into packages based on their
functionality. The model classes are stored in the `model` package. These include the entity class - Train Departure and
the register class - Train Departure Register. The utility classes used to validate the methods and improve the
robustness
of the code are stored in the package called `utils`. The `ui` package contains the user interface class. The main
application class is stored in the `app` package.

## Link to repository

[//]: # (TODO: Include a link to your repository here.)
https://github.com/kaamyashinde/MappeVurdering

## How to run the project

[//]: # (TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?)
The project can be run by running the main method in the `TrainDispatchApp` class located in the `app` package. The
application has a user interface that allows the user to interact with the application.
After running the application, the user is prompted to enter the time of the day - because the simplified application
only takes into consideration a station within a 24-hour window (as in a whole day). After that, the user is presented
with the menu and can choose between a range of options.

0. Quit the application
1. Overview of departures
2. Add a departure
3. Remove a departure
4. Find a departure
5. Filter by destination
6. Assign a track
7. Add dela
8. Update time

## How to run the tests

[//]: # (TODO: Describe how to run the tests here.)
The tests are found in the `src/test/java` folder and can be run by running the main method in the desired test class.
There is a test class for each class in the `model` package. The test class for the `TrainDeparture` class is
named `TrainDepartureTest` while the test class for the `TrainDepartureRegister` class is named `TrainDepartureRegisterTest`.

## References

[//]: # (TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter.
Or if you have used code from a website or other source, include a link to the source.)
The code has been developed by the student. ChatGPT has been used to generate parts of the code is clearly stated in the javaDoc comments.
