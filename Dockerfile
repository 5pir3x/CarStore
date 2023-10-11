# Stage 1: Build the Spring Boot application and run the tests
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn test
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight image to run the application
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/carstore-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "carstore-0.0.1-SNAPSHOT.jar"]
