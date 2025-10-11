

# ---------- Stage 1: Build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the built JAR from stage 1
COPY --from=build /app/target/TaskService-0.0.1-SNAPSHOT.jar app.jar

# Render assigns the port via $PORT
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
