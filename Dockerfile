# Use the official OpenJDK 17 slim image as the base
FROM maven:3.9-eclipse-temurin-21

# Set the working directory inside the container
WORKDIR /app  

# Copy the built JAR file from target folder to the container
COPY target/academia-0.0.1-SNAPSHOT.jar app.jar  

# Expose port 8080 so Docker can map it to the host
EXPOSE 8080  

# Specify the command to run the Spring Boot JAR
ENTRYPOINT ["java","-jar","app.jar"]
