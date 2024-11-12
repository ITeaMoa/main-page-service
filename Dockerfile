# Use an official OpenJDK runtime (Java 17) as the base image for running the Spring Boot app
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files to the container
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle
COPY src /app/src

# Grant execution rights to the Gradle wrapper
RUN chmod +x ./gradlew

# Build the application JAR using Gradle
RUN ./gradlew build

# Expose the port that Spring Boot uses (default is 8080)
# will change after eks cluster added
EXPOSE 8080 

# Copy the generated JAR file into the container
COPY build/libs/mainpage-0.0.1-SNAPSHOT.jar /app/mainpage-0.0.1-SNAPSHOT.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/mainpage-0.0.1-SNAPSHOT.jar"]