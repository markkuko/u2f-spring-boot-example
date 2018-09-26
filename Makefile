

# Dependency check
dep-check:
	./gradlew clean dependencyCheckAnalyze

# Generate server side keyfile
src/main/resources/server.jks:
	keytool -genkey -alias selfsigned_localhost_sslserver -keyalg RSA -keysize 2048 -validity 1500 -keypass changeitpass -storepass changeitpass -keystore src/main/resources/server.jks

# Start the server
start: src/main/resources/server.jks
	./gradlew clean bootRun

# Deletes server.jks
clean-key:
	rm src/main/resources/server.jks
