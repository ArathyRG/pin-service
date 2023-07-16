# PIN Activation Service
Service exposes an API that takes customerId and macAddress as input and sends it to a southbound system to activate the PIN terminal.

Below responses are expected based on interaction with southbound system:
- If PIN terminal is activated, "ACTIVE" status is returned.
- If PIN terminal is not registered or already attached to a different customer, "INACTIVE" status is returned.
- If any other unexpected error occurs, "UNKNOWN" status is returned.

## Tools and Technologies Used
- Spring Boot : 2.7.13
- Java : 1.8
- Maven : 3.8.8
- IDE : STS
- Sonarlint
- Postman

## Testing the service
Application can be run either from IDE as a Java application or use following command in terminal:
 ```sh
mvn spring-boot:run
```
You can find all the API queries in the attached postman collection [here](PinActivation.postman_collection.json).