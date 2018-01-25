# Validaator Simulaator

Validaator Simulaator tries to simulate the e-ticketing system Ridango AS has applied to public transportation systems in Estonia and in some places of Sweden and Ukraine. This simulation has been done in a very crude and basic fashion.

## Frontend

Technologies used:
  - JavaFX

The frontend part of the project Validaator Simulaator serves as a demonstration of the backend's functionality. The UI allows to register a new user or a stop; select a registered user from the database and to validate them against a stop, logging a transaction to the database.

#### Installing and running the project
Have JDK9 SE and execute ```$ gradlew run```.

#### What could be improved

 - Refactoring ValidatorController splitting it into multiple classes
 - Refactoring validaator.fxml splitting it into partial views
 - Adding tests

##### Dependencies
[org.json:json:20171018](https://mvnrepository.com/artifact/org.json/json/20171018)
[com.squareup.okhttp:okhttp:2.7.5](https://mvnrepository.com/artifact/com.squareup.okhttp/okhttp/2.7.5)
[junit:junit:4.12](https://mvnrepository.com/artifact/junit/junit/4.12)

----------------------------------------
----------------------------------------
----------------------------------------
