Money Transfer Challenge
Overview
This project is a simple REST API application designed to handle concurrent money transfer requests. The server listens on localhost:8080 and uses an in-memory synchronized datastore with minimalistic locking to manage accounts and transactions.

Features
Create Account: Allows creation of new accounts.
Get Account: Retrieves account details by account ID.
Transfer Money: Facilitates money transfers between accounts.
Validations
The application includes the following validations:

Duplicate Account: Prevents the creation of accounts with duplicate IDs.
Account Doesn't Exist: Ensures that operations are only performed on existing accounts.
Insufficient Funds: Checks for sufficient funds before processing a transfer.
Illegal Transfer Amount: Validates that transfer amounts are non-negative.
Libraries
The application uses the following libraries:

Swagger: For API documentation.
JUnit: For unit testing.
Lombok: To reduce boilerplate code in model classes.
Cucumber: For Behavior-Driven Development (BDD) testing //TODO:Add one sample test class
Running the Application
To run the application, use the following command:

java -jar target/payments-1.0-SNAPSHOT-jar-with-dependencies.jar


Advanced features we can add:
1.We can integrate sonarQube for analysis of code quality and bugs.
2.Spring Actuator can be added for monitoring and health check of applications.
3.We can use containerization ( docker /kubernetes) for deployment of applications easily.
4.Logging mechanism we can use for troubleshooting.
5.CI/CD pipeline can be added for build deployment using terraform or buildscripts for jenkins/bamboo tool 

