# U2F Spring Boot example

This is an example project to demostrate [U2F authentication](https://en.wikipedia.org/wiki/Universal_2nd_Factor)
with Spring Boot 2.

U2F requires using https, you must generate your own
server keystore (src/main/resources/server.jks) to run
the example project. Remember to change keystore credentials
in [src/main/resources/application.yml](src/main/resources/application.yml)



