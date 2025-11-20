# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies (this layer will be cached if pom.xml doesn't change)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the application and create the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Run the Spring Boot application
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/academia-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 so Docker can map it to the host
EXPOSE 8080

# Specify the command to run the Spring Boot JAR
ENTRYPOINT ["java", "-jar", "app.jar"]