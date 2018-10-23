jar-file = u2f-0.1.0-SNAPSHOT.jar

# Dependency check
dep-check:
	./gradlew clean dependencyCheckAnalyze

# Generate server side keyfile
src/main/resources/server.jks:
	keytool -genkey -alias selfsigned_localhost_sslserver -keyalg RSA -keysize 2048 -validity 1500 -keypass changeitpass -storepass changeitpass -keystore src/main/resources/server.jks

# Start the server
start: src/main/resources/server.jks
	./gradlew -Dillegal-access=deny clean bootRun

# Build runnable jar
build/libs/$(jar-file): src/main/resources/server.jks
	./gradlew -Dillegal-access=deny clean build

run-jar: build/libs/$(jar-file)
	java --illegal-access=deny -jar build/libs/$(jar-file)
# Deletes server.jks
clean-key:
	rm src/main/resources/server.jks

clean:
	rm build/libs/$(jar-file) | true
	./gradlew -Dillegal-access=deny clean