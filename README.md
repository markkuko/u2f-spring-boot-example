# U2F Spring Boot example

This is an example project to demostrate [U2F authentication](https://en.wikipedia.org/wiki/Universal_2nd_Factor)
with Spring Boot 2.

You can start the project with: ```make start```.
The U2F-library requires https connection, the make command
generates the server.jks (src/main/resources/server.jks) automatically.
The keystore credentials etc is configured in [src/main/resources/application.yml](src/main/resources/application.yml)

