# Use an official OpenJDK image as base
FROM openjdk:22-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy only the built JAR file (not the source code)
COPY target/task-manager-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 9090

# Run the application
CMD ["java", "-jar", "app.jar"]
