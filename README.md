Springboot application that registers and returns network devices

0- Prerequisites
```
1- To run the application locally you need to have Java 15
2- To build the Docker image locally you'll need Docker installed 
3- Postgres database required to run the app.
4- Maven wrapper included in the project
```
1- Clone repository

2- Compile and start app
```
mvnw clean spring-boot:run
```
You should see something like:
```
Started DeviceRegApplication in 3.616 seconds (JVM running for 3.933)
```
4- Confirm that the application is running
```
curl http://localhost:7070/device
```
